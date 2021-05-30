package com.bunnings.catalog;

import com.bunnings.catalog.model.CatalogItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"SKU", "Description", "Source"})
public class Item {

    private final CatalogItem item;

    public Item(CatalogItem item) {
        this.item = item;
    }

    @JsonProperty("SKU")
    public String getSku() {
        return item.getProduct().getSku();
    }

    @JsonProperty("Description")
    public String getDescription() {
        return item.getProduct().getDescription();
    }

    @JsonProperty("Source")
    public String getSource() {
        return item.getCompany();
    }
}
