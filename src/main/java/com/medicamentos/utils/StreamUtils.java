package com.medicamentos.utils;

import java.util.List;
import java.util.stream.Collectors;

public class StreamUtils {

    public static <T> List<T> distintosElementos(List<T> items) {
        return items.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public static <T, R> List<R> mapear(List<T> items, java.util.function.Function<T, R> mapper) {
        return items.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    public static <T> List<T> filtrar(List<T> items, java.util.function.Predicate<T> predicate) {
        return items.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}