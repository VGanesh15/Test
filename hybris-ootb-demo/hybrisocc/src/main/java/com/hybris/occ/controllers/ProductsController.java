package com.hybris.occ.controllers;

import com.hybris.facades.facades.ProductFacade;
import com.hybris.facades.data.ProductData;
import com.hybris.facades.data.SearchResultData;
import com.hybris.occ.dto.ResponseWsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Hybris OOTB: Products OCC REST Controller.
 * Mirrors de.hybris.platform.commercewebservices.core.v2.controller.ProductsController.
 *
 * Maps to URL: /occ/v2/{baseSiteId}/products
 *
 * OCC v2 Products API endpoints:
 * GET  /products/search          - Full-text search with pagination & facets
 * GET  /products/{productCode}   - Product details
 * GET  /products/featured        - Homepage featured products
 * GET  /products/{code}/similar  - Similar/recommended products
 */
@RestController
@RequestMapping("/occ/v2/{baseSiteId}/products")
@Tag(name = "Products", description = "SAP Commerce OCC v2 Products API")
public class ProductsController extends BaseController {

    private final ProductFacade productFacade;

    @Autowired
    public ProductsController(final ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @GetMapping("/search")
    @Operation(summary = "Full-text product search with facets and pagination")
    public ResponseEntity<ResponseWsDTO<SearchResultData<ProductData>>> searchProducts(
            @PathVariable final String baseSiteId,
            @RequestParam(required = false, defaultValue = "") final String query,
            @RequestParam(defaultValue = "0") final int currentPage,
            @RequestParam(defaultValue = "10") final int pageSize,
            @RequestParam(required = false) final String sort,
            @RequestParam(required = false) final String fields) {

        LOG.info("OCC /products/search: baseSite={}, query='{}', page={}, size={}",
            baseSiteId, query, currentPage, pageSize);

        final SearchResultData<ProductData> results;
        if (query == null || query.isBlank()) {
            results = productFacade.textSearch("", currentPage, pageSize);
            results.setResults(productFacade.getFeaturedProducts());
            results.setTotalResults(results.getResults() != null ? results.getResults().size() : 0);
        } else {
            results = productFacade.textSearch(query, currentPage, pageSize);
        }
        results.setFreeTextSearch(query);
        return success(results);
    }

    @GetMapping("/{productCode}")
    @Operation(summary = "Get product details by product code")
    public ResponseEntity<ResponseWsDTO<ProductData>> getProductByCode(
            @PathVariable final String baseSiteId,
            @PathVariable final String productCode,
            @RequestParam(required = false, defaultValue = "BASIC") final String fields) {

        LOG.info("OCC /products/{}: baseSite={}", productCode, baseSiteId);
        final ProductData product = productFacade.getProductForCode(productCode);
        return success(product);
    }

    @GetMapping("/featured")
    @Operation(summary = "Get featured products for homepage carousel")
    public ResponseEntity<ResponseWsDTO<List<ProductData>>> getFeaturedProducts(
            @PathVariable final String baseSiteId,
            @RequestParam(defaultValue = "8") final int count) {

        LOG.info("OCC /products/featured: baseSite={}, count={}", baseSiteId, count);
        final List<ProductData> featured = productFacade.getFeaturedProducts();
        return success(featured);
    }

    @GetMapping("/{productCode}/references")
    @Operation(summary = "Get product references (similar/recommended products)")
    public ResponseEntity<ResponseWsDTO<List<ProductData>>> getProductReferences(
            @PathVariable final String baseSiteId,
            @PathVariable final String productCode,
            @RequestParam(defaultValue = "SIMILAR") final String referenceType,
            @RequestParam(defaultValue = "4") final int limit) {

        LOG.info("OCC /products/{}/references: type={}", productCode, referenceType);
        final List<ProductData> similar = productFacade.getSimilarProducts(productCode, limit);
        return success(similar);
    }

    @GetMapping("/category/{categoryCode}")
    @Operation(summary = "Get products by category code")
    public ResponseEntity<ResponseWsDTO<SearchResultData<ProductData>>> getProductsByCategory(
            @PathVariable final String baseSiteId,
            @PathVariable final String categoryCode,
            @RequestParam(defaultValue = "0") final int currentPage,
            @RequestParam(defaultValue = "12") final int pageSize) {

        LOG.info("OCC /products/category/{}: baseSite={}", categoryCode, baseSiteId);
        final SearchResultData<ProductData> results = productFacade.categorySearch(
            categoryCode, currentPage, pageSize);
        return success(results);
    }
}
