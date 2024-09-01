package com.eduardoschelive.deliverbills.bills.utils;

import java.util.List;

public record FineAndInterestRule(int daysOverdue, double fine, double rate) {

    public static final List<FineAndInterestRule> DEFAULT_RULES = List.of(
            new FineAndInterestRule(0, 0, 0),
            new FineAndInterestRule(3, 2, 0.1),
            new FineAndInterestRule(5, 3, 0.2),
            new FineAndInterestRule(Integer.MAX_VALUE, 5, 0.3)
    );

    public FineAndInterestRule(int daysOverdue, double fine, double rate) {
        this.daysOverdue = daysOverdue;
        this.fine = PercentUtils.convertToDecimal(fine);
        this.rate = PercentUtils.convertToDecimal(rate);
    }

}
