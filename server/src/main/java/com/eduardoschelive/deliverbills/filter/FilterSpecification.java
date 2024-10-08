package com.eduardoschelive.deliverbills.filter;

import com.eduardoschelive.deliverbills.common.utils.StringUtils;
import com.eduardoschelive.deliverbills.filter.enums.FilterOperation;
import com.eduardoschelive.deliverbills.filter.exception.BadFilterFormatException;
import com.eduardoschelive.deliverbills.filter.exception.NoSuchFilterableFieldException;
import com.eduardoschelive.deliverbills.filter.exception.UnsupportedFilterOperationException;
import com.eduardoschelive.deliverbills.filter.strategies.ParsingStrategySelector;
import com.eduardoschelive.deliverbills.filter.utils.FilterFieldDetails;
import com.eduardoschelive.deliverbills.filter.utils.FilterOperationDetails;
import com.eduardoschelive.deliverbills.filter.utils.FilterUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.Map;

import static com.eduardoschelive.deliverbills.filter.utils.FilterConstants.CASE_INSENSITIVE_SUFFIX;
import static com.eduardoschelive.deliverbills.filter.utils.FilterConstants.NEGATION_PREFIX;

/**
 * @param <E> the entity type
 *            <p>
 *            A class that extracts the filter parameters from the request parameters
 *            and creates a {@link Specification} object.
 *            The filter parameters are used to filter the results of a query.
 *            </p>
 */
@RequiredArgsConstructor
public class FilterSpecification<E> implements Specification<E> {

    private final Map<String, String> queryParameters;
    private final Map<String, FilterFieldDetails> fieldDetailsMap;

    @Override
    public Predicate toPredicate(@NonNull Root<E> root, @NonNull CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var predicates = queryParameters.entrySet().stream()
                .map(entry -> createPredicate(entry, root, criteriaBuilder))
                .toList();
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate createPredicate(Map.Entry<String, String> entry, Root<E> root, CriteriaBuilder criteriaBuilder) {
        var fieldPath = FilterUtils.extractFieldPath(entry.getKey());
        var operation = FilterUtils.extractOperation(entry.getKey());

        validateFilterFieldAndOperation(fieldPath, operation);

        var filterableFieldDetails = fieldDetailsMap.get(fieldPath);
        var parser = ParsingStrategySelector.getStrategy(filterableFieldDetails.fieldType());
        var filterOperationDetails = createFilterOperationDetails(operation);

        return parser.buildPredicate(criteriaBuilder, root, filterableFieldDetails.fieldPath(), filterOperationDetails, entry.getValue());
    }

    private FilterOperationDetails createFilterOperationDetails(String suffix) {
        var lowerCaseSuffix = suffix.toLowerCase();
        var negated = isNegated(lowerCaseSuffix);
        var caseInsensitive = isCaseInsensitive(lowerCaseSuffix);

        if (negated) {
            lowerCaseSuffix = StringUtils.stripPrefix(lowerCaseSuffix, NEGATION_PREFIX);
        }
        if (caseInsensitive) {
            lowerCaseSuffix = StringUtils.stripSuffix(lowerCaseSuffix, CASE_INSENSITIVE_SUFFIX);
        }

        var operation = FilterOperation.fromSuffix(lowerCaseSuffix);
        return new FilterOperationDetails(operation, negated, caseInsensitive);
    }

    private void validateFilterFieldAndOperation(String fieldPath, String operation) {
        if (fieldPath == null || operation == null) {
            throw new BadFilterFormatException();
        }
        if (!isSupported(operation)) {
            throw new UnsupportedFilterOperationException(operation);
        }

        if (!fieldDetailsMap.containsKey(fieldPath)) {
            throw new NoSuchFilterableFieldException(fieldPath);
        }

    }

    private boolean isSupported(String suffix) {
        return FilterOperation.isSupported(normalizeSuffix(suffix));
    }

    private String normalizeSuffix(String suffix) {
        var result = suffix.toLowerCase();
        result = StringUtils.stripPrefix(result, NEGATION_PREFIX);
        result = StringUtils.stripSuffix(result, CASE_INSENSITIVE_SUFFIX);
        return result;
    }

    private boolean isNegated(String suffix) {
        return suffix.toLowerCase().startsWith(NEGATION_PREFIX);
    }

    private boolean isCaseInsensitive(String suffix) {
        return suffix.toLowerCase().endsWith(CASE_INSENSITIVE_SUFFIX);
    }

}
