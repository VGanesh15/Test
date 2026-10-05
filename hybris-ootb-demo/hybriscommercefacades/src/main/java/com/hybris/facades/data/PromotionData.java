package com.hybris.facades.data;

/** Hybris OOTB: PromotionData DTO. */
public class PromotionData {
    private String code;
    private String title;
    private String description;
    private PriceData savings;
    private String promotionType;
    private boolean enabled;
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public PriceData getSavings() { return savings; }
    public void setSavings(PriceData savings) { this.savings = savings; }
    public String getPromotionType() { return promotionType; }
    public void setPromotionType(String promotionType) { this.promotionType = promotionType; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}
