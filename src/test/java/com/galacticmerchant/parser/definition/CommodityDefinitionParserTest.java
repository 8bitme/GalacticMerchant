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

    @Test
    public void parse_numeralCannotBeSubtracted_errorThrown() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("five cannot appear before a larger number as it cannot be subtracted");

        CommodityDefinitionParser commodityDefinitionParser = new CommodityDefinitionParser();
        String inputToParse = "five oneHundred Silver is 34 Credits";

        HashMap<String, Numeral> globalNumeralToBaseNumeralMap = new HashMap<>();
        globalNumeralToBaseNumeralMap.put("five", Numeral.FIVE);
        globalNumeralToBaseNumeralMap.put("oneHundred", Numeral.ONE_HUNDRED);
        HashMap<String, Commodity> commodityNameToCommodityMap = new HashMap<>();

        commodityDefinitionParser.parse(inputToParse, globalNumeralToBaseNumeralMap, commodityNameToCommodityMap);
    }

    @Test
    public void parse_numeralCannotBeSubtractedFromSubsequentNumeral_errorThrown() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("one cannot be subtracted from the subsequent number");

        CommodityDefinitionParser commodityDefinitionParser = new CommodityDefinitionParser();
        String inputToParse = "one fifty Silver is 34 Credits";

        HashMap<String, Numeral> globalNumeralToBaseNumeralMap = new HashMap<>();
        globalNumeralToBaseNumeralMap.put("one", Numeral.ONE);
        globalNumeralToBaseNumeralMap.put("fifty", Numeral.FIFTY);
        HashMap<String, Commodity> commodityNameToCommodityMap = new HashMap<>();

        commodityDefinitionParser.parse(inputToParse, globalNumeralToBaseNumeralMap, commodityNameToCommodityMap);
    }

}