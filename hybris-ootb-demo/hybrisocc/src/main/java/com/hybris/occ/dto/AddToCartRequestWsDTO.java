package com.hybris.occ.dto;

/** OCC request DTO for adding a product to cart. */
public class AddToCartRequestWsDTO {
    private String productCode;
    private long quantity = 1;
    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }
    public long getQuantity() { return quantity; }
    public void setQuantity(long quantity) { this.quantity = quantity; }
}
