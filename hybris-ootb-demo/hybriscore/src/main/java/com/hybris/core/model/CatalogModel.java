package com.hybris.core.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Hybris OOTB: Catalog type.
 * Corresponds to items.xml <itemtype code="Catalog">.
 * A Catalog contains CatalogVersions which contain Products and Categories.
 */
@Entity
@Table(name = "catalog")
public class CatalogModel extends ItemModel {

    @Column(name = "catalog_id", unique = true, nullable = false)
    private String id;

    @Column(name = "catalog_name")
    private String name;

    @Column(name = "url_patterns")
    private String urlPatterns;

    @Column(name = "default_catalog")
    private boolean defaultCatalog;

    @OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CatalogVersionModel> catalogVersions = new ArrayList<>();

    public CatalogModel() {
        setTypeCode("Catalog");
        setItemType("Catalog");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(String urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    public boolean isDefaultCatalog() {
        return defaultCatalog;
    }

    public void setDefaultCatalog(boolean defaultCatalog) {
        this.defaultCatalog = defaultCatalog;
    }

    public List<CatalogVersionModel> getCatalogVersions() {
        return catalogVersions;
    }

    public void setCatalogVersions(List<CatalogVersionModel> catalogVersions) {
        this.catalogVersions = catalogVersions;
    }
}
