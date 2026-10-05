package com.hybris.core.service;

import com.hybris.core.model.CategoryModel;

import java.util.List;
import java.util.Optional;

/**
 * Hybris OOTB: CategoryService interface.
 * Mirrors de.hybris.platform.category.CategoryService.
 *
 * Categories support multi-level hierarchy used for navigation and product classification.
 * The storefront navigation component and breadcrumb builder depend on this service.
 */
public interface CategoryService {

    Optional<CategoryModel> getCategoryForCode(String code);

    List<CategoryModel> getRootCategories(String catalogVersionPk);

    List<CategoryModel> getSubcategories(String parentCategoryCode);

    List<CategoryModel> getAllCategoriesForCatalogVersion(String catalogVersionPk);

    CategoryModel saveCategory(CategoryModel category);

    void removeCategory(String code);
}
