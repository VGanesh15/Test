package com.hybris.core.dao;

import com.hybris.core.model.CatalogVersionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Hybris OOTB pattern: CatalogVersionDao.
 * Mirrors de.hybris.platform.catalog.daos.CatalogVersionDao.
 *
 * The CatalogVersionService uses this to manage the "session catalog versions" -
 * the context that determines product visibility throughout the platform.
 */
@Repository
public interface CatalogVersionDao extends JpaRepository<CatalogVersionModel, String> {

    @Query("SELECT cv FROM CatalogVersionModel cv WHERE cv.catalog.id = :catalogId AND cv.versionLabel = :version")
    Optional<CatalogVersionModel> findByCatalogIdAndVersion(
        @Param("catalogId") String catalogId,
        @Param("version") String version);

    List<CatalogVersionModel> findByActive(boolean active);

    @Query("SELECT cv FROM CatalogVersionModel cv WHERE cv.catalog.id = :catalogId")
    List<CatalogVersionModel> findByCatalogId(@Param("catalogId") String catalogId);

    @Query("SELECT cv FROM CatalogVersionModel cv WHERE cv.active = true")
    List<CatalogVersionModel> findAllActiveCatalogVersions();
}
