package com.hybris.core.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Hybris OOTB: PriceRow (Europe1PriceRow).
 * Corresponds to items.xml <itemtype code="PriceRow">.
 * Flexible price rules supporting user group, quantity breaks, currency, and date ranges.
 * Used by the Europe1PriceFactory to resolve prices during cart calculation.
 */
@Entity
@Table(name = "price_row", indexes = {
    @Index(name = "idx_price_product", columnList = "product_pk")
})
public class PriceRowModel extends ItemModel {

    @Column(name = "currency_iso")
    private String currencyIso;

    @Column(name = "price", precision = 19, scale = 5)
    private BigDecimal price;

    @Column(name = "min_qty")
    private Long minQuantity = 1L;

    @Column(name = "unit")
    private String unit;

    @Column(name = "net")
    private boolean net;

    @Column(name = "channel")
    private String channel;

    @Column(name = "user_group")
    private String userGroup;

    @Column(name = "product_id")
    private String productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_pk")
    private ProductModel product;

    public PriceRowModel() {
        setTypeCode("PriceRow");
        setItemType("PriceRow");
    }

    public String getCurrencyIso() { return currencyIso; }
    public void setCurrencyIso(String currencyIso) { this.currencyIso = currencyIso; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Long getMinQuantity() { return minQuantity; }
    public void setMinQuantity(Long minQuantity) { this.minQuantity = minQuantity; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public boolean isNet() { return net; }
    public void setNet(boolean net) { this.net = net; }
    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }
    public String getUserGroup() { return userGroup; }
    public void setUserGroup(String userGroup) { this.userGroup = userGroup; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public ProductModel getProduct() { return product; }
    public void setProduct(ProductModel product) { this.product = product; }
}
