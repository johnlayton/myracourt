package com.bunnings.catalog.service;

import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.StreamSupport.stream;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.IOException;
import java.io.InputStream;
import java.util.Spliterator;
import java.util.stream.Stream;

public abstract class CSVSource<T> {
    private final Class<T> target;

    public CSVSource(Class<T> target) {
        this.target = target;
    }

    public Stream<T> get(final InputStream inputStream) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(target).withHeader();
        ObjectReader reader = mapper.readerFor(target).with(schema);
        return stream(spliteratorUnknownSize(reader.readValues(inputStream), Spliterator.ORDERED), false);
    }
}
