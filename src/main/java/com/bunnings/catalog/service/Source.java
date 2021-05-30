package com.bunnings.catalog.service;

import java.io.IOException;
import java.util.stream.Stream;

public interface Source<T> {
    Stream<T> get() throws IOException;
}
