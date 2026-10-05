package com.hybris.core.service;

import com.hybris.core.dao.ProductDao;
import com.hybris.core.model.ProductModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Hybris OOTB: DefaultProductService.
 * Mirrors de.hybris.platform.product.impl.DefaultProductService.
 *
 * Caching mirrors Hybris ModelService second-level cache (L2 cache region).
 * In Hybris, models are cached in the hybris cache region "model_region".
 */
@Service("productService")
@Transactional(readOnly = true)
public class DefaultProductService implements ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultProductService.class);

    private final ProductDao productDao;

    @Autowired
    public DefaultProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    @Cacheable(value = "products", key = "#code")
    public ProductModel getProductForCode(final String code) {
        Assert.hasText(code, "Product code must not be blank");
        LOG.debug("Loading product by code: {}", code);
        final List<ProductModel> products = productDao.findByCode(code);
        if (products.isEmpty()) {
            throw new NoSuchElementException("No product found with code: " + code);
        }
        return products.get(0);
    }

    @Override
    public Optional<ProductModel> getProductForCode(final String code, final String catalogVersionPk) {
        Assert.hasText(code, "Product code must not be blank");
        Assert.hasText(catalogVersionPk, "CatalogVersion PK must not be blank");
        return productDao.findByCodeAndCatalogVersionPk(code, catalogVersionPk);
    }

    @Override
    public List<ProductModel> searchProducts(final String searchTerm) {
        Assert.hasText(searchTerm, "Search term must not be blank");
        LOG.debug("Searching products for term: {}", searchTerm);
        return productDao.searchByTerm(searchTerm.trim());
    }

    @Override
    public List<ProductModel> getProductsForCategory(final String categoryCode) {
        Assert.hasText(categoryCode, "Category code must not be blank");
        LOG.debug("Getting products for category: {}", categoryCode);
        return productDao.findByCategoryCode(categoryCode);
    }

    @Override
    @Cacheable(value = "approvedProducts")
    public List<ProductModel> getAllApprovedProducts() {
        LOG.debug("Fetching all approved products");
        return productDao.findAll().stream()
            .filter(p -> p.getApprovalStatus() == ProductModel.ApprovalStatus.APPROVED)
            .toList();
    }

    @Override
    public List<ProductModel> getFeaturedProducts(final int maxResults) {
        LOG.debug("Fetching featured products, maxResults={}", maxResults);
        return productDao.findFeaturedProducts(PageRequest.of(0, maxResults));
    }

    @Override
    @Transactional
    @CacheEvict(value = {"products", "approvedProducts"}, allEntries = true)
    public ProductModel saveProduct(final ProductModel product) {
        Assert.notNull(product, "Product model must not be null");
        Assert.hasText(product.getCode(), "Product code must not be blank");
        LOG.info("Saving product: {}", product.getCode());
        return productDao.save(product);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"products", "approvedProducts"}, allEntries = true)
    public void removeProduct(final String code) {
        Assert.hasText(code, "Product code must not be blank");
        final ProductModel product = getProductForCode(code);
        LOG.info("Removing product: {}", code);
        productDao.delete(product);
    }

    @Override
    @Transactional
    @CacheEvict(value = "products", key = "#productCode")
    public ProductModel updateStockLevel(final String productCode, final int delta) {
        Assert.hasText(productCode, "Product code must not be blank");
        final ProductModel product = getProductForCode(productCode);
        final int newLevel = (product.getStockLevel() == null ? 0 : product.getStockLevel()) + delta;
        if (newLevel < 0) {
            throw new IllegalStateException("Insufficient stock for product: " + productCode);
        }
        product.setStockLevel(newLevel);
        LOG.info("Updated stock level for {} to {}", productCode, newLevel);
        return productDao.save(product);
    }
}
