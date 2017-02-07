package com.galacticmerchant.parser.util;

import com.galacticmerchant.type.numeral.Numeral;
import com.galacticmerchant.type.numeral.util.NumeralRule;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NumeralUtil {

    public static double sumNumeralValues(List<Numeral> numeralValues) {
        double numeralSum = 0;
        boolean previousWasLessThanCurrent = false;
        for (int i = 0; i < numeralValues.size(); i++) {
            Integer currentValue = numeralValues.get(i).getValue();

            if (i != numeralValues.size() - 1) {
                Integer nextValue = numeralValues.get(i + 1).getValue();

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


    public static void throwErrorIfNumeralOccurencesAreInvalid(Map<String, Numeral> globalNumeralToBaseNumeralMap, List<Numeral> numeralsList) {
        int occurrences = 1;
        for (int i = 1; i < numeralsList.size(); i++) {
            Numeral previousNumeral = numeralsList.get(i - 1);
            Numeral currentNumeral = numeralsList.get(i);

            if (currentNumeral == previousNumeral) {
                occurrences++;
            } else {
                occurrences = 1;
            }

            throwErrorIfPreviousNumeralIsNotSubtractableFromCurrent(globalNumeralToBaseNumeralMap, previousNumeral, currentNumeral);
            throwErrorIfPreviousNumeralMayNotBeSubtracted(globalNumeralToBaseNumeralMap, previousNumeral, currentNumeral);
            throwErrorIfNoneRepeatableNumeralIsRepated(globalNumeralToBaseNumeralMap, occurrences, currentNumeral);
            throwExceptionIfNumeralRepeatedTooManyTimes(globalNumeralToBaseNumeralMap, occurrences, currentNumeral);
        }
    }

    private static void throwErrorIfPreviousNumeralIsNotSubtractableFromCurrent(Map<String, Numeral> globalNumeralToBaseNumeralMap, Numeral previousNumeral, Numeral currentNumeral) {
        if (previousNumeral != currentNumeral && NumeralRule.canBeSubtracted(previousNumeral) && NumeralRule.cannotSubtractNumeralFromAnother(previousNumeral, currentNumeral)) {
            List<String> galacticNumeralName = getGalacticNumeralName(globalNumeralToBaseNumeralMap, previousNumeral);
            throw new IllegalArgumentException(galacticNumeralName.get(0) + " cannot be subtracted from the subsequent number");
        }
    }

    private static void throwErrorIfPreviousNumeralMayNotBeSubtracted(Map<String, Numeral> globalNumeralToBaseNumeralMap, Numeral previousNumeral, Numeral currentNumeral) {
        if (previousNumeral.getValue() < currentNumeral.getValue() && previousNumeral != currentNumeral && NumeralRule.cannotSubtractNumeralFromAnother(previousNumeral, currentNumeral)) {
            List<String> galacticNumeralName = getGalacticNumeralName(globalNumeralToBaseNumeralMap, previousNumeral);
            throw new IllegalArgumentException(galacticNumeralName.get(0) + " cannot appear before a larger number as it cannot be subtracted");
        }
    }

    private static void throwErrorIfNoneRepeatableNumeralIsRepated(Map<String, Numeral> globalNumeralToBaseNumeralMap, int occurrences, Numeral currentNumeral) {
        if (currentNumeral.getMaxRepetitions() == 0 && occurrences > 1) {
            List<String> associatedKeyName = getGalacticNumeralName(globalNumeralToBaseNumeralMap, currentNumeral);
            throw new IllegalArgumentException(associatedKeyName.get(0) + " cannot appear consecutively");
        }
    }

    private static void throwExceptionIfNumeralRepeatedTooManyTimes(Map<String, Numeral> globalNumeralToBaseNumeralMap, int occurrences, Numeral currentNumeral) {
        if (currentNumeral.getMaxRepetitions() != 0 && occurrences > currentNumeral.getMaxRepetitions()) {
            List<String> associatedKeyName = getGalacticNumeralName(globalNumeralToBaseNumeralMap, currentNumeral);
            throw new IllegalArgumentException(associatedKeyName.get(0) + " can only appear " + currentNumeral.getMaxRepetitions() + " times consecutively");
        }
    }

    private static List<String> getGalacticNumeralName(Map<String, Numeral> globalNumeralToBaseNumeralMap, Numeral currentNumeral) {
        return globalNumeralToBaseNumeralMap.entrySet().stream().filter(stringNumeralEntry -> stringNumeralEntry.getValue() == currentNumeral).map(Map.Entry::getKey).collect(Collectors.toList());
    }
}
