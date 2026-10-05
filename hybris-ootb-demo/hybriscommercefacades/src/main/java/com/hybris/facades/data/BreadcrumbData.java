package com.hybris.facades.data;

/** Hybris OOTB: BreadcrumbData DTO. */
public class BreadcrumbData {
    private String facetCode;
    private String facetName;
    private String facetValueCode;
    private String facetValueName;
    private String removeQuery;
    private String truncateQuery;
    public String getFacetCode() { return facetCode; }
    public void setFacetCode(String facetCode) { this.facetCode = facetCode; }
    public String getFacetName() { return facetName; }
    public void setFacetName(String facetName) { this.facetName = facetName; }
    public String getFacetValueCode() { return facetValueCode; }
    public void setFacetValueCode(String facetValueCode) { this.facetValueCode = facetValueCode; }
    public String getFacetValueName() { return facetValueName; }
    public void setFacetValueName(String facetValueName) { this.facetValueName = facetValueName; }
    public String getRemoveQuery() { return removeQuery; }
    public void setRemoveQuery(String removeQuery) { this.removeQuery = removeQuery; }
    public String getTruncateQuery() { return truncateQuery; }
    public void setTruncateQuery(String truncateQuery) { this.truncateQuery = truncateQuery; }
}
