package com.galacticmerchant.parser.definition;

import com.galacticmerchant.type.Commodity;
import com.galacticmerchant.type.numeral.Numeral;

import java.util.Map;

public abstract class DefinitionParser {

    public abstract DefinitionParser instance();

    public abstract boolean canParse(String inputToParse);

    public abstract void parse(String inputToParse, Map<String, Numeral> globalNumeralToBaseNumeralMap, Map<String, Commodity> commodityNameToCommodityMap);
}
