package com.galacticmerchant.parser.definition;

import com.galacticmerchant.parser.util.NumeralUtil;
import com.galacticmerchant.type.Commodity;
import com.galacticmerchant.type.Currency;
import com.galacticmerchant.type.numeral.Numeral;
import com.galacticmerchant.type.numeral.util.NumeralRule;

import java.util.*;
import java.util.stream.Collectors;

public class CommodityDefinitionParser extends DefinitionParser {

    @Override
    public DefinitionParser instance() {
        return new CommodityDefinitionParser();
    }

    @Override
    public boolean canParse(String inputToParse) {
        return doesNotEndsWithQuestionMark(inputToParse) && DEFINITION_PATTERN.matcher(inputToParse).find();
    }

    @Override
    public void parse(String inputToParse, Map<String, Numeral> globalNumeralToBaseNumeralMap, Map<String, Commodity> commodityNameToCommodityMap) {
        String commodityName = "";
        String currencyName = "";
        double units = -1;
        boolean previousWasNumeral = false;
        List<Integer> numeralValues = new ArrayList<>();
        String[] words = inputToParse.split(" ");
        List<Numeral> numeralsList = new ArrayList<>();

        for (String wordI : words) {
            boolean isNumeral = globalNumeralToBaseNumeralMap.containsKey(wordI);
            if (isNumeral) {
                previousWasNumeral = true;
                Numeral numeral = globalNumeralToBaseNumeralMap.get(wordI);
                numeralValues.add(numeral.getValue());

                numeralsList.add(numeral);
            } else if (isCommodity(previousWasNumeral)) {
                commodityName = wordI;
                previousWasNumeral = false;
            } else if (isUnits(commodityName, units, wordI)) {
                units = Integer.parseInt(wordI);
            } else if (isCurrencyName(units)) {
                currencyName = wordI;
            }
        }

        throwErrorIfNumeralOccurencesAreInvalid(globalNumeralToBaseNumeralMap, numeralsList);

        double numeralSum = NumeralUtil.sumNumeralValues(numeralValues);
        commodityNameToCommodityMap.put(commodityName, new Commodity(commodityName, new Currency(currencyName), (units / numeralSum)));

    }

    private void throwErrorIfNumeralOccurencesAreInvalid(Map<String, Numeral> globalNumeralToBaseNumeralMap, List<Numeral> numeralsList) {
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

    private void throwErrorIfPreviousNumeralIsNotSubtractableFromCurrent(Map<String, Numeral> globalNumeralToBaseNumeralMap, Numeral previousNumeral, Numeral currentNumeral) {
        if (previousNumeral != currentNumeral && NumeralRule.canBeSubtracted(previousNumeral) && NumeralRule.cannotSubtractNumeralFromAnother(previousNumeral, currentNumeral)) {
            List<String> galacticNumeralName = getGalacticNumeralName(globalNumeralToBaseNumeralMap, previousNumeral);
            throw new IllegalArgumentException(galacticNumeralName.get(0) + " cannot be subtracted from the subsequent number");
        }
    }

    private void throwErrorIfPreviousNumeralMayNotBeSubtracted(Map<String, Numeral> globalNumeralToBaseNumeralMap, Numeral previousNumeral, Numeral currentNumeral) {
        if (previousNumeral.getValue() < currentNumeral.getValue() && previousNumeral != currentNumeral && NumeralRule.cannotSubtractNumeralFromAnother(previousNumeral, currentNumeral)) {
            List<String> galacticNumeralName = getGalacticNumeralName(globalNumeralToBaseNumeralMap, previousNumeral);
            throw new IllegalArgumentException(galacticNumeralName.get(0) + " cannot appear before a larger number as it cannot be subtracted");
        }
    }

    private void throwErrorIfNoneRepeatableNumeralIsRepated(Map<String, Numeral> globalNumeralToBaseNumeralMap, int occurrences, Numeral currentNumeral) {
        if (currentNumeral.getMaxRepetitions() == 0 && occurrences > 1) {
            List<String> associatedKeyName = getGalacticNumeralName(globalNumeralToBaseNumeralMap, currentNumeral);
            throw new IllegalArgumentException(associatedKeyName.get(0) + " cannot appear consecutively");
        }
    }

    private void throwExceptionIfNumeralRepeatedTooManyTimes(Map<String, Numeral> globalNumeralToBaseNumeralMap, int occurrences, Numeral currentNumeral) {
        if (currentNumeral.getMaxRepetitions() != 0 && occurrences > currentNumeral.getMaxRepetitions()) {
            List<String> associatedKeyName = getGalacticNumeralName(globalNumeralToBaseNumeralMap, currentNumeral);
            throw new IllegalArgumentException(associatedKeyName.get(0) + " can only appear " + currentNumeral.getMaxRepetitions() + " times consecutively");
        }
    }

    private List<String> getGalacticNumeralName(Map<String, Numeral> globalNumeralToBaseNumeralMap, Numeral currentNumeral) {
        return globalNumeralToBaseNumeralMap.entrySet().stream().filter(stringNumeralEntry -> stringNumeralEntry.getValue() == currentNumeral).map(Map.Entry::getKey).collect(Collectors.toList());
    }


    private boolean isCommodity(boolean previousWasNumeral) {
        return previousWasNumeral;
    }

    private boolean isCurrencyName(double units) {
        return units > -1;
    }

    private boolean isUnits(String commodityName, double units, String wordI) {
        return !commodityName.isEmpty() && !"is".equals(wordI) && units == -1;
    }
}
