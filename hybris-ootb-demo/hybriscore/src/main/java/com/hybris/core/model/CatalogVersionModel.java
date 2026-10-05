package com.hybris.core.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Hybris OOTB: CatalogVersion type.
 * Corresponds to items.xml <itemtype code="CatalogVersion">.
 * Active catalog versions are used to determine which products are visible in the storefront.
 */
@Entity
@Table(name = "catalog_version", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"catalog_pk", "catalog_version_label"})
})
public class CatalogVersionModel extends ItemModel {

    @Column(name = "catalog_version_label", nullable = false)
    private String versionLabel;

    @Column(name = "active")
    private boolean active;

    @Column(name = "mime_settings")
    private String mimeSettings;

    @ManyToOne(fetch = FetchType.LAZY, cascade = jakarta.persistence.CascadeType.PERSIST)
    @JoinColumn(name = "catalog_pk")
    private CatalogModel catalog;

    @OneToMany(mappedBy = "catalogVersion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductModel> products = new ArrayList<>();

    @OneToMany(mappedBy = "catalogVersion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CategoryModel> categories = new ArrayList<>();

    public CatalogVersionModel() {
        setTypeCode("CatalogVersion");
        setItemType("CatalogVersion");
    }

    public String getVersionLabel() {
        return versionLabel;
    }

    public void setVersionLabel(String versionLabel) {
        this.versionLabel = versionLabel;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getMimeSettings() {
        return mimeSettings;
    }

    public void setMimeSettings(String mimeSettings) {
        this.mimeSettings = mimeSettings;
    }

    public CatalogModel getCatalog() {
        return catalog;
    }

    public void setCatalog(CatalogModel catalog) {
        this.catalog = catalog;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    public List<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryModel> categories) {
        this.categories = categories;
    }
}
