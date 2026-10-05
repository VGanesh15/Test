package com.hybris.facades.data;

/** Hybris OOTB: FacetValueData DTO. */
public class FacetValueData {
    private String code;
    private String name;
    private long count;
    private boolean selected;
    private String query;
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public long getCount() { return count; }
    public void setCount(long count) { this.count = count; }
    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }
    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }
}
