package com.hybris.facades.data;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Hybris OOTB: PriceData DTO.
 * Mirrors de.hybris.platform.commercefacades.product.data.PriceData.
 *
 * Represents a formatted price with currency symbol. Used throughout the
 * storefront and OCC API for price display. FormattedValue is locale-aware.
 */
public class PriceData {

    public enum PriceType {
        BUY, FROM
    }

    private PriceType priceType = PriceType.BUY;
    private BigDecimal value;
    private String currencyIso;
    private String formattedValue;
    private BigDecimal maxQuantity;
    private BigDecimal minQuantity;

    public PriceData() {}

    public PriceData(final BigDecimal value, final String currencyIso) {
        this.value = value;
        this.currencyIso = currencyIso;
        this.formattedValue = NumberFormat.getCurrencyInstance(Locale.US).format(value != null ? value : BigDecimal.ZERO);
    }

    public PriceType getPriceType() { return priceType; }
    public void setPriceType(PriceType priceType) { this.priceType = priceType; }
    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }
    public String getCurrencyIso() { return currencyIso; }
    public void setCurrencyIso(String currencyIso) { this.currencyIso = currencyIso; }
    public String getFormattedValue() { return formattedValue; }
    public void setFormattedValue(String formattedValue) { this.formattedValue = formattedValue; }
    public BigDecimal getMaxQuantity() { return maxQuantity; }
    public void setMaxQuantity(BigDecimal maxQuantity) { this.maxQuantity = maxQuantity; }
    public BigDecimal getMinQuantity() { return minQuantity; }
    public void setMinQuantity(BigDecimal minQuantity) { this.minQuantity = minQuantity; }
}
