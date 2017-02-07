package com.galacticmerchant.parser.definition;

import com.galacticmerchant.type.Commodity;
import com.galacticmerchant.type.numeral.Numeral;

import java.util.Map;
import java.util.regex.Pattern;

public abstract class DefinitionParser {
    static final Pattern DEFINITION_PATTERN = Pattern.compile("\\b(.*)\\b is (.)");

    public abstract DefinitionParser instance();

    public abstract boolean canParse(String inputToParse);

    public abstract void parse(String inputToParse, Map<String, Numeral> globalNumeralToBaseNumeralMap, Map<String, Commodity> commodityNameToCommodityMap);

    boolean doesNotEndsWithQuestionMark(String noteI) {
        return noteI.lastIndexOf('?') == -1;
    }
}
