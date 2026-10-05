package com.hybris.core.service;

import com.hybris.core.dao.OrderDao;
import com.hybris.core.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Hybris OOTB: DefaultOrderService.
 * Mirrors de.hybris.platform.order.impl.DefaultOrderService.
 *
 * placeOrder() converts a Cart to an Order. In real Hybris this triggers:
 * 1. CalculationService.recalculate(cart)
 * 2. PlaceOrderStrategy (validates, creates Order items)
 * 3. Business process start: order-process
 * 4. CleanUpCartAfterOrderPlacedAction
 */
@Service("orderService")
@Transactional
public class DefaultOrderService implements OrderService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultOrderService.class);
    private static final AtomicInteger ORDER_COUNTER = new AtomicInteger(1000);

    private final OrderDao orderDao;
    private final CartService cartService;

    @Autowired
    public DefaultOrderService(final OrderDao orderDao, final CartService cartService) {
        this.orderDao = orderDao;
        this.cartService = cartService;
    }

    @Override
    public OrderModel placeOrder(final CartModel cart) {
        Assert.notNull(cart, "Cart must not be null");
        Assert.notEmpty(cart.getEntries(), "Cart must have at least one entry");

        LOG.info("Placing order from cart: {}", cart.getGuid());

        final OrderModel order = new OrderModel();
        order.setCode("ORD-" + ORDER_COUNTER.getAndIncrement());
        order.setGuid(UUID.randomUUID().toString());
        order.setUser(cart.getUser());
        order.setCurrencyIso(cart.getCurrencyIso());
        order.setSite(cart.getSite());
        order.setStore(cart.getStore());
        order.setSubTotal(cart.getSubTotal());
        order.setTotalPrice(cart.getTotalPrice());
        order.setTotalTax(cart.getTotalTax());
        order.setTotalDiscounts(cart.getTotalDiscounts());
        order.setDeliveryCost(cart.getDeliveryCost());
        order.setDeliveryModeCode(cart.getDeliveryModeCode());
        order.setPaymentModeCode(cart.getPaymentModeCode());
        order.setDeliveryAddress(cart.getDeliveryAddress());
        order.setPaymentAddress(cart.getPaymentAddress());
        order.setStatus(OrderModel.OrderStatus.CREATED);
        order.setPlaced(LocalDateTime.now());

        for (final CartEntryModel cartEntry : cart.getEntries()) {
            final OrderEntryModel orderEntry = new OrderEntryModel();
            orderEntry.setEntryNumber(cartEntry.getEntryNumber());
            orderEntry.setProduct(cartEntry.getProduct());
            orderEntry.setQuantity(cartEntry.getQuantity());
            orderEntry.setBasePrice(cartEntry.getBasePrice());
            orderEntry.setTotalPrice(cartEntry.getTotalPrice());
            orderEntry.setCurrencyIso(cartEntry.getCurrencyIso());
            orderEntry.setUnit(cartEntry.getUnit());
            orderEntry.setOrder(order);
            order.getEntries().add(orderEntry);
        }

        final OrderModel savedOrder = orderDao.save(order);
        cartService.clearCart(cart);

        LOG.info("Order placed successfully: code={}, total={}", savedOrder.getCode(), savedOrder.getTotalPrice());
        return submitForFraudCheck(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderModel> getOrderByCode(final String code) {
        return orderDao.findByCode(code);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderModel> getOrderByGuid(final String guid) {
        return orderDao.findByGuid(guid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderModel> getOrdersForCustomer(final String customerUid) {
        return orderDao.findOrderHistoryByUser(customerUid);
    }

    @Override
    public OrderModel updateOrderStatus(final String orderCode, final OrderModel.OrderStatus newStatus) {
        final OrderModel order = orderDao.findByCode(orderCode)
            .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderCode));
        LOG.info("Order {} status transition: {} → {}", orderCode, order.getStatus(), newStatus);
        order.setStatus(newStatus);
        return orderDao.save(order);
    }

    @Override
    public void cancelOrder(final String orderCode, final String reason) {
        final OrderModel order = orderDao.findByCode(orderCode)
            .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderCode));

        if (order.getStatus() == OrderModel.OrderStatus.COMPLETED ||
            order.getStatus() == OrderModel.OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot cancel order in status: " + order.getStatus());
        }

        order.setStatus(OrderModel.OrderStatus.CANCELLING);
        order.setStatusInfo(reason);
        orderDao.save(order);
        order.setStatus(OrderModel.OrderStatus.CANCELLED);
        orderDao.save(order);
        LOG.info("Order {} cancelled. Reason: {}", orderCode, reason);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderModel> getOrdersByStatus(final OrderModel.OrderStatus status) {
        return orderDao.findByStatus(status);
    }

    @Override
    public OrderModel submitForFraudCheck(final OrderModel order) {
        order.setStatus(OrderModel.OrderStatus.ON_VALIDATION);
        final OrderModel saved = orderDao.save(order);
        LOG.info("Order {} submitted for fraud check", order.getCode());
        return capturePayment(saved);
    }

    @Override
    public OrderModel capturePayment(final OrderModel order) {
        order.setPaymentTransactionId("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setStatus(OrderModel.OrderStatus.PAYMENT_CAPTURED);
        final OrderModel saved = orderDao.save(order);
        LOG.info("Payment captured for order: {}, txn={}", order.getCode(), order.getPaymentTransactionId());
        return updateOrderStatus(saved.getCode(), OrderModel.OrderStatus.READY);
    }
}
