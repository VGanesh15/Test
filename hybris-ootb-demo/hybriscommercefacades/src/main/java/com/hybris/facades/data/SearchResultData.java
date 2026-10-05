package com.hybris.facades.data;

import java.util.List;

/**
 * Hybris OOTB: ProductSearchPageData DTO.
 * Mirrors de.hybris.platform.commerceservices.search.pagedata.ProductSearchPageData.
 * Wraps search results with pagination and facets (SOLR-driven in real Hybris).
 */
public class SearchResultData<T> {

    private String freeTextSearch;
    private CategoryData currentCategory;
    private List<T> results;
    private int totalResults;
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private List<FacetData> facets;
    private List<BreadcrumbData> breadcrumbs;
    private List<String> spellingSuggestions;
    private String categoryCode;

    public String getFreeTextSearch() { return freeTextSearch; }
    public void setFreeTextSearch(String freeTextSearch) { this.freeTextSearch = freeTextSearch; }
    public CategoryData getCurrentCategory() { return currentCategory; }
    public void setCurrentCategory(CategoryData currentCategory) { this.currentCategory = currentCategory; }
    public List<T> getResults() { return results; }
    public void setResults(List<T> results) { this.results = results; }
    public int getTotalResults() { return totalResults; }
    public void setTotalResults(int totalResults) { this.totalResults = totalResults; }
    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }
    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }
    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
    public List<FacetData> getFacets() { return facets; }
    public void setFacets(List<FacetData> facets) { this.facets = facets; }
    public List<BreadcrumbData> getBreadcrumbs() { return breadcrumbs; }
    public void setBreadcrumbs(List<BreadcrumbData> breadcrumbs) { this.breadcrumbs = breadcrumbs; }
    public List<String> getSpellingSuggestions() { return spellingSuggestions; }
    public void setSpellingSuggestions(List<String> spellingSuggestions) { this.spellingSuggestions = spellingSuggestions; }
    public String getCategoryCode() { return categoryCode; }
    public void setCategoryCode(String categoryCode) { this.categoryCode = categoryCode; }
}
