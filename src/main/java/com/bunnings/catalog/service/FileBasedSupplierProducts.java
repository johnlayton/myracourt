package com.bunnings.catalog.service;

import com.bunnings.catalog.model.SupplierProduct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileBasedSupplierProducts extends CSVSource<SupplierProduct> implements Source<SupplierProduct> {
    private final Path path;

    public FileBasedSupplierProducts(Path path) {
        super(SupplierProduct.class);
        this.path = path;
    }

    @Override
    public Stream<SupplierProduct> get() throws IOException {
        return get(Files.newInputStream(path));
    }
}
