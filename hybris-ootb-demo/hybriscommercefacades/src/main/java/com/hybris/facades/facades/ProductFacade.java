package com.hybris.facades.facades;

import com.hybris.facades.data.ProductData;
import com.hybris.facades.data.SearchResultData;

import java.util.List;

/**
 * Hybris OOTB: ProductFacade interface.
 * Mirrors de.hybris.platform.commercefacades.product.ProductFacade.
 *
 * The facade layer is the boundary between the web tier and the business logic.
 * Web controllers (storefront and OCC) only interact with facades, never with
 * core services directly. This enables testability and clean layering.
 */
public interface ProductFacade {

    ProductData getProductForCode(String productCode);

    SearchResultData<ProductData> textSearch(String searchText, int page, int pageSize);

    SearchResultData<ProductData> categorySearch(String categoryCode, int page, int pageSize);

    List<ProductData> getFeaturedProducts();

    List<ProductData> getSimilarProducts(String productCode, int limit);

    ProductData getProductForCodeAndOptions(String code, List<String> options);
}
