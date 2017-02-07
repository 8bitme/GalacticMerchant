package com.galacticmerchant.parser.question;

import com.galacticmerchant.type.Commodity;
import com.galacticmerchant.type.numeral.Numeral;

import java.util.Map;

public class CommodityQuestionParser extends QuestionParser {

    @Override
    public QuestionParser instance() {
        return new CommodityQuestionParser();
    }

    @Override
    public boolean canParse(String inputToParse) {
        return inputToParse.startsWith(getCommodityQuestionForCurrency("Credits"));
    }

    private String getCommodityQuestionForCurrency(String currency) {
        return String.format("how many %s is ", currency);
    }

    @Override
    public String parse(String inputToParse, Map<String, Numeral> globalNumeralToBaseNumeralMap, Map<String, Commodity> commodityNameToCommodityMap) {
        String numeralStringToParseWithCommodity = inputToParse.substring(getCommodityQuestionForCurrency("Credits").length(), inputToParse.lastIndexOf("?")).trim();
        String commodity = numeralStringToParseWithCommodity.substring(numeralStringToParseWithCommodity.lastIndexOf(" ") + 1);
        String numeralString = numeralStringToParseWithCommodity.substring(0, numeralStringToParseWithCommodity.lastIndexOf(" "));

        double sumOfNumerals = validateNumeralStringAndCalculateSum(numeralString, globalNumeralToBaseNumeralMap);

        return outputAnswer(commodityNameToCommodityMap, numeralStringToParseWithCommodity, commodity, sumOfNumerals);
    }

    private String outputAnswer(Map<String, Commodity> commodityNameToCommodityMap, String numeralStringToParseWithCommodity, String commodity, double sumOfNumerals) {
        return numeralStringToParseWithCommodity + " is " + (int) (sumOfNumerals * commodityNameToCommodityMap.get(commodity).getValue()) + " Credits";
    }


}
