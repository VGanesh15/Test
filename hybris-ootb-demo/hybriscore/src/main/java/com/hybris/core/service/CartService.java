package com.hybris.core.service;

import com.hybris.core.model.CartEntryModel;
import com.hybris.core.model.CartModel;
import com.hybris.core.model.CustomerModel;

import java.util.Optional;

/**
 * Hybris OOTB: CartService interface.
 * Mirrors de.hybris.platform.order.CartService.
 *
 * Central service for session cart management. Cart entries are recalculated
 * whenever quantity changes via the CalculationService (price + tax + promotions).
 */
public interface CartService {

    CartModel getSessionCart();

    CartModel getOrCreateCartForUser(CustomerModel customer);

    Optional<CartModel> getCartForGuid(String guid);

    CartEntryModel addToCart(CartModel cart, String productCode, long quantity);

    CartModel updateCartEntry(CartModel cart, int entryNumber, long newQuantity);

    CartModel removeCartEntry(CartModel cart, int entryNumber);

    CartModel calculateCart(CartModel cart);

    CartModel setDeliveryAddress(CartModel cart, String addressPk);

    CartModel setPaymentAddress(CartModel cart, String addressPk);

    CartModel setDeliveryMode(CartModel cart, String deliveryModeCode);

    CartModel setPaymentMode(CartModel cart, String paymentModeCode);

    CartModel applyPromoCode(CartModel cart, String promoCode);

    void removePromoCode(CartModel cart, String promoCode);

    void clearCart(CartModel cart);

    int getCartTotalItems(CartModel cart);
}
