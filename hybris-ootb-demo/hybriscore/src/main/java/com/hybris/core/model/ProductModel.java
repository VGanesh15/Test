package com.hybris.core.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Hybris OOTB: Product item type.
 * Corresponds to items.xml <itemtype code="Product" extends="GenericItem">.
 * Core product model with catalog versioning, variant support, and approval status.
 */
@Entity
@Table(name = "product", indexes = {
    @Index(name = "idx_product_code", columnList = "code"),
    @Index(name = "idx_product_catalog_version", columnList = "catalog_version_pk")
})
public class ProductModel extends ItemModel {

    public enum ApprovalStatus {
        CHECK, APPROVED, UNAPPROVED
    }

    public enum ArticleStatus {
        APPROVED, INWORK, CHECK
    }

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", length = 500)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "summary", length = 2000)
    private String summary;

    @Column(name = "ean")
    private String ean;

    @Column(name = "manufacturer_name")
    private String manufacturerName;

    @Column(name = "manufacturer_aid")
    private String manufacturerAID;

    @Column(name = "unit")
    private String unit;

    @Column(name = "price", precision = 19, scale = 5)
    private BigDecimal price;

    @Column(name = "currency_iso")
    private String currencyIso;

    @Column(name = "stock_level")
    private Integer stockLevel;

    @Column(name = "available_for_pickup")
    private boolean availableForPickup;

    @Column(name = "purchase_able")
    private boolean purchasable = true;

    @Column(name = "online_date")
    private LocalDate onlineDate;

    @Column(name = "offline_date")
    private LocalDate offlineDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status")
    private ApprovalStatus approvalStatus = ApprovalStatus.CHECK;

    @Enumerated(EnumType.STRING)
    @Column(name = "article_status")
    private ArticleStatus articleStatus = ArticleStatus.INWORK;

    @Column(name = "thumbnail_url", length = 1000)
    private String thumbnailUrl;

    @Column(name = "picture_url", length = 1000)
    private String pictureUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_version_pk")
    private CatalogVersionModel catalogVersion;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "product_category",
        joinColumns = @JoinColumn(name = "product_pk"),
        inverseJoinColumns = @JoinColumn(name = "category_pk")
    )
    private List<CategoryModel> supercategories = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PriceRowModel> europe1Prices = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MediaModel> gallerymages = new ArrayList<>();

    public ProductModel() {
        setTypeCode("Product");
        setItemType("Product");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getManufacturerAID() {
        return manufacturerAID;
    }

    public void setManufacturerAID(String manufacturerAID) {
        this.manufacturerAID = manufacturerAID;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrencyIso() {
        return currencyIso;
    }

    public void setCurrencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
    }

    public Integer getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(Integer stockLevel) {
        this.stockLevel = stockLevel;
    }

    public boolean isAvailableForPickup() {
        return availableForPickup;
    }

    public void setAvailableForPickup(boolean availableForPickup) {
        this.availableForPickup = availableForPickup;
    }

    public boolean isPurchasable() {
        return purchasable;
    }

    public void setPurchasable(boolean purchasable) {
        this.purchasable = purchasable;
    }

    public LocalDate getOnlineDate() {
        return onlineDate;
    }

    public void setOnlineDate(LocalDate onlineDate) {
        this.onlineDate = onlineDate;
    }

    public LocalDate getOfflineDate() {
        return offlineDate;
    }

    public void setOfflineDate(LocalDate offlineDate) {
        this.offlineDate = offlineDate;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public ArticleStatus getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(ArticleStatus articleStatus) {
        this.articleStatus = articleStatus;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public CatalogVersionModel getCatalogVersion() {
        return catalogVersion;
    }

    public void setCatalogVersion(CatalogVersionModel catalogVersion) {
        this.catalogVersion = catalogVersion;
    }

    public List<CategoryModel> getSupercategories() {
        return supercategories;
    }

    public void setSupercategories(List<CategoryModel> supercategories) {
        this.supercategories = supercategories;
    }

    public List<PriceRowModel> getEurope1Prices() {
        return europe1Prices;
    }

    public void setEurope1Prices(List<PriceRowModel> europe1Prices) {
        this.europe1Prices = europe1Prices;
    }

    public List<MediaModel> getGallerymages() {
        return gallerymages;
    }

    public void setGallerymages(List<MediaModel> gallerymages) {
        this.gallerymages = gallerymages;
    }
}
