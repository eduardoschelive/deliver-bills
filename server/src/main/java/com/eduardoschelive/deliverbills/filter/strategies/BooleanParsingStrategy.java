package com.eduardoschelive.deliverbills.filter.strategies;

import com.eduardoschelive.deliverbills.filter.enums.FilterOperation;
import com.eduardoschelive.deliverbills.filter.exception.InvalidBooleanFilterValueException;
import com.eduardoschelive.deliverbills.filter.utils.FilterOperationDetails;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class BooleanParsingStrategy extends ParsingStrategy {

    @Override
    public Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, String fieldName, FilterOperationDetails filterOperationDetails, Object value) {
        var stringValue = value.toString().trim();

        if (filterOperationDetails.filterOperation().equals(FilterOperation.NULL)) {
            return super.buildPredicate(criteriaBuilder, root, fieldName, filterOperationDetails, null);
        }

        if (!isValidBoolean(stringValue)) {
            throw new InvalidBooleanFilterValueException(stringValue);
        }

        var booleanValue = Boolean.parseBoolean(stringValue);

        return super.buildPredicate(criteriaBuilder, root, fieldName, filterOperationDetails, booleanValue);
    }

    private boolean isValidBoolean(String value) {
        return "true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value);
    }

}
