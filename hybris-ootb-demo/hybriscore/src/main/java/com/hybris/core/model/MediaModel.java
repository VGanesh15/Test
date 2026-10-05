package com.hybris.core.model;

import jakarta.persistence.*;

/**
 * Hybris OOTB: Media item type.
 * Corresponds to items.xml <itemtype code="Media" extends="GenericItem">.
 * Stores product images, documents, and other media assets.
 * Media are managed within CatalogVersions to support approval workflows.
 */
@Entity
@Table(name = "media")
public class MediaModel extends ItemModel {

    public enum MediaFormat {
        THUMBNAIL, PRODUCT_300, PRODUCT_515, PRODUCT_770, PRODUCT_1200,
        ZOOM, DETAIL, CARTPAGE, PORTRAIT_SMALL, PORTRAIT_NORMAL
    }

    @Column(name = "code")
    private String code;

    @Column(name = "url", length = 2000)
    private String url;

    @Column(name = "alt_text", length = 500)
    private String altText;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "mime")
    private String mime;

    @Column(name = "size_bytes")
    private Long sizeBytes;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_format")
    private MediaFormat mediaFormat;

    @Column(name = "external_url")
    private boolean externalUrl;

    @Column(name = "catalog_id")
    private String catalogId;

    @Column(name = "catalog_version")
    private String catalogVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_pk")
    private ProductModel product;

    public MediaModel() {
        setTypeCode("Media");
        setItemType("Media");
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getAltText() { return altText; }
    public void setAltText(String altText) { this.altText = altText; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getMime() { return mime; }
    public void setMime(String mime) { this.mime = mime; }
    public Long getSizeBytes() { return sizeBytes; }
    public void setSizeBytes(Long sizeBytes) { this.sizeBytes = sizeBytes; }
    public Integer getWidth() { return width; }
    public void setWidth(Integer width) { this.width = width; }
    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }
    public MediaFormat getMediaFormat() { return mediaFormat; }
    public void setMediaFormat(MediaFormat mediaFormat) { this.mediaFormat = mediaFormat; }
    public boolean isExternalUrl() { return externalUrl; }
    public void setExternalUrl(boolean externalUrl) { this.externalUrl = externalUrl; }
    public String getCatalogId() { return catalogId; }
    public void setCatalogId(String catalogId) { this.catalogId = catalogId; }
    public String getCatalogVersion() { return catalogVersion; }
    public void setCatalogVersion(String catalogVersion) { this.catalogVersion = catalogVersion; }
    public ProductModel getProduct() { return product; }
    public void setProduct(ProductModel product) { this.product = product; }
}
