package com.eduardoschelive.deliverbills.bills.utils;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PercentUtilsTest {

    private static Stream<Arguments> provideValues() {
        return Stream.of(
                Arguments.of(1.0, 0.01),
                Arguments.of(100.0, 1.0),
                Arguments.of(0.0, 0.0),
                Arguments.of(50.0, 0.5),
                Arguments.of(25.0, 0.25)
        );
    }

    @ParameterizedTest
    @MethodSource("provideValues")
    void should_return_the_value_divided_by_100(Double value, Double expected) {
        var result = PercentUtils.convertToDecimal(value);
        assertEquals(expected, result);
    }

}