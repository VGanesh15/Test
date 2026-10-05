package com.hybris.core.service;

import com.hybris.core.dao.CategoryDao;
import com.hybris.core.model.CategoryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * Hybris OOTB: DefaultCategoryService.
 * Mirrors de.hybris.platform.category.impl.DefaultCategoryService.
 *
 * Category trees are cached aggressively since they change rarely.
 * Cache invalidation should happen via CMS synchronization or impex import.
 */
@Service("categoryService")
@Transactional(readOnly = true)
public class DefaultCategoryService implements CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultCategoryService.class);

    private final CategoryDao categoryDao;

    @Autowired
    public DefaultCategoryService(final CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    @Cacheable(value = "categories", key = "#code")
    public Optional<CategoryModel> getCategoryForCode(final String code) {
        Assert.hasText(code, "Category code must not be blank");
        final List<CategoryModel> categories = categoryDao.findByCode(code);
        return categories.isEmpty() ? Optional.empty() : Optional.of(categories.get(0));
    }

    @Override
    @Cacheable(value = "rootCategories", key = "#catalogVersionPk")
    public List<CategoryModel> getRootCategories(final String catalogVersionPk) {
        Assert.hasText(catalogVersionPk, "CatalogVersion PK must not be blank");
        return categoryDao.findRootCategoriesByCatalogVersion(catalogVersionPk);
    }

    @Override
    @Cacheable(value = "subcategories", key = "#parentCategoryCode")
    public List<CategoryModel> getSubcategories(final String parentCategoryCode) {
        Assert.hasText(parentCategoryCode, "Parent category code must not be blank");
        return categoryDao.findBySupercategoryCode(parentCategoryCode);
    }

    @Override
    public List<CategoryModel> getAllCategoriesForCatalogVersion(final String catalogVersionPk) {
        Assert.hasText(catalogVersionPk, "CatalogVersion PK must not be blank");
        return categoryDao.findByCatalogVersionPk(catalogVersionPk);
    }

    @Override
    @Transactional
    public CategoryModel saveCategory(final CategoryModel category) {
        Assert.notNull(category, "Category must not be null");
        Assert.hasText(category.getCode(), "Category code must not be blank");
        LOG.info("Saving category: {}", category.getCode());
        return categoryDao.save(category);
    }

    @Override
    @Transactional
    public void removeCategory(final String code) {
        Assert.hasText(code, "Category code must not be blank");
        final Optional<CategoryModel> cat = getCategoryForCode(code);
        cat.ifPresent(c -> {
            LOG.info("Removing category: {}", code);
            categoryDao.delete(c);
        });
    }
}
