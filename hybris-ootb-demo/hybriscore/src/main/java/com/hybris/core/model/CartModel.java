package com.hybris.core.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Hybris OOTB: Cart item type.
 * Corresponds to items.xml <itemtype code="Cart" extends="AbstractOrder">.
 * Session cart holding line items, promotions, and payment/delivery info.
 * Converted to OrderModel upon successful checkout via PlaceOrderStrategy.
 */
@Entity
@Table(name = "cart", indexes = {
    @Index(name = "idx_cart_guid", columnList = "guid"),
    @Index(name = "idx_cart_user", columnList = "user_pk")
})
public class CartModel extends ItemModel {

    @Column(name = "guid", unique = true)
    private String guid;

    @Column(name = "code")
    private String code;

    @Column(name = "currency_iso")
    private String currencyIso;

    @Column(name = "net")
    private boolean net;

    @Column(name = "site")
    private String site;

    @Column(name = "store")
    private String store;

    @Column(name = "subtotal", precision = 19, scale = 5)
    private BigDecimal subTotal = BigDecimal.ZERO;

    @Column(name = "total_price", precision = 19, scale = 5)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @Column(name = "total_tax", precision = 19, scale = 5)
    private BigDecimal totalTax = BigDecimal.ZERO;

    @Column(name = "total_discounts", precision = 19, scale = 5)
    private BigDecimal totalDiscounts = BigDecimal.ZERO;

    @Column(name = "delivery_cost", precision = 19, scale = 5)
    private BigDecimal deliveryCost = BigDecimal.ZERO;

    @Column(name = "payment_cost", precision = 19, scale = 5)
    private BigDecimal paymentCost = BigDecimal.ZERO;

    @Column(name = "delivery_mode_code")
    private String deliveryModeCode;

    @Column(name = "payment_mode_code")
    private String paymentModeCode;

    @Column(name = "expiration_time")
    private LocalDateTime expirationTime;

    @Column(name = "calculated")
    private boolean calculated;

    @Column(name = "applied_promo_codes")
    private String appliedPromoCodes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk")
    private CustomerModel user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_address_pk")
    private AddressModel deliveryAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_address_pk")
    private AddressModel paymentAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy("entry_number ASC")
    private List<CartEntryModel> entries = new ArrayList<>();

    public CartModel() {
        setTypeCode("Cart");
        setItemType("Cart");
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCurrencyIso() {
        return currencyIso;
    }

    public void setCurrencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
    }

    public boolean isNet() {
        return net;
    }

    public void setNet(boolean net) {
        this.net = net;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getTotalDiscounts() {
        return totalDiscounts;
    }

    public void setTotalDiscounts(BigDecimal totalDiscounts) {
        this.totalDiscounts = totalDiscounts;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public BigDecimal getPaymentCost() {
        return paymentCost;
    }

    public void setPaymentCost(BigDecimal paymentCost) {
        this.paymentCost = paymentCost;
    }

    public String getDeliveryModeCode() {
        return deliveryModeCode;
    }

    public void setDeliveryModeCode(String deliveryModeCode) {
        this.deliveryModeCode = deliveryModeCode;
    }

    public String getPaymentModeCode() {
        return paymentModeCode;
    }

    public void setPaymentModeCode(String paymentModeCode) {
        this.paymentModeCode = paymentModeCode;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public boolean isCalculated() {
        return calculated;
    }

    public void setCalculated(boolean calculated) {
        this.calculated = calculated;
    }

    public String getAppliedPromoCodes() {
        return appliedPromoCodes;
    }

    public void setAppliedPromoCodes(String appliedPromoCodes) {
        this.appliedPromoCodes = appliedPromoCodes;
    }

    public CustomerModel getUser() {
        return user;
    }

    public void setUser(CustomerModel user) {
        this.user = user;
    }

    public AddressModel getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(AddressModel deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public AddressModel getPaymentAddress() {
        return paymentAddress;
    }

    public void setPaymentAddress(AddressModel paymentAddress) {
        this.paymentAddress = paymentAddress;
    }

    public List<CartEntryModel> getEntries() {
        return entries;
    }

    public void setEntries(List<CartEntryModel> entries) {
        this.entries = entries;
    }
}
