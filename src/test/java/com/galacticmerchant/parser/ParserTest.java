package com.galacticmerchant.parser;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void parse_singleNumeralDef_globalNumeralPopulatedCorrectly() throws Exception {
        Parser parser = new Parser("glob is I");
        parser.parse();

        assertThat(parser.globalNumeralToBaseNumeralMap.containsKey("glob"), is(true));
    }

    @Test
    public void parse_singleNumeralDef_baseNumeralMappedCorrectly() throws Exception {
        Parser parser = new Parser("glob is I");
        parser.parse();

        assertThat(parser.globalNumeralToBaseNumeralMap.get("glob").getRomanNumeral(), is(equalTo('I')));
    }

    @Test
    public void parse_twoNumeralDefs_defKeysPopulatedCorrectly() throws Exception {
        Parser parser = new Parser("glob is I\nprok is V");
        parser.parse();

        assertThat(parser.globalNumeralToBaseNumeralMap.containsKey("glob"), is(true));
        assertThat(parser.globalNumeralToBaseNumeralMap.containsKey("prok"), is(true));
    }

    @Test
    public void parse_multipleNumeralDefs_defKeysPopulatedCorrectly() throws Exception {
        String conversionNotes = "glob is I\n" +
                "prok is V\n" +
                "pish is X\n" +
                "tegj is L";

        Parser parser = new Parser(conversionNotes);
        parser.parse();

        assertThat(parser.globalNumeralToBaseNumeralMap.containsKey("glob"), is(true));
        assertThat(parser.globalNumeralToBaseNumeralMap.containsKey("prok"), is(true));
        assertThat(parser.globalNumeralToBaseNumeralMap.containsKey("pish"), is(true));
        assertThat(parser.globalNumeralToBaseNumeralMap.containsKey("tegj"), is(true));
    }

    @Test
    public void parse_singleNumeralDefAndSingleCommodityDef_commodityDefinedCorrectly() throws Exception {
        String conversionNotes = "glob is I\n" +
                "glob glob Silver is 34 Credits";

        Parser parser = new Parser(conversionNotes);
        parser.parse();

        assertThat(parser.commodityNameToCmmodityMap.containsKey("Silver"), is(true));
    }

    @Test
    public void parse_singleNumeralDefAndSingleCommodityDef_currencyParsedCorrectly() throws Exception {
        String conversionNotes = "glob is I\n" +
                "glob glob Silver is 34 Credits";

        Parser parser = new Parser(conversionNotes);
        parser.parse();

        assertThat(parser.commodityNameToCmmodityMap.get("Silver").getCurrency().getName() , is(equalTo("Credits")));
    }

    @Test
    public void parse_singleNumeralDefAndSingleCommodityDef_unitsCalculatedCorrectly() throws Exception {
        String conversionNotes = "glob is I\n" +
                "glob glob Silver is 34 Credits";

        Parser parser = new Parser(conversionNotes);
        parser.parse();

        assertThat(parser.commodityNameToCmmodityMap.get("Silver").getValue() , is(equalTo(17)));
    }
}