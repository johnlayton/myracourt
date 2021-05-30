package com.bunnings.catalog.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.bunnings.catalog.model.Product;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class FileBasedProductsTest {

    @Test
    public void shouldReadProductsFile() throws Exception {
        final List<Product> products = new FileBasedProducts(new File(testResources(), "catalog.csv").toPath())
            .get().collect(Collectors.toList());
        assertThat(products.size()).isEqualTo(1);
        assertThat(products.get(0).getSku()).isEqualTo("AAA-BBB-CCC");
        assertThat(products.get(0).getDescription()).isEqualTo("Test Description");
    }

    @Test
    public void shouldThrowIOExceptionForMissingProductsFile() throws Exception {
        assertThrows(IOException.class, () -> {
            new FileBasedProducts(new File(testResources(), "missing.csv").toPath()).get();
        });
    }

    private File testResources() throws Exception {
        return Path.of(getClass().getResource("/test").toURI()).toFile();
    }
}
