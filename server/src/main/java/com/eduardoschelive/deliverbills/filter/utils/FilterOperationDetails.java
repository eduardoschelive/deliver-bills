package com.eduardoschelive.deliverbills.filter.utils;

import com.eduardoschelive.deliverbills.filter.enums.FilterOperation;

public record FilterOperationDetails(
        FilterOperation filterOperation,
        boolean negated,
        boolean isCaseInsensitive
) {
}
