package com.bunnings.catalog.service;

import com.bunnings.catalog.model.Product;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileBasedProducts extends CSVSource<Product> implements Source<Product> {
    private final Path path;

    public FileBasedProducts(Path path) {
        super(Product.class);
        this.path = path;
    }

    @Override
    public Stream<Product> get() throws IOException {
        return get(Files.newInputStream(path));
    }
}
