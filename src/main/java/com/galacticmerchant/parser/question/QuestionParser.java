package com.galacticmerchant.parser.question;

import com.galacticmerchant.type.Commodity;
import com.galacticmerchant.type.numeral.Numeral;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class QuestionParser {

    public abstract QuestionParser instance();

    public abstract boolean canParse(String inputToParse);

    public abstract String parse(String inputToParse, Map<String, Numeral> globalNumeralToBaseNumeralMap, Map<String, Commodity> commodityNameToCommodityMap);

    protected double calculateSumOfGlobalNumeralString(String numeralStringToParse, Map<String, Numeral> globalNumeralToBaseNumeralMap) {
        List<Integer> globalNumeralValues = Arrays.stream(numeralStringToParse.split(" ")).map(s -> globalNumeralToBaseNumeralMap.get(s).getValue()).collect(Collectors.toList());
        return sumNumeralValues(globalNumeralValues);
    }

    private double sumNumeralValues(List<Integer> numeralValues) {
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
