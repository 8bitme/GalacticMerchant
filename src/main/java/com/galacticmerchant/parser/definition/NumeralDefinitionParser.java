package com.galacticmerchant.parser.definition;

import com.galacticmerchant.type.Commodity;
import com.galacticmerchant.type.numeral.Numeral;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumeralDefinitionParser extends DefinitionParser {

    private static final Pattern NUMERAL_PATTERN = Pattern.compile("\\b(.*)\\b is (.)");

    @Override
    public DefinitionParser instance() {
        return new NumeralDefinitionParser();
    }

    @Override
    public boolean canParse(String inputToParse) {
        return countNumberOfWords(inputToParse) == 3 && NUMERAL_PATTERN.matcher(inputToParse).find();
    }

    private int countNumberOfWords(String s) {
        return s.split(" ").length;
    }

    @Override
    public void parse(String inputToParse, Map<String, Numeral> globalNumeralToBaseNumeralMap, Map<String, Commodity> commodityNameToCommodityMap) {
        Matcher matcher = NUMERAL_PATTERN.matcher(inputToParse);
        matcher.find();
        String globalNumeral = matcher.group(1);
        String baseNumeral = matcher.group(2);

        Optional<Numeral> numeralOptional = Numeral.fromRomanNumeral(baseNumeral);

        if (numeralOptional.isPresent()) {
            globalNumeralToBaseNumeralMap.put(globalNumeral, numeralOptional.get());
        }
    }
}
