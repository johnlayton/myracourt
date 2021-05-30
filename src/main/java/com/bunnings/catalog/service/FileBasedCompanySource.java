package com.bunnings.catalog.service;

import com.bunnings.catalog.model.Product;
import com.bunnings.catalog.model.Supplier;
import com.bunnings.catalog.model.SupplierProduct;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileBasedCompanySource implements CompanySource {

    private final String basedir;
    private final String company;

    public FileBasedCompanySource(String basedir, String company) {
        this.basedir = basedir;
        this.company = company;
    }

    @Override
    public String identifier() {
        return company;
    }

    @Override
    public Stream<Supplier> suppliers() throws IOException {
        return new FileBasedSuppliers(Path.of(basedir, String.format("suppliers%s.csv", company))).get();
    }

    @Override
    public Stream<Product> products() throws IOException {
        return new FileBasedProducts(Path.of(basedir, String.format("catalog%s.csv", company))).get();
    }

    @Override
    public Stream<SupplierProduct> supplierProducts() throws IOException {
        return new FileBasedSupplierProducts(Path.of(basedir, String.format("barcodes%s.csv", company))).get();
    }
}
