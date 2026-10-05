package com.hybris.facades.data;

/**
 * Hybris OOTB: ImageData DTO.
 * Mirrors de.hybris.platform.commercefacades.product.data.ImageData.
 * Represents a media item resolved through the MediaContainerConverter.
 */
public class ImageData {

    public enum ImageType {
        PRIMARY, GALLERY
    }

    private String url;
    private String altText;
    private Integer galleryIndex;
    private ImageType imageType;
    private String format;
    private Integer width;

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getAltText() { return altText; }
    public void setAltText(String altText) { this.altText = altText; }
    public Integer getGalleryIndex() { return galleryIndex; }
    public void setGalleryIndex(Integer galleryIndex) { this.galleryIndex = galleryIndex; }
    public ImageType getImageType() { return imageType; }
    public void setImageType(ImageType imageType) { this.imageType = imageType; }
    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }
    public Integer getWidth() { return width; }
    public void setWidth(Integer width) { this.width = width; }
}
