package com.galacticmerchant.parser.util;

import java.util.List;

public class NumeralUtil {

    public static double sumNumeralValues(List<Integer> numeralValues) {
        double numeralSum = 0;
        boolean previousWasLessThanCurrent = false;
        for (int i = 0; i < numeralValues.size(); i++) {
            Integer currentValue = numeralValues.get(i);

            if (i != numeralValues.size() - 1) {
                Integer nextValue = numeralValues.get(i + 1);

                if (currentValue < nextValue) {
                    numeralSum += nextValue - currentValue;
                    previousWasLessThanCurrent = true;
                } else if (previousWasLessThanCurrent) {
                    previousWasLessThanCurrent = false;
                } else {
                    numeralSum += currentValue;
                }
            } else if (!previousWasLessThanCurrent) {
                numeralSum += currentValue;
            }
        }
        return numeralSum;
    }
}
