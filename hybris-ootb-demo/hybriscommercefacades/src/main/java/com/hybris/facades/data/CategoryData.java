package com.hybris.facades.data;

import java.util.List;

/**
 * Hybris OOTB: CategoryData DTO.
 * Mirrors de.hybris.platform.commercefacades.product.data.CategoryData.
 */
public class CategoryData {

    private String code;
    private String name;
    private String description;
    private String url;
    private ImageData image;
    private List<CategoryData> subcategories;
    private int productCount;

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public ImageData getImage() { return image; }
    public void setImage(ImageData image) { this.image = image; }
    public List<CategoryData> getSubcategories() { return subcategories; }
    public void setSubcategories(List<CategoryData> subcategories) { this.subcategories = subcategories; }
    public int getProductCount() { return productCount; }
    public void setProductCount(int productCount) { this.productCount = productCount; }
}
