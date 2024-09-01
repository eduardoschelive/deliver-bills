package com.eduardoschelive.deliverbills.bills.entity;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BillTest {

    @Test
    void it_should_calculate_corrected_value() {
        var bill = Bill.builder()
                .originalValue(BigDecimal.valueOf(300.0))
                .dueDate(LocalDate.of(2021, 10, 1))
                .paymentDate(LocalDate.of(2021, 10, 10))
                .daysOverdue(1) // Valor apenas para testar
                .build();

        bill.calculateCorrectedValue();

        assertNotNull(bill.getCorrectedValue());
    }

    @Test
    void it_should_calculate_the_days_overdue() {
        var bill = Bill.builder()
                .dueDate(LocalDate.of(2021, 10, 1))
                .paymentDate(LocalDate.of(2021, 10, 10))
                .build();

        bill.calculateDaysOverdue();

        assertEquals(9, bill.getDaysOverdue());
    }

}