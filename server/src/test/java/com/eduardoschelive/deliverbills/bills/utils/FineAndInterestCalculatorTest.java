package com.eduardoschelive.deliverbills.bills.utils;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FineAndInterestCalculatorTest {

    @Test
    void it_should_calculate_the_compound_interest() {
        var fineAndInterestCalculator = new FineAndInterestCalculator(10, BigDecimal.valueOf(100), List.of());

        var fineAndInterestRule = new FineAndInterestRule(10, 2, 10);

        var result = fineAndInterestCalculator.calculateInterestMultiplier(fineAndInterestRule);
        var resultRounded = result.setScale(2, RoundingMode.HALF_EVEN);
        var expected = BigDecimal.valueOf(2.59).setScale(2, RoundingMode.HALF_EVEN);

        assertEquals(expected, resultRounded);
    }

    // Valores conferidos de acordo com o site https://www.mobills.com.br/calculadoras/calculadora-juros-compostos/
    @Test
    void it_should_calculate_total_value() {
        var rules = List.of(
                new FineAndInterestRule(10, 2, 10)
        );

        var fineAndInterestCalculator = new FineAndInterestCalculator(10, BigDecimal.valueOf(100), rules);

        var result = fineAndInterestCalculator.calculateTotalAmount();

        var resultRounded = result.setScale(2, RoundingMode.HALF_EVEN);
        var expected = BigDecimal.valueOf(264.56).setScale(2, RoundingMode.HALF_EVEN);

        assertEquals(expected, resultRounded);

    }

    @Nested
    class FineTests {

        static Stream<Arguments> provideFineAndInterest() {
            return Stream.of(
                    Arguments.of(3, 2, 0.1, 2.0),
                    Arguments.of(5, 3, 0.2, 3.0),
                    Arguments.of(6, 3, 0.2, 3.0),
                    Arguments.of(10, 5, 0.3, 5.0),
                    Arguments.of(11, 5, 0.3, 5.0)
            );
        }

        @ParameterizedTest
        @MethodSource("provideFineAndInterest")
        void it_should_calculate_the_value_with_fine(int fineDaysOverdue, double fine, double rate, double expectedValue) {
            var fineAndInterestCalculator = new FineAndInterestCalculator(3, BigDecimal.valueOf(100), List.of());

            var fineAndInterestRule = new FineAndInterestRule(fineDaysOverdue, fine, rate);

            var result = fineAndInterestCalculator.calculateFineValue(fineAndInterestRule);

            var expected = BigDecimal.valueOf(expectedValue).setScale(2, RoundingMode.HALF_EVEN);
            assertEquals(expected, result);
        }

    }

}