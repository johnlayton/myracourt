package com.bunnings.catalog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"SupplierID", "SKU", "Barcode"})
public class SupplierProduct {
    @JsonProperty("SupplierID")
    private String supplier;
    @JsonProperty("SKU")
    private String sku;
    @JsonProperty("Barcode")
    private String barcode;

    public SupplierProduct() {
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

}
