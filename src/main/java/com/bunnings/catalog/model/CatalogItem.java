package com.bunnings.catalog.model;

public class CatalogItem {
    private final String company;
    private final SupplierProduct supplierProduct;
    private final Supplier supplier;
    private final Product product;

    public CatalogItem(String company, SupplierProduct supplierProduct, Supplier supplier, Product product) {
        this.company = company;
        this.supplierProduct = supplierProduct;
        this.supplier = supplier;
        this.product = product;
    }

    public String getCompany() {
        return company;
    }

    public SupplierProduct getSupplierProduct() {
        return supplierProduct;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Product getProduct() {
        return product;
    }
}
