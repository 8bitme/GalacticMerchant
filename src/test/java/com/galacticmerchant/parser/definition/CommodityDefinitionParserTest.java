package com.galacticmerchant.parser.definition;

import com.galacticmerchant.type.Commodity;
import com.galacticmerchant.type.numeral.Numeral;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

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

    @Test
    public void parse_MMVI_2006() throws Exception {
        CommodityDefinitionParser commodityDefinitionParser = new CommodityDefinitionParser();
        String inputToParse = "thousand thousand five one Silver is 2006 Credits";

        HashMap<String, Numeral> globalNumeralToBaseNumeralMap = new HashMap<>();
        globalNumeralToBaseNumeralMap.put("one", Numeral.ONE);
        globalNumeralToBaseNumeralMap.put("five", Numeral.FIVE);
        globalNumeralToBaseNumeralMap.put("thousand", Numeral.ONE_THOUSAND);
        HashMap<String, Commodity> commodityNameToCommodityMap = new HashMap<>();

        commodityDefinitionParser.parse(inputToParse, globalNumeralToBaseNumeralMap, commodityNameToCommodityMap);

        assertThat(commodityNameToCommodityMap.get("Silver").getValue(), is(equalTo(1.0)));
    }

    @Test
    public void parse_MCMXLIV_1944() throws Exception {
        CommodityDefinitionParser commodityDefinitionParser = new CommodityDefinitionParser();
        String inputToParse = "thousand hundred  thousand ten fifty one five Silver is 1944 Credits";

        HashMap<String, Numeral> globalNumeralToBaseNumeralMap = new HashMap<>();
        globalNumeralToBaseNumeralMap.put("one", Numeral.ONE);
        globalNumeralToBaseNumeralMap.put("five", Numeral.FIVE);
        globalNumeralToBaseNumeralMap.put("ten", Numeral.TEN);
        globalNumeralToBaseNumeralMap.put("fifty", Numeral.FIFTY);
        globalNumeralToBaseNumeralMap.put("hundred", Numeral.ONE_HUNDRED);
        globalNumeralToBaseNumeralMap.put("fivehundred", Numeral.FIVE_HUNDRED);
        globalNumeralToBaseNumeralMap.put("thousand", Numeral.ONE_THOUSAND);
        HashMap<String, Commodity> commodityNameToCommodityMap = new HashMap<>();

        commodityDefinitionParser.parse(inputToParse, globalNumeralToBaseNumeralMap, commodityNameToCommodityMap);

        assertThat(commodityNameToCommodityMap.get("Silver").getValue(), is(equalTo(1.0)));
    }
}