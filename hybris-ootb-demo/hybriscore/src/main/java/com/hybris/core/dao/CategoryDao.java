package com.hybris.core.dao;

import com.hybris.core.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Hybris OOTB pattern: CategoryDao.
 * Mirrors de.hybris.platform.category.daos.CategoryDao.
 *
 * In Hybris, category hierarchy is stored in CategoryCategoryRelation.
 * The NavigationComponent and CategoryPageComponent rely on this for navigation.
 */
@Repository
public interface CategoryDao extends JpaRepository<CategoryModel, String> {

    Optional<CategoryModel> findByCodeAndCatalogVersionPk(String code, String catalogVersionPk);

    List<CategoryModel> findByCode(String code);

    /**
     * Find root categories (no supercategory) for a given catalog version.
     * Used by the navigation component to build the top-level menu.
     */
    @Query("SELECT c FROM CategoryModel c WHERE c.supercategory IS NULL AND c.catalogVersion.pk = :cvPk")
    List<CategoryModel> findRootCategoriesByCatalogVersion(@Param("cvPk") String catalogVersionPk);

    /**
     * Find direct subcategories. The Category navigation component calls this
     * recursively to build the full category tree.
     */
    List<CategoryModel> findBySupercategoryCode(String parentCategoryCode);

    /**
     * Find all categories in a catalog version.
     */
    List<CategoryModel> findByCatalogVersionPk(String catalogVersionPk);
}
