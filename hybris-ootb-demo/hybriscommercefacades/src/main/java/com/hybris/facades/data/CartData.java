package com.hybris.facades.data;

import java.util.List;

/**
 * Hybris OOTB: CartData DTO.
 * Mirrors de.hybris.platform.commercefacades.order.data.CartData.
 */
public class CartData extends AbstractOrderData {

    private boolean calculated;
    private int totalItems;
    private List<PromotionData> appliedOrderPromotions;
    private List<PromotionData> potentialOrderPromotions;
    private boolean hasPickUpItems;
    private boolean hasShippingItems;
    private boolean guestCustomer;
    private String guid;

    public boolean isCalculated() { return calculated; }
    public void setCalculated(boolean calculated) { this.calculated = calculated; }
    public int getTotalItems() { return totalItems; }
    public void setTotalItems(int totalItems) { this.totalItems = totalItems; }
    public List<PromotionData> getAppliedOrderPromotions() { return appliedOrderPromotions; }
    public void setAppliedOrderPromotions(List<PromotionData> appliedOrderPromotions) { this.appliedOrderPromotions = appliedOrderPromotions; }
    public List<PromotionData> getPotentialOrderPromotions() { return potentialOrderPromotions; }
    public void setPotentialOrderPromotions(List<PromotionData> potentialOrderPromotions) { this.potentialOrderPromotions = potentialOrderPromotions; }
    public boolean isHasPickUpItems() { return hasPickUpItems; }
    public void setHasPickUpItems(boolean hasPickUpItems) { this.hasPickUpItems = hasPickUpItems; }
    public boolean isHasShippingItems() { return hasShippingItems; }
    public void setHasShippingItems(boolean hasShippingItems) { this.hasShippingItems = hasShippingItems; }
    public boolean isGuestCustomer() { return guestCustomer; }
    public void setGuestCustomer(boolean guestCustomer) { this.guestCustomer = guestCustomer; }
    public String getGuid() { return guid; }
    public void setGuid(String guid) { this.guid = guid; }
}
