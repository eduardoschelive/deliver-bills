package com.eduardoschelive.deliverbills.filter.utils;

public record FilterFieldDetails(
        Class<?> fieldType,
        String fieldPath
) {
}
