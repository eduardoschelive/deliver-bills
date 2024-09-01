package com.eduardoschelive.deliverbills.bills.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PercentUtils {

    public static double convertToDecimal(double percentage) {
        return percentage / 100;
    }

}
