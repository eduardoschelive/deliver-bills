package com.eduardoschelive.deliverbills.bills.utils;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class FineAndInterestCalculator {

    private final Integer daysOverdue;
    private final BigDecimal value;
    private final List<FineAndInterestRule> fineAndInterestRuleRules;

    private FineAndInterestRule getFineAndInterest() {
        return fineAndInterestRuleRules.stream()
                .filter(fineAndInterestRule -> daysOverdue <= fineAndInterestRule.daysOverdue())
                .min(Comparator.comparingInt(FineAndInterestRule::daysOverdue))
                .orElse(fineAndInterestRuleRules.getLast());
    }

    /*
     *  Calcula a quantidade total que deve ser paga com base nas regras de juros e multa
     *  o cálculo é feito com base na seguinte fórmula
     *  M = (C + [C * J / 100]) x (1 + i) ^ t
     * Onde:
     * M = Montante
     * C = Capital
     * J = Multa
     * i = Taxa de juros
     * t = Quantidade de vezes que os juros são aplicados
     * */
    public BigDecimal calculateTotalAmount() {
        var fineAndInterest = getFineAndInterest();
        return calculateTotalValue(fineAndInterest);
    }

    public BigDecimal calculateFineValue(FineAndInterestRule fineAndInterestRule) {
        var fine = BigDecimal.valueOf(fineAndInterestRule.fine());
        return value.multiply(fine);
    }

    public BigDecimal calculateInterestMultiplier(FineAndInterestRule fineAndInterestRule) {
        var rate = BigDecimal.valueOf(fineAndInterestRule.rate());
        return BigDecimal.ONE.add(rate).pow(daysOverdue);
    }

    public BigDecimal calculateTotalValue(FineAndInterestRule fineAndInterestRule) {
        var fineValue = calculateFineValue(fineAndInterestRule);
        var valueWithFine = value.add(fineValue);
        var compoundInterestValue = calculateInterestMultiplier(fineAndInterestRule);
        return valueWithFine.multiply(compoundInterestValue);
    }


}