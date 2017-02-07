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

        NumeralUtil.throwErrorIfNumeralOccurencesAreInvalid(globalNumeralToBaseNumeralMap, numeralsList);
        double numeralSum = NumeralUtil.sumNumeralValues(numeralsList);
        commodityNameToCommodityMap.put(commodityName, new Commodity(commodityName, new Currency(currencyName), (units / numeralSum)));

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
