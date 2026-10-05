package com.hybris.core.dao;

import com.hybris.core.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Hybris OOTB pattern: ProductDao.
 * Mirrors de.hybris.platform.product.daos.ProductDao.
 * Uses Spring Data JPA as a stand-in for Hybris FlexibleSearch.
 *
 * In real Hybris, queries are written in FlexibleSearch (FS):
 *   SELECT {p:PK} FROM {Product AS p} WHERE {p:code} = ?code
 *
 * FlexibleSearch is compiled to SQL at runtime by the platform,
 * respecting catalog version visibility and item type hierarchies.
 */
@Repository
public interface ProductDao extends JpaRepository<ProductModel, String> {

    /**
     * Hybris FS equivalent:
     * SELECT {p:PK} FROM {Product AS p} WHERE {p:code} = ?code
     *   AND {p:catalogVersion} = ?catalogVersion
     */
    Optional<ProductModel> findByCodeAndCatalogVersionPk(String code, String catalogVersionPk);

    /**
     * FlexibleSearch:
     * SELECT {p:PK} FROM {Product AS p} WHERE {p:catalogVersion} = ?cv
     *   AND {p:approvalStatus} = 'approved'
     */
    @Query("SELECT p FROM ProductModel p WHERE p.catalogVersion.pk = :cvPk AND p.approvalStatus = 'APPROVED'")
    List<ProductModel> findApprovedProductsByCatalogVersion(@Param("cvPk") String catalogVersionPk);

    /**
     * Full-text search simulation. In real Hybris this uses SOLR via
     * de.hybris.platform.commerceservices.search.solrfacetsearch.
     */
    @Query("SELECT p FROM ProductModel p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :term, '%')) " +
           "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :term, '%')) " +
           "OR LOWER(p.code) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<ProductModel> searchByTerm(@Param("term") String searchTerm);

    /**
     * Find products by category code.
     */
    @Query("SELECT p FROM ProductModel p JOIN p.supercategories c WHERE c.code = :categoryCode")
    List<ProductModel> findByCategoryCode(@Param("categoryCode") String categoryCode);

    /**
     * Find by code across all catalog versions (used in OCC APIs).
     */
    List<ProductModel> findByCode(String code);

    /**
     * Find all products for a given manufacturer.
     */
    List<ProductModel> findByManufacturerName(String manufacturerName);

    /**
     * Find products with stock below threshold - used for stock alerts.
     */
    @Query("SELECT p FROM ProductModel p WHERE p.stockLevel < :threshold AND p.purchasable = true")
    List<ProductModel> findProductsWithLowStock(@Param("threshold") int threshold);

    /**
     * Count products per category - used for navigation facets.
     */
    @Query("SELECT c.code, COUNT(p) FROM ProductModel p JOIN p.supercategories c GROUP BY c.code")
    List<Object[]> countProductsPerCategory();

    /**
     * Find featured/top-selling products. In real Hybris this would use
     * the ProductReferences type with reference type SIMILAR or UPSELL.
     */
    @Query("SELECT p FROM ProductModel p WHERE p.approvalStatus = 'APPROVED' " +
           "AND p.purchasable = true ORDER BY p.createdTs DESC")
    List<ProductModel> findFeaturedProducts(org.springframework.data.domain.Pageable pageable);

    /**
     * Exists check by code and catalog version.
     */
    boolean existsByCodeAndCatalogVersionPk(String code, String catalogVersionPk);
}
