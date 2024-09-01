package com.eduardoschelive.deliverbills.bills.dto;

import com.eduardoschelive.deliverbills.bills.entity.Bill;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record UpsertBillDTO(
        @NotBlank
        String name,
        @NotNull @PositiveOrZero
        BigDecimal originalValue,
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dueDate,
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate paymentDate
) {

    public Bill toEntity() {

        return new Bill(
                null,
                name,
                originalValue,
                null,
                null,
                dueDate,
                paymentDate
        );
    }

}
