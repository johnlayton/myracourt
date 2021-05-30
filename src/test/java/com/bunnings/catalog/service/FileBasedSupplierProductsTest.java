package com.bunnings.catalog.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bunnings.catalog.model.Product;
import com.bunnings.catalog.model.SupplierProduct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class FileBasedSupplierProductsTest {

    @Test
    public void shouldReadSupplierProductsFile() throws Exception {
        final List<SupplierProduct> supplierProducts = new FileBasedSupplierProducts(new File(testResources(), "barcodes.csv").toPath())
            .get().collect(Collectors.toList());
        assertThat(supplierProducts.size()).isEqualTo(1);
        assertThat(supplierProducts.get(0).getSku()).isEqualTo("AAA-BBB-CCC");
        assertThat(supplierProducts.get(0).getBarcode()).isEqualTo("0123456789");
        assertThat(supplierProducts.get(0).getSupplier()).isEqualTo("00001");
    }

    @Test
    public void shouldThrowIOExceptionForMissingProductsFile() throws Exception {
        assertThrows(IOException.class, () -> {
            new FileBasedSupplierProducts(new File(testResources(), "missing.csv").toPath()).get();
        });
    }

    private File testResources() throws Exception {
        return Path.of(getClass().getResource("/test").toURI()).toFile();
    }
}
