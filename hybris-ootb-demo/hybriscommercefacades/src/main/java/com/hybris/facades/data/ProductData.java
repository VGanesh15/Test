package com.hybris.facades.data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Hybris OOTB: ProductData DTO.
 * Mirrors de.hybris.platform.commercefacades.product.data.ProductData.
 *
 * Data Transfer Objects (DTOs) in Hybris are simple POJOs generated from
 * *-beans.xml files. They carry no JPA annotations and are used in:
 * - Facade layer (converts Model → Data)
 * - Web layer (binding to Thymeleaf templates)
 * - OCC REST layer (serialised to JSON)
 *
 * The converter pattern: Converter<ProductModel, ProductData> uses
 * registered Populators to fill individual fields.
 */
public class ProductData {

    private String code;
    private String name;
    private String description;
    private String summary;
    private String ean;
    private String manufacturerName;
    private String url;
    private PriceData price;
    private String currencyIso;
    private StockData stock;
    private List<ImageData> images;
    private List<CategoryData> categories;
    private String approvalStatus;
    private boolean purchasable;
    private boolean availableForPickup;
    private String thumbnailUrl;
    private String pictureUrl;
    private Double averageRating;
    private Integer numberOfReviews;
    private List<ProductData> variantOptions;
    private String variantType;
    private boolean multidimensional;

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getEan() { return ean; }
    public void setEan(String ean) { this.ean = ean; }
    public String getManufacturerName() { return manufacturerName; }
    public void setManufacturerName(String manufacturerName) { this.manufacturerName = manufacturerName; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public PriceData getPrice() { return price; }
    public void setPrice(PriceData price) { this.price = price; }
    public String getCurrencyIso() { return currencyIso; }
    public void setCurrencyIso(String currencyIso) { this.currencyIso = currencyIso; }
    public StockData getStock() { return stock; }
    public void setStock(StockData stock) { this.stock = stock; }
    public List<ImageData> getImages() { return images; }
    public void setImages(List<ImageData> images) { this.images = images; }
    public List<CategoryData> getCategories() { return categories; }
    public void setCategories(List<CategoryData> categories) { this.categories = categories; }
    public String getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(String approvalStatus) { this.approvalStatus = approvalStatus; }
    public boolean isPurchasable() { return purchasable; }
    public void setPurchasable(boolean purchasable) { this.purchasable = purchasable; }
    public boolean isAvailableForPickup() { return availableForPickup; }
    public void setAvailableForPickup(boolean availableForPickup) { this.availableForPickup = availableForPickup; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
    public String getPictureUrl() { return pictureUrl; }
    public void setPictureUrl(String pictureUrl) { this.pictureUrl = pictureUrl; }
    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
    public Integer getNumberOfReviews() { return numberOfReviews; }
    public void setNumberOfReviews(Integer numberOfReviews) { this.numberOfReviews = numberOfReviews; }
    public List<ProductData> getVariantOptions() { return variantOptions; }
    public void setVariantOptions(List<ProductData> variantOptions) { this.variantOptions = variantOptions; }
    public String getVariantType() { return variantType; }
    public void setVariantType(String variantType) { this.variantType = variantType; }
    public boolean isMultidimensional() { return multidimensional; }
    public void setMultidimensional(boolean multidimensional) { this.multidimensional = multidimensional; }
}
