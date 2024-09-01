package com.eduardoschelive.deliverbills.filter.utils;

import com.eduardoschelive.deliverbills.filter.FilterPagination;
import com.eduardoschelive.deliverbills.filter.FilterSorting;
import com.eduardoschelive.deliverbills.filter.FilterSpecification;
import com.eduardoschelive.deliverbills.filter.entity.FilterTestEntity;
import com.eduardoschelive.deliverbills.filter.repository.FilterTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class FilterTestUtils {

    private final FilterTestRepository testRepository;

    public void createTestEntity(String stringField, Boolean booleanField) {
        var entity = FilterTestEntity.builder()
                .stringField(stringField)
                .booleanField(booleanField)
                .build();

        testRepository.save(entity);
    }

    public void clearRepositories() {
        testRepository.deleteAll();
    }

    public Specification<FilterTestEntity> buildSpecification(Map<String, String> filters) {
        var filterDetails = FilterUtils.extractFilterableFields(FilterTestEntity.class);
        return new FilterSpecification<>(filters, filterDetails);
    }

    public PageRequest buildPageable(Map<String, String> filters) {
        var filterDetails = FilterUtils.extractFilterableFields(FilterTestEntity.class);
        var queryParameters = new QueryParameterDetails(filters);

        var sort = new FilterSorting(queryParameters.getSortParameters(), filterDetails);
        var pagination = new FilterPagination(queryParameters.getPaginationParameters());

        return pagination.getPageRequest().withSort(sort.getSort());
    }

}
