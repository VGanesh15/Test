package com.hybris.facades.facades;

import com.hybris.facades.data.CartData;
import com.hybris.facades.data.OrderEntryData;

/**
 * Hybris OOTB: CartFacade interface.
 * Mirrors de.hybris.platform.commercefacades.order.CartFacade.
 */
public interface CartFacade {

    CartData getSessionCart();

    CartData addToCart(String productCode, long quantity);

    CartData updateCartEntry(int entryNumber, long newQuantity);

    CartData removeCartEntry(int entryNumber);

    CartData applyPromoCode(String promoCode);

    void removePromoCode(String promoCode);

    boolean hasSessionCart();

    boolean hasEntries();

    CartData setDeliveryAddress(String addressPk);

    CartData setDeliveryMode(String deliveryModeCode);

    CartData setPaymentMode(String paymentModeCode);

    void clearCart();

    int getCartTotalItems();
}
