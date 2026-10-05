package com.hybris.facades.converters;

/**
 * Hybris OOTB: Populator interface.
 * Mirrors de.hybris.platform.converters.Populator.
 *
 * Each Populator fills a specific aspect of the target DTO.
 * Multiple Populators compose to form a Converter.
 *
 * Common Hybris OOTB Populators for Product:
 * - ProductBasicPopulator (code, name, description)
 * - ProductPricePopulator (price, formattedPrice)
 * - ProductStockPopulator (stockLevel, inStock status)
 * - ProductCategoryPopulator (supercategories)
 * - ProductGalleryImagesPopulator (gallery images)
 * - ProductVariantMatrixPopulator (variant options grid)
 */
public interface Populator<SOURCE, TARGET> {

    void populate(SOURCE source, TARGET target);
}
