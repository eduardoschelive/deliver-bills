package com.eduardoschelive.deliverbills.bills.entity;

import com.eduardoschelive.deliverbills.bills.utils.FineAndInterestCalculator;
import com.eduardoschelive.deliverbills.bills.utils.FineAndInterestRule;
import com.eduardoschelive.deliverbills.filter.annotation.Filterable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static java.lang.Math.toIntExact;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "bills")
@Entity
@Builder
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Filterable
    @Column(nullable = false, name = "name")
    private String name;

    @Filterable
    @Column(nullable = false, name = "original_value", precision = 19, scale = 4)
    private BigDecimal originalValue;

    @Filterable
    @Column(nullable = false, name = "corrected_value", precision = 19, scale = 4)
    private BigDecimal correctedValue;

    @Filterable
    @Column(nullable = false, name = "days_overdue")
    private Integer daysOverdue;

    @Filterable
    @Column(nullable = false, name = "due_date")
    private LocalDate dueDate;

    @Filterable
    @Column(nullable = false, name = "payment_date")
    private LocalDate paymentDate;

    public void calculateDaysOverdue() {
        var dayDiff = this.dueDate.until(this.paymentDate, ChronoUnit.DAYS);
        this.daysOverdue = Math.max(toIntExact(dayDiff), 0);
    }

    public void calculateCorrectedValue() {
        var calculator = new FineAndInterestCalculator(this.daysOverdue, this.originalValue, FineAndInterestRule.DEFAULT_RULES);
        this.correctedValue = calculator.calculateTotalAmount();
    }

    public void updateBillCalculations() {
        this.calculateDaysOverdue();
        this.calculateCorrectedValue();
    }

    @PrePersist
    public void prePersist() {
        this.updateBillCalculations();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateBillCalculations();
    }

}
