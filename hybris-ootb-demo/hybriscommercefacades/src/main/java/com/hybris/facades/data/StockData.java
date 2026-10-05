package com.hybris.facades.data;

/**
 * Hybris OOTB: StockData DTO.
 * Mirrors de.hybris.platform.commercefacades.product.data.StockData.
 * Reflects warehouse stock availability for display on the PDP.
 */
public class StockData {

    public enum StockLevelStatus {
        inStock, lowStock, outOfStock, forceInStock
    }

    private Long stockLevel;
    private StockLevelStatus stockLevelStatus;
    private boolean isValueRounded;

    public Long getStockLevel() { return stockLevel; }
    public void setStockLevel(Long stockLevel) { this.stockLevel = stockLevel; }
    public StockLevelStatus getStockLevelStatus() { return stockLevelStatus; }
    public void setStockLevelStatus(StockLevelStatus stockLevelStatus) { this.stockLevelStatus = stockLevelStatus; }
    public boolean isValueRounded() { return isValueRounded; }
    public void setValueRounded(boolean valueRounded) { isValueRounded = valueRounded; }
}
