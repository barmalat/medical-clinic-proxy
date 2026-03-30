package com.barmalat.medicalclinic_proxy.model;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int totalPages,
        long totalElements,
        int size,
        int number) {
}