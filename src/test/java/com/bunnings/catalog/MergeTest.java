package com.bunnings.catalog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bunnings.catalog.model.Product;
import com.bunnings.catalog.model.Supplier;
import com.bunnings.catalog.model.SupplierProduct;
import com.bunnings.catalog.service.CompanySource;
import com.bunnings.catalog.service.FileBasedCompanySource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class MergeTest {

    @Test
    public void shouldHandleTestScenario() {
        final List<Item> collect = new Merge()
            .perform(
                new FileBasedCompanySource(getClass().getResource("/input").getPath(), "A"),
                new FileBasedCompanySource(getClass().getResource("/input").getPath(), "B")
            )
            .map(Item::new)
            .collect(Collectors.toList());

        assertThat(collect.size()).isEqualTo(7);
        assertThat(collect)
            .extracting(Item::getDescription)
            .haveExactly(1, createMatchingStringCondition("Walkers Special Old Whiskey"))
            .haveExactly(1, createMatchingStringCondition("Bread - Raisin"))
            .haveExactly(1, createMatchingStringCondition("Tea - Decaf 1 Cup"))
            .haveExactly(2, createMatchingStringCondition("Cheese - Grana Padano"))
            .haveExactly(2, createMatchingStringCondition("Carbonated Water - Lemon Lime"));
        assertThat(collect)
            .extracting(Item::getSku)
            .containsOnly(
                "647-vyk-317",
                "280-oad-768",
                "165-rcy-650",
                "167-eol-949",
                "650-epd-782",
                "999-eol-949",
                "999-epd-782"
            );
    }

    private Condition<String> createMatchingStringCondition(String s) {
        return new Condition<>(item -> item.equals(s), s);
    }

    @Test
    public void shouldHandleCliInputOutput() throws Exception {
        File file = File.createTempFile("output", "csv");
        Merge.main(new String[] {
            file.getAbsolutePath(), getClass().getResource("/input").getPath(), "A", "B"
        });

        assertThat(file).exists();
        assertThat(Files.readAllLines(file.toPath()))
            .containsExactlyInAnyOrder(Files.readAllLines(Path.of(getClass().getResource("/output/result_output.csv").toURI())).toArray(String[]::new));
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenMissingCompanyData() {
        assertThrows(RuntimeException.class, () -> {
            new Merge()
                .perform(
                    new FileBasedCompanySource(getClass().getResource("/input").getPath(), "A"),
                    new FileBasedCompanySource(getClass().getResource("/input").getPath(), "C")
                )
                .collect(Collectors.toList());
        });
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenCatalogEntryMissing() {
        assertThrows(NoSuchElementException.class, () -> {
            new Merge()
                .perform(
                    testCompanySource("A",
                        Collections.emptyList(),
                        Collections.singletonList(createSupplier("0001", "Supplier")),
                        Collections.singletonList(createSupplierProduct("0001", "AAA-CCC", "0123456789"))
                    )
                )
                .collect(Collectors.toList());
        });
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenCatalogEntryNotMatching() {
        assertThrows(NoSuchElementException.class, () -> {
            new Merge()
                .perform(
                    testCompanySource("A",
                        Collections.singletonList(createProduct("AAA-BBB", "Product Description")),
                        Collections.singletonList(createSupplier("0001", "Supplier")),
                        Collections.singletonList(createSupplierProduct("0001", "AAA-CCC", "0123456789"))
                    )
                )
                .collect(Collectors.toList());
        });
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenSupplierEntryMissing() {
        assertThrows(NoSuchElementException.class, () -> {
            new Merge()
                .perform(
                    testCompanySource("A",
                        Collections.singletonList(createProduct("AAA-CCC", "Product Description")),
                        Collections.emptyList(),
                        Collections.singletonList(createSupplierProduct("0001", "AAA-CCC", "0123456789"))
                    )
                )
                .collect(Collectors.toList());
        });
    }

    private Product createProduct(String sku, String description) {
        Product product = new Product();
        product.setSku(sku);
        product.setDescription(description);
        return product;
    }

    private Supplier createSupplier(String id, String name) {
        Supplier supplier = new Supplier();
        supplier.setId(id);
        supplier.setName(name);
        return supplier;
    }

    private SupplierProduct createSupplierProduct(String supplier, String sku, String barcode) {
        SupplierProduct supplierProduct = new SupplierProduct();
        supplierProduct.setSupplier(supplier);
        supplierProduct.setBarcode(barcode);
        supplierProduct.setSku(sku);
        return supplierProduct;
    }

    private CompanySource testCompanySource(String company, List<Product> products, List<Supplier> suppliers, List<SupplierProduct> supplierProducts) {
        return new CompanySource() {
            @Override
            public String identifier() {
                return company;
            }

            @Override
            public Stream<Supplier> suppliers() {
                return suppliers.stream();
            }

            @Override
            public Stream<Product> products() {
                return products.stream();
            }

            @Override
            public Stream<SupplierProduct> supplierProducts() {
                return supplierProducts.stream();
            }
        };
    }

}
