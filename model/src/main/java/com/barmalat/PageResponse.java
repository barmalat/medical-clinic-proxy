package com.barmalat;

import java.util.List;
import java.util.function.Function;

public record PageResponse<T>(
        List<T> content,
        int totalPages,
        long totalElements,
        int size,
        int number) {
    public <R> PageResponse<R> map(Function<T, R> mapper) {
        List<R> mappedContent = content.stream()
                .map(mapper)
                .toList();
        return new PageResponse<>(mappedContent, totalPages, totalElements, size, number);
    }
}