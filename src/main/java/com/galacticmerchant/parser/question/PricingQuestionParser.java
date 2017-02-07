package com.galacticmerchant.parser.question;

import com.galacticmerchant.type.Commodity;
import com.galacticmerchant.type.numeral.Numeral;

import java.util.Map;

public class PricingQuestionParser extends QuestionParser {
    private static final String NUMERAL_QUESTION_PREFIX = "how much is";

    @Override
    public QuestionParser instance() {
        return new PricingQuestionParser();
    }

    @Override
    public boolean canParse(String inputToParse) {
        return inputToParse.startsWith(NUMERAL_QUESTION_PREFIX);
    }

    @Override
    public String parse(String inputToParse, Map<String, Numeral> globalNumeralToBaseNumeralMap, Map<String, Commodity> commodityNameToCommodityMap) {
        String numeralStringToParse = inputToParse.substring(NUMERAL_QUESTION_PREFIX.length() + 1, inputToParse.lastIndexOf("?")).trim();
        double sumOfNumerals = validateNumeralStringAndCalculateSum(numeralStringToParse, globalNumeralToBaseNumeralMap);
        return outputAnswer(numeralStringToParse, sumOfNumerals);
    }

    private String outputAnswer(String numeralStringToParse, double sumOfNumerals) {
        return numeralStringToParse + " is " + (int) sumOfNumerals;
    }


}
