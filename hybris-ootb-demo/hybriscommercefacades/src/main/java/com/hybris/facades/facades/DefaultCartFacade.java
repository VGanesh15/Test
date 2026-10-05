package com.hybris.facades.facades;

import com.hybris.core.model.CartModel;
import com.hybris.core.service.CartService;
import com.hybris.facades.converters.CartConverter;
import com.hybris.facades.data.CartData;
import com.hybris.facades.data.OrderEntryData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Hybris OOTB: DefaultCartFacade.
 * Mirrors de.hybris.platform.commercefacades.order.impl.DefaultCartFacade.
 */
@Service("cartFacade")
public class DefaultCartFacade implements CartFacade {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultCartFacade.class);

    private final CartService cartService;
    private final CartConverter cartConverter;

    CartModel sessionCart;

    @Autowired
    public DefaultCartFacade(final CartService cartService, final CartConverter cartConverter) {
        this.cartService = cartService;
        this.cartConverter = cartConverter;
    }

    @Override
    public CartData getSessionCart() {
        if (sessionCart == null) {
            sessionCart = cartService.getSessionCart();
        }
        return cartConverter.convert(sessionCart);
    }

    @Override
    public CartData addToCart(final String productCode, final long quantity) {
        if (sessionCart == null) {
            sessionCart = cartService.getSessionCart();
        }
        cartService.addToCart(sessionCart, productCode, quantity);
        sessionCart = cartService.calculateCart(sessionCart);
        return cartConverter.convert(sessionCart);
    }

    @Override
    public CartData updateCartEntry(final int entryNumber, final long newQuantity) {
        ensureCart();
        sessionCart = cartService.updateCartEntry(sessionCart, entryNumber, newQuantity);
        return cartConverter.convert(sessionCart);
    }

    @Override
    public CartData removeCartEntry(final int entryNumber) {
        ensureCart();
        sessionCart = cartService.removeCartEntry(sessionCart, entryNumber);
        return cartConverter.convert(sessionCart);
    }

    @Override
    public CartData applyPromoCode(final String promoCode) {
        ensureCart();
        sessionCart = cartService.applyPromoCode(sessionCart, promoCode);
        return cartConverter.convert(sessionCart);
    }

    @Override
    public void removePromoCode(final String promoCode) {
        ensureCart();
        cartService.removePromoCode(sessionCart, promoCode);
    }

    @Override
    public boolean hasSessionCart() {
        return sessionCart != null;
    }

    @Override
    public boolean hasEntries() {
        return sessionCart != null && !sessionCart.getEntries().isEmpty();
    }

    @Override
    public CartData setDeliveryAddress(final String addressPk) {
        ensureCart();
        sessionCart = cartService.setDeliveryAddress(sessionCart, addressPk);
        return cartConverter.convert(sessionCart);
    }

    @Override
    public CartData setDeliveryMode(final String deliveryModeCode) {
        ensureCart();
        sessionCart = cartService.setDeliveryMode(sessionCart, deliveryModeCode);
        return cartConverter.convert(sessionCart);
    }

    @Override
    public CartData setPaymentMode(final String paymentModeCode) {
        ensureCart();
        sessionCart = cartService.setPaymentMode(sessionCart, paymentModeCode);
        return cartConverter.convert(sessionCart);
    }

    @Override
    public void clearCart() {
        if (sessionCart != null) {
            cartService.clearCart(sessionCart);
        }
    }

    @Override
    public int getCartTotalItems() {
        if (sessionCart == null) return 0;
        return cartService.getCartTotalItems(sessionCart);
    }

    private void ensureCart() {
        if (sessionCart == null) {
            sessionCart = cartService.getSessionCart();
        }
    }
}
