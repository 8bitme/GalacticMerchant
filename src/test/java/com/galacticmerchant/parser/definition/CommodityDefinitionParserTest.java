package com.galacticmerchant.parser.definition;

import com.galacticmerchant.type.Commodity;
import com.galacticmerchant.type.numeral.Numeral;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;

public class CommodityDefinitionParserTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void parse_numeralRepeatedTooManyTimes_errorThrown() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("moo can only appear 3 times consecutively");

        CommodityDefinitionParser commodityDefinitionParser = new CommodityDefinitionParser();
        String inputToParse = "moo moo moo moo Silver is 34 Credits";

        HashMap<String, Numeral> globalNumeralToBaseNumeralMap = new HashMap<>();
        globalNumeralToBaseNumeralMap.put("moo", Numeral.ONE);
        HashMap<String, Commodity> commodityNameToCommodityMap = new HashMap<>();

        commodityDefinitionParser.parse(inputToParse, globalNumeralToBaseNumeralMap, commodityNameToCommodityMap);
    }

    @Test
    public void parse_numeralRepeatedMaxTimes_noErrorThrown() throws Exception {
        CommodityDefinitionParser commodityDefinitionParser = new CommodityDefinitionParser();
        String inputToParse = "glob glob glob Silver is 34 Credits";

        HashMap<String, Numeral> globalNumeralToBaseNumeralMap = new HashMap<>();
        globalNumeralToBaseNumeralMap.put("glob", Numeral.ONE);
        HashMap<String, Commodity> commodityNameToCommodityMap = new HashMap<>();

        commodityDefinitionParser.parse(inputToParse, globalNumeralToBaseNumeralMap, commodityNameToCommodityMap);
    }

    @Test
    public void parse_repeatableNumeralNotRepeated_noErrorThrown() throws Exception {
        CommodityDefinitionParser commodityDefinitionParser = new CommodityDefinitionParser();
        String inputToParse = "glob Silver is 34 Credits";

        HashMap<String, Numeral> globalNumeralToBaseNumeralMap = new HashMap<>();
        globalNumeralToBaseNumeralMap.put("glob", Numeral.ONE);
        HashMap<String, Commodity> commodityNameToCommodityMap = new HashMap<>();

        commodityDefinitionParser.parse(inputToParse, globalNumeralToBaseNumeralMap, commodityNameToCommodityMap);
    }

    @Test
    public void parse_noneRepeatableNumeralNotRepeated_noErrorThrown() throws Exception {
        CommodityDefinitionParser commodityDefinitionParser = new CommodityDefinitionParser();
        String inputToParse = "glob Silver is 34 Credits";

        HashMap<String, Numeral> globalNumeralToBaseNumeralMap = new HashMap<>();
        globalNumeralToBaseNumeralMap.put("glob", Numeral.FIVE);
        HashMap<String, Commodity> commodityNameToCommodityMap = new HashMap<>();

        commodityDefinitionParser.parse(inputToParse, globalNumeralToBaseNumeralMap, commodityNameToCommodityMap);
    }

    @Test
    public void parse_numeralMayNotBeRepeated_errorThrown() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("glob cannot appear consecutively");

        CommodityDefinitionParser commodityDefinitionParser = new CommodityDefinitionParser();
        String inputToParse = "glob glob Silver is 34 Credits";

        HashMap<String, Numeral> globalNumeralToBaseNumeralMap = new HashMap<>();
        globalNumeralToBaseNumeralMap.put("glob", Numeral.FIVE);
        HashMap<String, Commodity> commodityNameToCommodityMap = new HashMap<>();

        commodityDefinitionParser.parse(inputToParse, globalNumeralToBaseNumeralMap, commodityNameToCommodityMap);
    }

}