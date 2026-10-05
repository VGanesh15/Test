package com.hybris.core.service;

import com.hybris.core.model.CartModel;
import com.hybris.core.model.OrderModel;

import java.util.List;
import java.util.Optional;

/**
 * Hybris OOTB: OrderService interface.
 * Mirrors de.hybris.platform.order.OrderService.
 *
 * The order lifecycle (CREATED → PAYMENT_AUTHORIZED → READY → COMPLETED)
 * is driven by order business processes (OBP) in real Hybris, using
 * de.hybris.platform.processengine.BusinessProcessService.
 */
public interface OrderService {

    /**
     * Place order from a cart. Triggers the place order business process.
     * In real Hybris: PlaceOrderAction → CheckoutService.placeOrder()
     */
    OrderModel placeOrder(CartModel cart);

    Optional<OrderModel> getOrderByCode(String code);

    Optional<OrderModel> getOrderByGuid(String guid);

    List<OrderModel> getOrdersForCustomer(String customerUid);

    OrderModel updateOrderStatus(String orderCode, OrderModel.OrderStatus newStatus);

    void cancelOrder(String orderCode, String reason);

    List<OrderModel> getOrdersByStatus(OrderModel.OrderStatus status);

    OrderModel submitForFraudCheck(OrderModel order);

    OrderModel capturePayment(OrderModel order);
}
