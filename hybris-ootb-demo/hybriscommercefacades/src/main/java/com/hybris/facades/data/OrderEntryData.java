package com.hybris.facades.data;

/**
 * Hybris OOTB: OrderEntryData DTO.
 * Mirrors de.hybris.platform.commercefacades.order.data.OrderEntryData.
 */
public class OrderEntryData {

    private Integer entryNumber;
    private Long quantity;
    private ProductData product;
    private PriceData basePrice;
    private PriceData totalPrice;
    private String unit;
    private boolean updateable;
    private DeliveryModeData deliveryMode;

    public Integer getEntryNumber() { return entryNumber; }
    public void setEntryNumber(Integer entryNumber) { this.entryNumber = entryNumber; }
    public Long getQuantity() { return quantity; }
    public void setQuantity(Long quantity) { this.quantity = quantity; }
    public ProductData getProduct() { return product; }
    public void setProduct(ProductData product) { this.product = product; }
    public PriceData getBasePrice() { return basePrice; }
    public void setBasePrice(PriceData basePrice) { this.basePrice = basePrice; }
    public PriceData getTotalPrice() { return totalPrice; }
    public void setTotalPrice(PriceData totalPrice) { this.totalPrice = totalPrice; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public boolean isUpdateable() { return updateable; }
    public void setUpdateable(boolean updateable) { this.updateable = updateable; }
    public DeliveryModeData getDeliveryMode() { return deliveryMode; }
    public void setDeliveryMode(DeliveryModeData deliveryMode) { this.deliveryMode = deliveryMode; }
}
