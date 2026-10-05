package com.hybris.facades.facades;

import com.hybris.core.model.AddressModel;
import com.hybris.core.model.CartModel;
import com.hybris.core.model.OrderModel;
import com.hybris.core.service.CartService;
import com.hybris.core.service.OrderService;
import com.hybris.facades.converters.CartConverter;
import com.hybris.facades.converters.OrderConverter;
import com.hybris.facades.data.AddressData;
import com.hybris.facades.data.CartData;
import com.hybris.facades.data.OrderData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Hybris OOTB: DefaultCheckoutFacade.
 * Mirrors de.hybris.platform.commercefacades.order.impl.DefaultCheckoutFacade.
 */
@Service("checkoutFacade")
public class DefaultCheckoutFacade implements CheckoutFacade {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultCheckoutFacade.class);

    private final CartService cartService;
    private final OrderService orderService;
    private final CartConverter cartConverter;
    private final OrderConverter orderConverter;
    private final DefaultCartFacade cartFacade;

    @Autowired
    public DefaultCheckoutFacade(final CartService cartService,
                                  final OrderService orderService,
                                  final CartConverter cartConverter,
                                  final OrderConverter orderConverter,
                                  final DefaultCartFacade cartFacade) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.cartConverter = cartConverter;
        this.orderConverter = orderConverter;
        this.cartFacade = cartFacade;
    }

    @Override
    public CartData getCheckoutCart() {
        return cartFacade.getSessionCart();
    }

    @Override
    public CartData setDeliveryAddress(final AddressData addressData) {
        final AddressModel address = convertToModel(addressData);
        final CartModel cart = cartFacade.sessionCart;
        if (cart != null) {
            cart.setDeliveryAddress(address);
        }
        return cartFacade.getSessionCart();
    }

    @Override
    public CartData setDeliveryMode(final String deliveryModeCode) {
        return cartFacade.setDeliveryMode(deliveryModeCode);
    }

    @Override
    public CartData setPaymentMode(final String paymentModeCode) {
        return cartFacade.setPaymentMode(paymentModeCode);
    }

    @Override
    public boolean isValidCheckout() {
        return hasValidDeliveryAddress() && hasValidDeliveryMode() && hasValidPaymentInfo();
    }

    @Override
    public boolean hasValidDeliveryAddress() {
        final CartModel cart = cartFacade.sessionCart;
        return cart != null && cart.getDeliveryAddress() != null;
    }

    @Override
    public boolean hasValidDeliveryMode() {
        final CartModel cart = cartFacade.sessionCart;
        return cart != null && cart.getDeliveryModeCode() != null;
    }

    @Override
    public boolean hasValidPaymentInfo() {
        final CartModel cart = cartFacade.sessionCart;
        return cart != null && cart.getPaymentModeCode() != null;
    }

    @Override
    public OrderData placeOrder() {
        final CartModel cart = cartFacade.sessionCart;
        if (cart == null) {
            throw new IllegalStateException("No session cart available to place order");
        }
        LOG.info("Placing order from checkout facade");
        final OrderModel order = orderService.placeOrder(cart);
        cartFacade.sessionCart = null;
        return orderConverter.convert(order);
    }

    @Override
    public List<String> getSupportedDeliveryModes() {
        return List.of("standard", "express", "premium");
    }

    @Override
    public List<String> getSupportedPaymentModes() {
        return List.of("creditCard", "paypal", "debitCard");
    }

    private AddressModel convertToModel(final AddressData data) {
        final AddressModel model = new AddressModel();
        model.setFirstName(data.getFirstName());
        model.setLastName(data.getLastName());
        model.setLine1(data.getLine1());
        model.setLine2(data.getLine2());
        model.setTown(data.getTown());
        model.setPostalCode(data.getPostalCode());
        model.setPhone1(data.getPhone());
        if (data.getCountry() != null) {
            model.setCountryIso(data.getCountry().getIsocode());
        }
        model.setShippingAddress(true);
        return model;
    }
}
