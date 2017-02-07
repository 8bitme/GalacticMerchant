package com.galacticmerchant.parser.definition;

import com.galacticmerchant.parser.util.NumeralUtil;
import com.galacticmerchant.type.Commodity;
import com.galacticmerchant.type.Currency;
import com.galacticmerchant.type.numeral.Numeral;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommodityDefinitionParser extends DefinitionParser {

    @Override
    public DefinitionParser instance() {
        return new CommodityDefinitionParser();
    }

    @Override
    public boolean canParse(String inputToParse) {
        return !endsWith('?', inputToParse);
    }

    private boolean endsWith(char charToCheckFor, String noteI) {
        return noteI.lastIndexOf(charToCheckFor) != -1;
    }

    @Override
    public void parse(String inputToParse, Map<String, Numeral> globalNumeralToBaseNumeralMap, Map<String, Commodity> commodityNameToCommodityMap) {
        String commodityName = "";
        String currencyName = "";
        double units = -1;
        boolean previousWasNumeral = false;
        List<Integer> numeralValues = new ArrayList<>();
        String[] words = inputToParse.split(" ");

        for (String wordI : words) {
            boolean isNumeral = globalNumeralToBaseNumeralMap.containsKey(wordI);
            if (isNumeral) {
                previousWasNumeral = true;
                numeralValues.add(globalNumeralToBaseNumeralMap.get(wordI).getValue());
            } else if (isCommodity(previousWasNumeral)) {
                commodityName = wordI;
                previousWasNumeral = false;
            } else if (isUnits(commodityName, units, wordI)) {
                units = Integer.parseInt(wordI);
            } else if (isCurrencyName(units)) {
                currencyName = wordI;
            }

        }

        double numeralSum = NumeralUtil.sumNumeralValues(numeralValues);
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
