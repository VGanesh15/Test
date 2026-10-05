package com.hybris.core.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Hybris OOTB: OrderEntry item type.
 * Corresponds to items.xml <itemtype code="OrderEntry" extends="AbstractOrderEntry">.
 * Immutable snapshot of a product at time of order placement.
 */
@Entity
@Table(name = "order_entry")
public class OrderEntryModel extends ItemModel {

    @Column(name = "entry_number")
    private Integer entryNumber;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "base_price", precision = 19, scale = 5)
    private BigDecimal basePrice;

    @Column(name = "total_price", precision = 19, scale = 5)
    private BigDecimal totalPrice;

    @Column(name = "discount_percentage", precision = 10, scale = 5)
    private BigDecimal discountPercentage = BigDecimal.ZERO;

    @Column(name = "tax_percentage", precision = 10, scale = 5)
    private BigDecimal taxPercentage = BigDecimal.ZERO;

    @Column(name = "currency_iso")
    private String currencyIso;

    @Column(name = "unit")
    private String unit;

    @Column(name = "give_away")
    private boolean giveAway;

    @Column(name = "named_delivery_date_applied")
    private boolean namedDeliveryDateApplied;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_pk")
    private OrderModel order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_pk")
    private ProductModel product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_address_pk")
    private AddressModel deliveryAddress;

    public OrderEntryModel() {
        setTypeCode("OrderEntry");
        setItemType("OrderEntry");
    }

    public Integer getEntryNumber() { return entryNumber; }
    public void setEntryNumber(Integer entryNumber) { this.entryNumber = entryNumber; }
    public Long getQuantity() { return quantity; }
    public void setQuantity(Long quantity) { this.quantity = quantity; }
    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public BigDecimal getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(BigDecimal discountPercentage) { this.discountPercentage = discountPercentage; }
    public BigDecimal getTaxPercentage() { return taxPercentage; }
    public void setTaxPercentage(BigDecimal taxPercentage) { this.taxPercentage = taxPercentage; }
    public String getCurrencyIso() { return currencyIso; }
    public void setCurrencyIso(String currencyIso) { this.currencyIso = currencyIso; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public boolean isGiveAway() { return giveAway; }
    public void setGiveAway(boolean giveAway) { this.giveAway = giveAway; }
    public boolean isNamedDeliveryDateApplied() { return namedDeliveryDateApplied; }
    public void setNamedDeliveryDateApplied(boolean namedDeliveryDateApplied) { this.namedDeliveryDateApplied = namedDeliveryDateApplied; }
    public OrderModel getOrder() { return order; }
    public void setOrder(OrderModel order) { this.order = order; }
    public ProductModel getProduct() { return product; }
    public void setProduct(ProductModel product) { this.product = product; }
    public AddressModel getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(AddressModel deliveryAddress) { this.deliveryAddress = deliveryAddress; }
}
