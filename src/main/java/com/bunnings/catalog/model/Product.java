package com.bunnings.catalog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Objects;

@JsonPropertyOrder({"SKU", "Description"})
public class Product {
    @JsonProperty("SKU")
    private String sku;
    @JsonProperty("Description")
    private String description;

    public Product() {
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
