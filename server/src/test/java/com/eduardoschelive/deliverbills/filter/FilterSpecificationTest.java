package com.eduardoschelive.deliverbills.filter;

import com.eduardoschelive.deliverbills.filter.entity.FilterTestEntity;
import com.eduardoschelive.deliverbills.filter.exception.BadFilterFormatException;
import com.eduardoschelive.deliverbills.filter.exception.NoSuchFilterableFieldException;
import com.eduardoschelive.deliverbills.filter.exception.UnsupportedFilterOperationException;
import com.eduardoschelive.deliverbills.filter.exception.UnsupportedFilterOperationOnFieldException;
import com.eduardoschelive.deliverbills.filter.repository.FilterTestRepository;
import com.eduardoschelive.deliverbills.filter.utils.FilterTestUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FilterSpecificationTest {

    @Autowired
    private FilterTestRepository testRepository;

    @Autowired
    private FilterTestUtils filterTestUtils;

    @BeforeAll
    static void setup(@Autowired FilterTestUtils filterTestUtils) {
        filterTestUtils.createTestEntity("stringField", true);
        filterTestUtils.createTestEntity("stringField", false);
    }

    @AfterAll
    static void cleanup(@Autowired FilterTestUtils filterTestUtils) {
        filterTestUtils.clearRepositories();
    }

    @Test
    void it_should_find_all() {
        List<FilterTestEntity> all = testRepository.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void it_should_filter_with_sort() {
        var pageParams = Map.of(
                "stringField_sort", "desc"
        );
        var filters = Map.of("booleanField_eq", "true");

        var spec = filterTestUtils.buildSpecification(filters);
        var pageable = filterTestUtils.buildPageable(pageParams);

        var results = testRepository.findAll(spec, pageable);

        assertEquals(1, results.getTotalElements());
    }

    @Test
    void it_should_throw_error_if_operation_not_supported_on_field() {
        var params = Map.of(
                "stringField_gt", "stringField"
        );

        var spec = filterTestUtils.buildSpecification(params);
        var pageable = Pageable.unpaged();

        assertThrows(UnsupportedFilterOperationOnFieldException.class, () ->
                testRepository.findAll(spec, pageable));
    }

    @Test
    void it_should_throw_error_when_field_or_value_is_empty() {
        var params = Map.of("", "");

        var spec = filterTestUtils.buildSpecification(params);
        var pageable = Pageable.unpaged();

        assertThrows(BadFilterFormatException.class, () ->
                testRepository.findAll(spec, pageable));
    }

    @Test
    void it_should_throw_error_when_operation_is_invalid() {
        var params = Map.of(
                "stringField_invalid", "stringField0"
        );

        var spec = filterTestUtils.buildSpecification(params);
        var pageable = Pageable.unpaged();

        assertThrows(UnsupportedFilterOperationException.class, () ->
                testRepository.findAll(spec, pageable));
    }

    @Test
    void it_should_throw_error_when_field_is_invalid() {
        var params = Map.of(
                "invalidField_eq", "x"
        );

        var spec = filterTestUtils.buildSpecification(params);
        var pageable = Pageable.unpaged();

        assertThrows(NoSuchFilterableFieldException.class, () ->
                testRepository.findAll(spec, pageable));
    }

    @Test
    void it_should_throw_error_when_operation_is_empty() {
        var params = Map.of(
                "stringField", "stringField0"
        );

        var spec = filterTestUtils.buildSpecification(params);
        var pageable = Pageable.unpaged();

        assertThrows(BadFilterFormatException.class, () ->
                testRepository.findAll(spec, pageable));
    }

    @Test
    void it_should_throw_error_when_field_is_empty() {
        var params = Map.of(
                "eq", "stringField0"
        );

        var spec = filterTestUtils.buildSpecification(params);
        var pageable = Pageable.unpaged();

        assertThrows(BadFilterFormatException.class, () ->
                testRepository.findAll(spec, pageable));
    }

}
