package com.hybris.facades.converters;

import com.hybris.core.model.CategoryModel;
import com.hybris.core.model.ProductModel;
import com.hybris.facades.data.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Hybris OOTB: ProductConverter.
 * Mirrors de.hybris.platform.commercefacades.converter.impl.DefaultProductConverter.
 *
 * In real Hybris, this is a populatingConverter bean with injected Populators.
 * Each Populator is independently unit-testable and configurable per extension.
 * Extensions override the populator list via Spring XML with <list merge="true">.
 */
@Component("productConverter")
public class ProductConverter implements Converter<ProductModel, ProductData> {

    @Override
    public ProductData convert(final ProductModel source) {
        return convert(source, new ProductData());
    }

    @Override
    public ProductData convert(final ProductModel source, final ProductData target) {
        if (source == null) return target;

        // Basic populator
        target.setCode(source.getCode());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setSummary(source.getSummary());
        target.setEan(source.getEan());
        target.setManufacturerName(source.getManufacturerName());
        target.setPurchasable(source.isPurchasable());
        target.setAvailableForPickup(source.isAvailableForPickup());
        target.setApprovalStatus(source.getApprovalStatus() != null
            ? source.getApprovalStatus().name() : null);
        target.setThumbnailUrl(source.getThumbnailUrl());
        target.setPictureUrl(source.getPictureUrl());
        target.setUrl("/p/" + source.getCode());

        // Price populator
        if (source.getPrice() != null) {
            final PriceData priceData = new PriceData(source.getPrice(),
                source.getCurrencyIso() != null ? source.getCurrencyIso() : "USD");
            target.setPrice(priceData);
            target.setCurrencyIso(source.getCurrencyIso());
        }

        // Stock populator
        final StockData stockData = new StockData();
        if (source.getStockLevel() == null || source.getStockLevel() == 0) {
            stockData.setStockLevelStatus(StockData.StockLevelStatus.outOfStock);
            stockData.setStockLevel(0L);
        } else if (source.getStockLevel() < 5) {
            stockData.setStockLevelStatus(StockData.StockLevelStatus.lowStock);
            stockData.setStockLevel(source.getStockLevel().longValue());
        } else {
            stockData.setStockLevelStatus(StockData.StockLevelStatus.inStock);
            stockData.setStockLevel(source.getStockLevel().longValue());
        }
        target.setStock(stockData);

        // Category populator
        if (source.getSupercategories() != null && !source.getSupercategories().isEmpty()) {
            final List<CategoryData> categories = new ArrayList<>();
            for (final CategoryModel cat : source.getSupercategories()) {
                final CategoryData catData = new CategoryData();
                catData.setCode(cat.getCode());
                catData.setName(cat.getName());
                catData.setUrl("/c/" + cat.getCode());
                categories.add(catData);
            }
            target.setCategories(categories);
        }

        // Gallery images populator
        if (source.getThumbnailUrl() != null || source.getPictureUrl() != null) {
            final List<ImageData> images = new ArrayList<>();
            if (source.getThumbnailUrl() != null) {
                final ImageData thumb = new ImageData();
                thumb.setUrl(source.getThumbnailUrl());
                thumb.setAltText(source.getName());
                thumb.setImageType(ImageData.ImageType.PRIMARY);
                thumb.setFormat("thumbnail");
                images.add(thumb);
            }
            if (source.getPictureUrl() != null) {
                final ImageData main = new ImageData();
                main.setUrl(source.getPictureUrl());
                main.setAltText(source.getName());
                main.setImageType(ImageData.ImageType.PRIMARY);
                main.setFormat("product");
                images.add(main);
            }
            target.setImages(images);
        }

        target.setAverageRating(4.2);
        target.setNumberOfReviews(0);

        return target;
    }
}
