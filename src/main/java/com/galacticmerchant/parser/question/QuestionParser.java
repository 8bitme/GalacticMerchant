package com.galacticmerchant.parser.question;

import com.galacticmerchant.parser.util.NumeralUtil;
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

    protected double validateNumeralStringAndCalculateSum(String numeralStringToParse, Map<String, Numeral> globalNumeralToBaseNumeralMap) {
        List<Numeral> numeralList = Arrays.stream(numeralStringToParse.split(" ")).map(s -> globalNumeralToBaseNumeralMap.get(s)).collect(Collectors.toList());

        NumeralUtil.throwErrorIfNumeralOccurencesAreInvalid(globalNumeralToBaseNumeralMap, numeralList);
        return NumeralUtil.sumNumeralValues(numeralList);
    }
}
