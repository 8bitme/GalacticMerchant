package com.galacticmerchant.parser.definition;

import com.galacticmerchant.type.Commodity;
import com.galacticmerchant.type.numeral.Numeral;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;

public class NumeralDefinitionParser extends DefinitionParser {

    @Override
    public DefinitionParser instance() {
        return new NumeralDefinitionParser();
    }

    @Override
    public boolean canParse(String inputToParse) {
        return countNumberOfWords(inputToParse) == 3 && doesNotEndsWithQuestionMark(inputToParse) && DEFINITION_PATTERN.matcher(inputToParse).find();
    }

    private int countNumberOfWords(String s) {
        return s.split(" ").length;
    }

    @Override
    public void parse(String inputToParse, Map<String, Numeral> globalNumeralToBaseNumeralMap, Map<String, Commodity> commodityNameToCommodityMap) {
        Matcher matcher = DEFINITION_PATTERN.matcher(inputToParse);
        matcher.find();
        String globalNumeral = matcher.group(1);
        String baseNumeral = matcher.group(2);

        Optional<Numeral> numeralOptional = Numeral.fromRomanNumeral(baseNumeral);

        if (numeralOptional.isPresent()) {
            globalNumeralToBaseNumeralMap.put(globalNumeral, numeralOptional.get());
        }
    }
}
