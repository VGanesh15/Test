package com.hybris.facades.facades;

import com.hybris.facades.data.AddressData;
import com.hybris.facades.data.CartData;
import com.hybris.facades.data.OrderData;

import java.util.List;

/**
 * Hybris OOTB: CheckoutFacade interface.
 * Mirrors de.hybris.platform.commercefacades.order.CheckoutFacade.
 *
 * CheckoutFacade orchestrates the multi-step checkout flow:
 * Step 1: Delivery address
 * Step 2: Delivery mode
 * Step 3: Payment details
 * Step 4: Order review & place order
 *
 * The checkout flow can be customised via CheckoutStep configuration and
 * CheckoutFlowFacade which controls which steps are required/optional.
 */
public interface CheckoutFacade {

    CartData getCheckoutCart();

    CartData setDeliveryAddress(AddressData addressData);

    CartData setDeliveryMode(String deliveryModeCode);

    CartData setPaymentMode(String paymentModeCode);

    boolean isValidCheckout();

    boolean hasValidDeliveryAddress();

    boolean hasValidDeliveryMode();

    boolean hasValidPaymentInfo();

    OrderData placeOrder();

    List<String> getSupportedDeliveryModes();

    List<String> getSupportedPaymentModes();
}
