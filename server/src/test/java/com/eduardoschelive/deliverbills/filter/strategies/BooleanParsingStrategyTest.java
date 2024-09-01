package com.eduardoschelive.deliverbills.filter.strategies;

import com.eduardoschelive.deliverbills.filter.entity.FilterTestEntity;
import com.eduardoschelive.deliverbills.filter.exception.InvalidBooleanFilterValueException;
import com.eduardoschelive.deliverbills.filter.repository.FilterTestRepository;
import com.eduardoschelive.deliverbills.filter.utils.FilterTestUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BooleanParsingStrategyTest {

    @Autowired
    private FilterTestRepository testRepository;

    @Autowired
    private FilterTestUtils filterTestUtils;

    @BeforeAll
    static void setup(@Autowired FilterTestUtils filterTestUtils) {
        filterTestUtils.createTestEntity("stringField", true);
        filterTestUtils.createTestEntity("stringField", false);
        filterTestUtils.createTestEntity("stringField", null);
    }

    @AfterAll
    static void cleanup(@Autowired FilterTestUtils filterTestUtils) {
        filterTestUtils.clearRepositories();
    }

    @Test
    void it_should_filter_with_boolean_field() {
        var params = Map.of(
                "booleanField_eq", "true"
        );

        var spec = filterTestUtils.buildSpecification(params);
        var pageable = Pageable.unpaged();

        var results = testRepository.findAll(spec, pageable);
        assertEquals(1, results.getTotalElements());
    }

    @Test
    void it_should_filter_by_negated_boolean_field() {
        var params = Map.of(
                "booleanField_n-eq", "true"
        );

        var spec = filterTestUtils.buildSpecification(params);
        var pageable = Pageable.unpaged();

        var results = testRepository.findAll(spec, pageable);
        assertEquals(1, results.getTotalElements());
    }

    @Test
    void it_should_throw_exception_when_invalid_boolean_value() {
        var params = Map.of(
                "booleanField_eq", "invalid"
        );

        var spec = filterTestUtils.buildSpecification(params);
        var pageable = Pageable.unpaged();

        assertThrows(InvalidBooleanFilterValueException.class, () -> {
            testRepository.findAll(spec, pageable);
        });
    }

    @Test
    void it_should_filter_null_boolean_field() {
        var params = Map.of(
                "booleanField_null", ""
        );

        var spec = filterTestUtils.buildSpecification(params);
        var pageable = Pageable.unpaged();

        Page<FilterTestEntity> results = testRepository.findAll(spec, pageable);
        assertEquals(1, results.getTotalElements());
    }


}