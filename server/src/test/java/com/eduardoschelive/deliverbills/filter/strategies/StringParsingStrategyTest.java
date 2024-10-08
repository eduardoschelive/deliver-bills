package com.eduardoschelive.deliverbills.filter.strategies;

import com.eduardoschelive.deliverbills.filter.entity.FilterTestEntity;
import com.eduardoschelive.deliverbills.filter.repository.FilterTestRepository;
import com.eduardoschelive.deliverbills.filter.utils.FilterTestUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StringParsingStrategyTest {

    @Autowired
    private FilterTestRepository testRepository;

    @Autowired
    private FilterTestUtils filterTestUtils;

    @BeforeAll
    static void setup(@Autowired FilterTestRepository repository) {
        var entity = FilterTestEntity.builder()
                .stringField("stringField")
                .booleanField(true)
                .build();

        repository.save(entity);
    }

    @AfterAll
    static void cleanup(@Autowired FilterTestUtils filterTestUtils) {
        filterTestUtils.clearRepositories();
    }

    private static Stream<Arguments> provideFilterParameters() {
        return Stream.of(
                Arguments.of(Map.of("stringField_eq", "stringField"), 1),
                Arguments.of(Map.of("stringField_eq-i", "STRINGFIELD"), 1),
                Arguments.of(Map.of("stringField_sw", "stringFie"), 1),
                Arguments.of(Map.of("stringField_sw-i", "STRINGFIE"), 1),
                Arguments.of(Map.of("stringField_ew", "eld"), 1),
                Arguments.of(Map.of("stringField_ew-i", "ELD"), 1),
                Arguments.of(Map.of("stringField_lk", "stringField"), 1),
                Arguments.of(Map.of("stringField_in", "stringField,stringField1"), 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideFilterParameters")
    void it_should_filter_with_string_field(Map<String, String> params, long expectedSize) {
        var spec = filterTestUtils.buildSpecification(params);
        var pageable = Pageable.unpaged();

        var results = testRepository.findAll(spec, pageable);
        assertEquals(expectedSize, results.getTotalElements());
    }
}