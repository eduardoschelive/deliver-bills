package com.eduardoschelive.deliverbills.bills.dto;


import com.eduardoschelive.deliverbills.bills.entity.Bill;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

public record BillDTO(
        UUID uuid,
        String name,
        BigDecimal originalValue,
        BigDecimal correctedValue,
        Integer daysOverdue,
        LocalDate dueDate,
        LocalDate paymentDate
) {

    public static BillDTO fromEntity(Bill bill) {
        return new BillDTO(
                bill.getUuid(),
                bill.getName(),
                bill.getOriginalValue().setScale(2, RoundingMode.HALF_EVEN),
                bill.getCorrectedValue().setScale(2, RoundingMode.HALF_EVEN),
                bill.getDaysOverdue(),
                bill.getDueDate(),
                bill.getPaymentDate()
        );
    }

}
