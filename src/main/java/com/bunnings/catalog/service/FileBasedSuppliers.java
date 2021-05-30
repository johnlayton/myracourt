package com.bunnings.catalog.service;

import com.bunnings.catalog.model.Supplier;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileBasedSuppliers extends CSVSource<Supplier> implements Source<Supplier> {

    private final Path path;

    public FileBasedSuppliers(Path path) {
        super(Supplier.class);
        this.path = path;
    }

    @Override
    public Stream<Supplier> get() throws IOException {
        return get(Files.newInputStream(path));
    }
}
