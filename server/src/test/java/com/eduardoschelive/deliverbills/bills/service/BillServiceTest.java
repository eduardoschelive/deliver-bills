package com.eduardoschelive.deliverbills.bills.service;

import com.eduardoschelive.deliverbills.bills.dto.UpsertBillDTO;
import com.eduardoschelive.deliverbills.bills.repository.BillRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BillServiceTest {

    @Autowired
    private BillService billService;

    @Autowired
    private BillRepository billRepository;

    @AfterEach
    void tearDown() {
        billRepository.deleteAll();
    }

    @Test
    void it_should_create_a_bill() {
        var bill = new UpsertBillDTO(
                "Test",
                BigDecimal.valueOf(300),
                LocalDate.of(2021, 10, 1),
                LocalDate.of(2021, 10, 10)
        );

        var result = billService.create(bill);

        assertNotNull(result);
    }

    @Test
    void it_should_list_bills() {
        var bill = new UpsertBillDTO(
                "Test",
                BigDecimal.valueOf(300),
                LocalDate.of(2021, 10, 1),
                LocalDate.of(2021, 10, 10)
        );

        billService.create(bill);

        var result = billService.list(Specification.where(null), Pageable.unpaged());

        assertFalse(result.getContent().isEmpty());
        assertEquals(1, result.getContent().size());
    }

    @Test
    void it_should_update_a_bill() {
        var bill = new UpsertBillDTO(
                "Test",
                BigDecimal.valueOf(300),
                LocalDate.of(2021, 10, 1),
                LocalDate.of(2021, 10, 10)
        );

        var createdBill = billService.create(bill);

        var updatedBill = new UpsertBillDTO(
                "Test",
                BigDecimal.valueOf(400),
                LocalDate.of(2021, 10, 1),
                LocalDate.of(2021, 10, 10)
        );

        billService.update(createdBill.uuid(), updatedBill);

        var result = billService.list(Specification.where(null), Pageable.unpaged());

        assertFalse(result.getContent().isEmpty());
        assertEquals(1, result.getContent().size());
        assertEquals(BigDecimal.valueOf(400).setScale(2, RoundingMode.HALF_EVEN), result.getContent().getFirst().originalValue());
    }

    @Test
    void it_should_delete_a_bill() {
        var bill = new UpsertBillDTO(
                "Test",
                BigDecimal.valueOf(300),
                LocalDate.of(2021, 10, 1),
                LocalDate.of(2021, 10, 10)
        );

        var createdBill = billService.create(bill);

        billService.delete(createdBill.uuid());

        var result = billService.list(Specification.where(null), Pageable.unpaged());

        assertTrue(result.getContent().isEmpty());
    }

}