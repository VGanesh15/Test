package com.hybris.core.service;

import com.hybris.core.model.ProductModel;

import java.util.List;
import java.util.Optional;

/**
 * Hybris OOTB: ProductService interface.
 * Mirrors de.hybris.platform.product.ProductService.
 * Provides product lookup and management operations used across all extensions.
 */
public interface ProductService {

    /**
     * Get product by code within the current session catalog version context.
     * Throws ModelNotFoundException if not found.
     */
    ProductModel getProductForCode(String code);

    /**
     * Get product by code within a specific catalog version.
     */
    Optional<ProductModel> getProductForCode(String code, String catalogVersionPk);

    /**
     * Full-text search. In real Hybris, this delegates to SOLR via
     * ProductSearchService / SolrProductSearchStrategy.
     */
    List<ProductModel> searchProducts(String searchTerm);

    /**
     * Get products by category code.
     */
    List<ProductModel> getProductsForCategory(String categoryCode);

    /**
     * Get all approved products.
     */
    List<ProductModel> getAllApprovedProducts();

    /**
     * Get featured products for the homepage carousel.
     */
    List<ProductModel> getFeaturedProducts(int maxResults);

    /**
     * Save product model. Triggers PrepareInterceptors and ValidateInterceptors.
     */
    ProductModel saveProduct(ProductModel product);

    /**
     * Delete product. Triggers RemoveInterceptors.
     */
    void removeProduct(String code);

    /**
     * Update stock level. Thread-safe - uses optimistic locking via @Version.
     */
    ProductModel updateStockLevel(String productCode, int delta);
}
