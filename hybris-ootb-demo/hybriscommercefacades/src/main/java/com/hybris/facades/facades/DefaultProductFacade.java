package com.hybris.facades.facades;

import com.hybris.core.model.ProductModel;
import com.hybris.core.service.CategoryService;
import com.hybris.core.service.ProductService;
import com.hybris.facades.converters.ProductConverter;
import com.hybris.facades.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hybris OOTB: DefaultProductFacade.
 * Mirrors de.hybris.platform.commercefacades.product.impl.DefaultProductFacade.
 *
 * Facade implementation delegates to ProductService + Converter.
 * Returns DTOs, never domain models.
 */
@Service("productFacade")
public class DefaultProductFacade implements ProductFacade {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultProductFacade.class);

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductConverter productConverter;

    @Autowired
    public DefaultProductFacade(final ProductService productService,
                                 final CategoryService categoryService,
                                 final ProductConverter productConverter) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productConverter = productConverter;
    }

    @Override
    public ProductData getProductForCode(final String productCode) {
        LOG.debug("ProductFacade: getProductForCode({})", productCode);
        final ProductModel product = productService.getProductForCode(productCode);
        return productConverter.convert(product);
    }

    @Override
    public SearchResultData<ProductData> textSearch(final String searchText, final int page, final int pageSize) {
        LOG.debug("ProductFacade: textSearch({}, page={}, size={})", searchText, page, pageSize);
        final List<ProductModel> allResults = (searchText == null || searchText.isBlank())
                ? productService.getAllApprovedProducts()
                : productService.searchProducts(searchText);
        return paginateAndWrap(allResults, searchText, null, page, pageSize);
    }

    @Override
    public SearchResultData<ProductData> categorySearch(final String categoryCode, final int page, final int pageSize) {
        LOG.debug("ProductFacade: categorySearch({}, page={}, size={})", categoryCode, page, pageSize);
        final List<ProductModel> allResults = productService.getProductsForCategory(categoryCode);

        final SearchResultData<ProductData> result = paginateAndWrap(allResults, null, categoryCode, page, pageSize);

        categoryService.getCategoryForCode(categoryCode).ifPresent(cat -> {
            final CategoryData catData = new CategoryData();
            catData.setCode(cat.getCode());
            catData.setName(cat.getName());
            catData.setUrl("/c/" + cat.getCode());
            result.setCurrentCategory(catData);
            result.setCategoryCode(categoryCode);

            final List<BreadcrumbData> breadcrumbs = new ArrayList<>();
            final BreadcrumbData crumb = new BreadcrumbData();
            crumb.setFacetCode("category");
            crumb.setFacetValueCode(categoryCode);
            crumb.setFacetValueName(cat.getName());
            breadcrumbs.add(crumb);
            result.setBreadcrumbs(breadcrumbs);
        });

        return result;
    }

    @Override
    public List<ProductData> getFeaturedProducts() {
        return productService.getFeaturedProducts(8).stream()
            .map(productConverter::convert)
            .collect(Collectors.toList());
    }

    @Override
    public List<ProductData> getSimilarProducts(final String productCode, final int limit) {
        try {
            final ProductModel product = productService.getProductForCode(productCode);
            if (product.getSupercategories() != null && !product.getSupercategories().isEmpty()) {
                final String catCode = product.getSupercategories().get(0).getCode();
                return productService.getProductsForCategory(catCode).stream()
                    .filter(p -> !p.getCode().equals(productCode))
                    .limit(limit)
                    .map(productConverter::convert)
                    .collect(Collectors.toList());
            }
        } catch (Exception e) {
            LOG.warn("Could not load similar products for {}: {}", productCode, e.getMessage());
        }
        return List.of();
    }

    @Override
    public ProductData getProductForCodeAndOptions(final String code, final List<String> options) {
        return getProductForCode(code);
    }

    private SearchResultData<ProductData> paginateAndWrap(final List<ProductModel> allResults,
                                                            final String searchText,
                                                            final String categoryCode,
                                                            final int page, final int pageSize) {
        final int totalResults = allResults.size();
        final int fromIndex = Math.min(page * pageSize, totalResults);
        final int toIndex = Math.min(fromIndex + pageSize, totalResults);
        final List<ProductModel> pageResults = allResults.subList(fromIndex, toIndex);

        final SearchResultData<ProductData> result = new SearchResultData<>();
        result.setFreeTextSearch(searchText);
        result.setCategoryCode(categoryCode);
        result.setTotalResults(totalResults);
        result.setCurrentPage(page);
        result.setPageSize(pageSize);
        result.setTotalPages(pageSize > 0 ? (int) Math.ceil((double) totalResults / pageSize) : 0);
        result.setResults(pageResults.stream()
            .map(productConverter::convert)
            .collect(Collectors.toList()));

        // Synthetic price-range facets with real counts from all results
        final BigDecimal t1 = new BigDecimal("500");
        final BigDecimal t2 = new BigDecimal("1000");
        final BigDecimal t3 = new BigDecimal("2000");
        long c1 = allResults.stream().filter(p -> p.getPrice() != null && p.getPrice().compareTo(t1) < 0).count();
        long c2 = allResults.stream().filter(p -> p.getPrice() != null && p.getPrice().compareTo(t1) >= 0 && p.getPrice().compareTo(t2) < 0).count();
        long c3 = allResults.stream().filter(p -> p.getPrice() != null && p.getPrice().compareTo(t2) >= 0 && p.getPrice().compareTo(t3) < 0).count();
        long c4 = allResults.stream().filter(p -> p.getPrice() != null && p.getPrice().compareTo(t3) >= 0).count();

        final List<FacetData> facets = new ArrayList<>();
        final FacetData priceFacet = new FacetData();
        priceFacet.setCode("price");
        priceFacet.setName("Price");
        priceFacet.setVisible(true);
        priceFacet.setMultiSelect(false);
        final List<FacetValueData> priceValues = new ArrayList<>();
        if (c1 > 0) priceValues.add(buildFacetValue("under500", "Under $500", c1));
        if (c2 > 0) priceValues.add(buildFacetValue("500to1000", "$500 – $1,000", c2));
        if (c3 > 0) priceValues.add(buildFacetValue("1000to2000", "$1,000 – $2,000", c3));
        if (c4 > 0) priceValues.add(buildFacetValue("over2000", "Over $2,000", c4));
        priceFacet.setValues(priceValues);
        facets.add(priceFacet);
        result.setFacets(facets);

        return result;
    }

    private FacetValueData buildFacetValue(final String code, final String name, final long count) {
        final FacetValueData fv = new FacetValueData();
        fv.setCode(code);
        fv.setName(name);
        fv.setCount(count);
        return fv;
    }
}
