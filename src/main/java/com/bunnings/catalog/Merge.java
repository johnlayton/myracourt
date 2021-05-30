package com.bunnings.catalog;

import static net.logstash.logback.argument.StructuredArguments.v;

import com.bunnings.catalog.model.CatalogItem;
import com.bunnings.catalog.model.Product;
import com.bunnings.catalog.model.Supplier;
import com.bunnings.catalog.service.CompanySource;
import com.bunnings.catalog.service.FileBasedCompanySource;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Merge {

    private static final Logger LOGGER = LoggerFactory.getLogger(Merge.class);

    public static void main(String[] args) throws Exception {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(Item.class).withHeader().withoutQuoteChar();
        mapper.writer(schema).writeValue(outputStream(args),
            new Merge().perform(companies(args))
                .map(Item::new)
                .collect(Collectors.toList())
        );
    }

    private static CompanySource[] companies(String[] args) {
        if (args.length > 3) {
            return Arrays.stream(args).skip(2)
                .map(company -> new FileBasedCompanySource(args[1], company))
                .toArray(CompanySource[]::new);
        } else {
            return new CompanySource[] {
                new FileBasedCompanySource("src/main/resources/input", "A"),
                new FileBasedCompanySource("src/main/resources/input", "B")
            };
        }
    }

    private static OutputStream outputStream(String[] args) throws IOException {
        return args.length > 0 ? Files.newOutputStream(Path.of(args[0])) : System.out;
    }

    public Stream<CatalogItem> perform(CompanySource... companies) {
        return Stream.of(companies)
            .flatMap(company -> {
                try {
                    List<Supplier> suppliers = company.suppliers().collect(Collectors.toList());
                    List<Product> products = company.products().collect(Collectors.toList());
                    return company.supplierProducts()
                        .map(supplierProduct -> new CatalogItem(company.identifier(), supplierProduct,
                            suppliers.stream().filter(supplier -> supplierProduct.getSupplier().equals(supplier.getId()))
                                .findFirst().orElseThrow(),
                            products.stream().filter(product -> supplierProduct.getSku().equals(product.getSku()))
                                .findFirst().orElseThrow()
                        ));
                } catch (IOException exception) {
                    LOGGER.error("Exception creating product catalog for company {}", v("company", company), exception);
                    throw new RuntimeException(exception);
                }
            })
            .filter(distinctByBarcode())
            .filter(distinctBySku());
    }

    private Predicate<CatalogItem> distinctBySku() {
        return distinct(item -> item.getSupplierProduct().getSku());
    }

    private Predicate<CatalogItem> distinctByBarcode() {
        return distinct(item -> item.getSupplierProduct().getBarcode());
    }

    private <T> Predicate<T> distinct(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
