package com.hybris.facades.data;

import java.util.List;

/** Hybris OOTB: FacetData DTO for SOLR faceted search. */
public class FacetData {
    private String code;
    private String name;
    private int priority;
    private boolean multiSelect;
    private boolean visible;
    private List<FacetValueData> values;
    private String topValues;
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
    public boolean isMultiSelect() { return multiSelect; }
    public void setMultiSelect(boolean multiSelect) { this.multiSelect = multiSelect; }
    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }
    public List<FacetValueData> getValues() { return values; }
    public void setValues(List<FacetValueData> values) { this.values = values; }
    public String getTopValues() { return topValues; }
    public void setTopValues(String topValues) { this.topValues = topValues; }
}
