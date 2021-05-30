package com.bunnings.catalog.service;

import com.bunnings.catalog.model.Product;
import com.bunnings.catalog.model.Supplier;
import com.bunnings.catalog.model.SupplierProduct;
import java.io.IOException;
import java.util.stream.Stream;

public interface CompanySource {
    String identifier();

    Stream<Supplier> suppliers() throws IOException;

    Stream<Product> products() throws IOException;

    Stream<SupplierProduct> supplierProducts() throws IOException;
}
