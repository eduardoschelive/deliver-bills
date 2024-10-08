package com.eduardoschelive.deliverbills.filter;

import com.eduardoschelive.deliverbills.filter.exception.InvalidSortDirectionException;
import com.eduardoschelive.deliverbills.filter.exception.NoSuchSortableFieldException;
import com.eduardoschelive.deliverbills.filter.utils.FilterFieldDetails;
import com.eduardoschelive.deliverbills.filter.utils.FilterUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.Map;

import static com.eduardoschelive.deliverbills.filter.utils.FilterConstants.ACCEPTED_SORT_VALUES;

@RequiredArgsConstructor
public class FilterSorting {

    private final Map<String, String> sortingParams;
    private final Map<String, FilterFieldDetails> filterDetailsMap;

    /**
     * @return the sort object based on the request parameters
     */
    public Sort getSort() {
        var initialSort = Sort.unsorted();

        for (Map.Entry<String, String> entry : sortingParams.entrySet()) {
            var key = FilterUtils.extractFieldPath(entry.getKey());
            var value = entry.getValue();

            if (!isSortField(key)) {
                throw new NoSuchSortableFieldException(key);
            }

            if (!isValidSortOrder(value)) {
                throw new InvalidSortDirectionException(value);
            }

            initialSort = addSortByField(initialSort, key, value);
        }

        return initialSort;
    }

    private Sort addSortByField(Sort sortBuilder, String key, String value) {
        var field = filterDetailsMap.get(key).fieldPath();
        var direction = Sort.Direction.fromString(value);
        return sortBuilder.and(Sort.by(direction, field));
    }

    private boolean isSortField(String key) {
        return filterDetailsMap.containsKey(key);
    }

    private boolean isValidSortOrder(String order) {
        return ACCEPTED_SORT_VALUES.contains(order);
    }

}
