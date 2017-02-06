package com.galacticmerchant.parser;

import com.galacticmerchant.type.Commodity;
import com.galacticmerchant.type.Currency;
import com.galacticmerchant.type.numeral.Numeral;
import com.galacticmerchant.type.numeral.util.Factory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    final Map<String, Numeral> globalNumeralToBaseNumeralMap = new HashMap<>();
    final Map<String, Commodity> commodityNameToCmmodityMap = new HashMap<>();
    private String conversionNotes;

    public Parser(String conversionNotes) {
        this.conversionNotes = conversionNotes;
    }

    public void parse() {
        Arrays.stream(conversionNotes.split("\\n")).forEach(noteI -> {
            int numberOfWords = countNumberOfWords(noteI);
            boolean endsInQuestionMark = endsWith('?', noteI);
            if (numberOfWords == 3) {
                parseNumeralDefinition(noteI);
            } else if (!endsInQuestionMark) {
                parseCommodityDefinition(noteI);

            }
        });
    }

    private void parseCommodityDefinition(String noteI) {
        int numeralSum = 0;

        String commodityName = "";
        String currencyName = "";
        int units = -1;
        boolean previousWasNumeral = false;
        String[] words = noteI.split(" ");

        for (String wordI : words) {
            boolean isNumeral = globalNumeralToBaseNumeralMap.containsKey(wordI);
            if (isNumeral) {
                previousWasNumeral = true;
                numeralSum+= globalNumeralToBaseNumeralMap.get(wordI).getValue();
            } else if (isCommodity(previousWasNumeral)) {
                commodityName = wordI;
                previousWasNumeral = false;
            } else if (isUnits(commodityName, units, wordI)) {
                units = Integer.parseInt(wordI);
            } else if (isCurrencyName(units)) {
                currencyName = wordI;
            }

        }
        commodityNameToCmmodityMap.put(commodityName, new Commodity(commodityName, new Currency(currencyName), units/numeralSum));
    }

    private boolean isCurrencyName(int units) {
        return units > -1;
    }

    private boolean isUnits(String commodityName, int units, String wordI) {
        return !commodityName.isEmpty() && !"is".equals(wordI) && units == -1;
    }

    private boolean isCommodity(boolean previousWasNumeral) {
        return previousWasNumeral;
    }

    private boolean endsWith(char charToCheckFor, String noteI) {
        return noteI.lastIndexOf(charToCheckFor) != -1;
    }

    private int countNumberOfWords(String s) {
        return s.split(" ").length;
    }

    private void parseNumeralDefinition(String currentLine) {
        Pattern numeralDefinitionPattern = Pattern.compile("\\b(.*)\\b is (.)");
        Matcher matcher = numeralDefinitionPattern.matcher(currentLine);
        matcher.find();
        String globalNumeral = matcher.group(1);
        String baseNumeral = matcher.group(2);

        Optional<Numeral> numeralOptional = Factory.fromRomanNumeral(baseNumeral);

        if (numeralOptional.isPresent()) {
            globalNumeralToBaseNumeralMap.put(globalNumeral, numeralOptional.get());
        }
    }
}
