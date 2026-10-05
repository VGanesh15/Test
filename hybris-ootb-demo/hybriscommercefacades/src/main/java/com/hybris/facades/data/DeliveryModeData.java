package com.hybris.facades.data;

/** Hybris OOTB: DeliveryModeData DTO. */
public class DeliveryModeData {
    private String code;
    private String name;
    private String description;
    private PriceData deliveryCost;
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public PriceData getDeliveryCost() { return deliveryCost; }
    public void setDeliveryCost(PriceData deliveryCost) { this.deliveryCost = deliveryCost; }
}
