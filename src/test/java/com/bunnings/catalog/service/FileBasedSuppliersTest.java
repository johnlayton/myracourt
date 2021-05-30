package com.bunnings.catalog.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bunnings.catalog.model.Supplier;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class FileBasedSuppliersTest {

    @Test
    public void shouldReadSuppliersFile() throws Exception {
        final List<Supplier> suppliers = new FileBasedSuppliers(new File(testResources(), "suppliers.csv").toPath())
            .get().collect(Collectors.toList());
        assertThat(suppliers.size()).isEqualTo(1);
        assertThat(suppliers.get(0).getId()).isEqualTo("00001");
        assertThat(suppliers.get(0).getName()).isEqualTo("Supplier Name");
    }

    @Test
    public void shouldThrowIOExceptionForMissingProductsFile() throws Exception {
        assertThrows(IOException.class, () -> {
            new FileBasedSuppliers(new File(testResources(), "missing.csv").toPath()).get();
        });
    }

    private File testResources() throws Exception {
        return Path.of(getClass().getResource("/test").toURI()).toFile();
    }
}
