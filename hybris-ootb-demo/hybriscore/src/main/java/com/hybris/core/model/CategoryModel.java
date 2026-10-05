package com.hybris.core.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Hybris OOTB: Category item type.
 * Corresponds to items.xml <itemtype code="Category" extends="GenericItem">.
 * Categories form a hierarchical tree structure used for product classification.
 */
@Entity
@Table(name = "category", indexes = {
    @Index(name = "idx_category_code", columnList = "code"),
    @Index(name = "idx_category_catalog_version", columnList = "catalog_version_pk")
})
public class CategoryModel extends ItemModel {

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", length = 500)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "thumbnail_url", length = 1000)
    private String thumbnailUrl;

    @Column(name = "picture_url", length = 1000)
    private String pictureUrl;

    @Column(name = "order_in_parent")
    private Integer orderInParent;

    @Column(name = "external_id")
    private String externalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_version_pk")
    private CatalogVersionModel catalogVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supercategory_pk")
    private CategoryModel supercategory;

    @OneToMany(mappedBy = "supercategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CategoryModel> subcategories = new ArrayList<>();

    @ManyToMany(mappedBy = "supercategories", fetch = FetchType.LAZY)
    private List<ProductModel> products = new ArrayList<>();

    public CategoryModel() {
        setTypeCode("Category");
        setItemType("Category");
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

    public Integer getOrderInParent() {
        return orderInParent;
    }

    public void setOrderInParent(Integer orderInParent) {
        this.orderInParent = orderInParent;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public CatalogVersionModel getCatalogVersion() {
        return catalogVersion;
    }

    public void setCatalogVersion(CatalogVersionModel catalogVersion) {
        this.catalogVersion = catalogVersion;
    }

    public CategoryModel getSupercategory() {
        return supercategory;
    }

    public void setSupercategory(CategoryModel supercategory) {
        this.supercategory = supercategory;
    }

    public List<CategoryModel> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<CategoryModel> subcategories) {
        this.subcategories = subcategories;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }
}
