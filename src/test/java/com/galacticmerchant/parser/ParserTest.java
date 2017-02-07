package com.galacticmerchant.parser;

import com.galacticmerchant.type.Commodity;
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

        assertThat(parser.commodityNameToCommodityMap.containsKey("Silver"), is(true));
    }

    @Test
    public void parse_singleNumeralDefAndSingleCommodityDef_currencyParsedCorrectly() throws Exception {
        String conversionNotes = "glob is I\n" +
                "glob glob Silver is 34 Credits";

        Parser parser = new Parser(conversionNotes);
        parser.parse();

        assertThat(parser.commodityNameToCommodityMap.get("Silver").getCurrency().getName(), is(equalTo("Credits")));
    }

    @Test
    public void parse_singleNumeralDefAndSingleCommodityDef_unitsCalculatedCorrectly() throws Exception {
        String conversionNotes = "glob is I\n" +
                "glob glob Silver is 34 Credits";

        Parser parser = new Parser(conversionNotes);
        parser.parse();

        assertThat(parser.commodityNameToCommodityMap.get("Silver").getValue(), is(equalTo(17.0)));
    }

    @Test
    public void parse_multiNumeralDefAndTwoCommodityDef_correctNumberOfCommoditiesParsed() throws Exception {
        String conversionNotes = "glob is I\n" +
                "prok is V\n" +
                "pish is X\n" +
                "tegj is L\n" +
                "glob glob Silver is 34 Credits\n" +
                "glob prok Gold is 57800 Credits";

        Parser parser = new Parser(conversionNotes);
        parser.parse();

        assertThat(parser.commodityNameToCommodityMap.size(), is(equalTo(2)));
    }

    @Test
    public void parse_multiNumeralDefAndTwoCommodityDef_commoditiesParedCorrectly() throws Exception {
        String conversionNotes = "glob is I\n" +
                "prok is V\n" +
                "pish is X\n" +
                "tegj is L\n" +
                "glob glob Silver is 34 Credits\n" +
                "glob prok Gold is 57800 Credits";

        Parser parser = new Parser(conversionNotes);
        parser.parse();

        Commodity silver = parser.commodityNameToCommodityMap.get("Silver");
        assertThat(silver.getName(), is(equalTo("Silver")));
        assertThat(silver.getValue(), is(equalTo(17.0)));
        assertThat(silver.getCurrency().getName(), is(equalTo("Credits")));

        Commodity gold = parser.commodityNameToCommodityMap.get("Gold");
        assertThat(gold.getName(), is(equalTo("Gold")));
        assertThat(gold.getValue(), is(equalTo(14450.0)));
        assertThat(gold.getCurrency().getName(), is(equalTo("Credits")));
    }

    @Test
    public void parse_multiNumeralDefAndMultiCommodityDef_commoditiesParedCorrectly() throws Exception {
        String conversionNotes = "glob is I\n" +
                "prok is V\n" +
                "pish is X\n" +
                "tegj is L\n" +
                "glob glob Silver is 34 Credits\n" +
                "glob prok Gold is 57800 Credits\n" +
                "pish pish Iron is 3910 Credits";

        Parser parser = new Parser(conversionNotes);
        parser.parse();

        Commodity silver = parser.commodityNameToCommodityMap.get("Silver");
        assertThat(silver.getName(), is(equalTo("Silver")));
        assertThat(silver.getValue(), is(equalTo(17.0)));
        assertThat(silver.getCurrency().getName(), is(equalTo("Credits")));

        Commodity gold = parser.commodityNameToCommodityMap.get("Gold");
        assertThat(gold.getName(), is(equalTo("Gold")));
        assertThat(gold.getValue(), is(equalTo(14450.0)));
        assertThat(gold.getCurrency().getName(), is(equalTo("Credits")));

        Commodity iron = parser.commodityNameToCommodityMap.get("Iron");
        assertThat(iron.getName(), is(equalTo("Iron")));
        assertThat(iron.getValue(), is(equalTo(195.5)));
        assertThat(iron.getCurrency().getName(), is(equalTo("Credits")));
    }

    @Test
    public void parse_numeralQuestion_correctNumberOfAnswerReturned() throws Exception {
        String conversionNotes = "glob is I\n" +
                "prok is V\n" +
                "pish is X\n" +
                "tegj is L\n" +
                "glob glob Silver is 34 Credits\n" +
                "glob prok Gold is 57800 Credits\n" +
                "pish pish Iron is 3910 Credits\n" +
                "how much is pish tegj glob glob ?";

        Parser parser = new Parser(conversionNotes);
        parser.parse();

        assertThat(parser.answers.size(), is(1));
    }

    @Test
    public void parse_numeralQuestion_correctAnswerReturned() throws Exception {
        String conversionNotes = "glob is I\n" +
                "prok is V\n" +
                "pish is X\n" +
                "tegj is L\n" +
                "glob glob Silver is 34 Credits\n" +
                "glob prok Gold is 57800 Credits\n" +
                "pish pish Iron is 3910 Credits\n" +
                "how much is pish tegj glob glob ?";

        Parser parser = new Parser(conversionNotes);
        parser.parse();

        assertThat(parser.answers.get(0), is("pish tegj glob glob is 42"));
    }

    @Test
    public void parse_twoNumeralQuestions_correctAnswerReturned() throws Exception {
        String conversionNotes = "glob is I\n" +
                "prok is V\n" +
                "pish is X\n" +
                "tegj is L\n" +
                "glob glob Silver is 34 Credits\n" +
                "glob prok Gold is 57800 Credits\n" +
                "pish pish Iron is 3910 Credits\n" +
                "how much is pish tegj glob glob ?\n"+
                "how much is tegj glob glob glob?";

        Parser parser = new Parser(conversionNotes);
        parser.parse();

        assertThat(parser.answers.get(1), is("tegj glob glob glob is 53"));
    }

    @Test
    public void parse_multipleNumeralQuestions_correctAnswerReturned() throws Exception {
        String conversionNotes = "glob is I\n" +
                "prok is V\n" +
                "pish is X\n" +
                "tegj is L\n" +
                "glob glob Silver is 34 Credits\n" +
                "glob prok Gold is 57800 Credits\n" +
                "pish pish Iron is 3910 Credits\n" +
                "how much is pish tegj glob glob ?\n"+
                "how much is tegj glob glob glob?\n"+
                "how much is prok?";

        Parser parser = new Parser(conversionNotes);
        parser.parse();

        assertThat(parser.answers.get(2), is("prok is 5"));
    }

    @Test
    public void parse_singleCommodityQuestion_correctAnswerReturned() throws Exception {
        String conversionNotes = "glob is I\n" +
                "prok is V\n" +
                "pish is X\n" +
                "tegj is L\n" +
                "glob glob Silver is 34 Credits\n" +
                "glob prok Gold is 57800 Credits\n" +
                "pish pish Iron is 3910 Credits\n" +
                "how many Credits is glob prok Silver ?";

        Parser parser = new Parser(conversionNotes);
        parser.parse();

        assertThat(parser.answers.get(0), is("glob prok Silver is 68 Credits"));
    }

    @Test
    public void parse_twoCommodityQuestion_correctAnswerReturned() throws Exception {
        String conversionNotes = "glob is I\n" +
                "prok is V\n" +
                "pish is X\n" +
                "tegj is L\n" +
                "glob glob Silver is 34 Credits\n" +
                "glob prok Gold is 57800 Credits\n" +
                "pish pish Iron is 3910 Credits\n" +
                "how many Credits is glob prok Silver ?\n" +
                "how many Credits is glob prok Gold ?";

        Parser parser = new Parser(conversionNotes);
        parser.parse();

        assertThat(parser.answers.get(1), is("glob prok Gold is 57800 Credits"));
    }

    @Test
    public void parse_multipleCommodityQuestion_correctAnswerReturned() throws Exception {
        String conversionNotes = "glob is I\n" +
                "prok is V\n" +
                "pish is X\n" +
                "tegj is L\n" +
                "glob glob Silver is 34 Credits\n" +
                "glob prok Gold is 57800 Credits\n" +
                "pish pish Iron is 3910 Credits\n" +
                "how many Credits is glob prok Silver ?\n" +
                "how many Credits is glob prok Gold ?\n" +
                "how many Credits is glob prok Iron ?";

        Parser parser = new Parser(conversionNotes);
        parser.parse();

        assertThat(parser.answers.get(2), is("glob prok Iron is 782 Credits"));
    }

    @Test
    public void parse_invalidQuestion_errorAnswerReturned() throws Exception {
        String conversionNotes = "glob is I\n" +
                "prok is V\n" +
                "pish is X\n" +
                "tegj is L\n" +
                "glob glob Silver is 34 Credits\n" +
                "glob prok Gold is 57800 Credits\n" +
                "pish pish Iron is 3910 Credits\n" +
                "how much wood could a woodchuck chuck if a woodchuck could chuck wood?";

        Parser parser = new Parser(conversionNotes);
        parser.parse();

        assertThat(parser.answers.get(0), is("I have no idea what you are talking about"));
    }

    @Test
    public void parse_fullNotesLog_returnsCorrectAnswers() throws Exception {
        String conversionNotes = "glob is I\n" +
                "prok is V\n" +
                "pish is X\n" +
                "tegj is L\n" +
                "glob glob Silver is 34 Credits\n" +
                "glob prok Gold is 57800 Credits\n" +
                "pish pish Iron is 3910 Credits\n" +
                "how much is pish tegj glob glob ?\n" +
                "how many Credits is glob prok Silver ?\n" +
                "how many Credits is glob prok Gold ?\n" +
                "how many Credits is glob prok Iron ?\n" +
                "how much wood could a woodchuck chuck if a woodchuck could chuck wood?";

        String expectedAnswer = "pish tegj glob glob is 42\n" +
                "glob prok Silver is 68 Credits\n" +
                "glob prok Gold is 57800 Credits\n" +
                "glob prok Iron is 782 Credits\n" +
                "I have no idea what you are talking about";

        Parser parser = new Parser(conversionNotes);
        String answer = parser.parse();

        assertThat(answer, is(equalTo(expectedAnswer)));
    }
}