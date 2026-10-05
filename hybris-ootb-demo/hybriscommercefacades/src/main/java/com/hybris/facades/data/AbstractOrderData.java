package com.hybris.facades.data;

import java.util.List;

/**
 * Hybris OOTB: AbstractOrderData DTO base.
 * Mirrors de.hybris.platform.commercefacades.order.data.AbstractOrderData.
 * Shared between CartData and OrderData.
 */
public abstract class AbstractOrderData {

    private String code;
    private PriceData subTotal;
    private PriceData totalPrice;
    private PriceData totalTax;
    private PriceData totalDiscounts;
    private PriceData deliveryCost;
    private String currencyIso;
    private String site;
    private AddressData deliveryAddress;
    private AddressData paymentAddress;
    private String deliveryMode;
    private String paymentMode;
    private List<OrderEntryData> entries;
    private CustomerData user;

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public PriceData getSubTotal() { return subTotal; }
    public void setSubTotal(PriceData subTotal) { this.subTotal = subTotal; }
    public PriceData getTotalPrice() { return totalPrice; }
    public void setTotalPrice(PriceData totalPrice) { this.totalPrice = totalPrice; }
    public PriceData getTotalTax() { return totalTax; }
    public void setTotalTax(PriceData totalTax) { this.totalTax = totalTax; }
    public PriceData getTotalDiscounts() { return totalDiscounts; }
    public void setTotalDiscounts(PriceData totalDiscounts) { this.totalDiscounts = totalDiscounts; }
    public PriceData getDeliveryCost() { return deliveryCost; }
    public void setDeliveryCost(PriceData deliveryCost) { this.deliveryCost = deliveryCost; }
    public String getCurrencyIso() { return currencyIso; }
    public void setCurrencyIso(String currencyIso) { this.currencyIso = currencyIso; }
    public String getSite() { return site; }
    public void setSite(String site) { this.site = site; }
    public AddressData getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(AddressData deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    public AddressData getPaymentAddress() { return paymentAddress; }
    public void setPaymentAddress(AddressData paymentAddress) { this.paymentAddress = paymentAddress; }
    public String getDeliveryMode() { return deliveryMode; }
    public void setDeliveryMode(String deliveryMode) { this.deliveryMode = deliveryMode; }
    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }
    public List<OrderEntryData> getEntries() { return entries; }
    public void setEntries(List<OrderEntryData> entries) { this.entries = entries; }
    public CustomerData getUser() { return user; }
    public void setUser(CustomerData user) { this.user = user; }
}
