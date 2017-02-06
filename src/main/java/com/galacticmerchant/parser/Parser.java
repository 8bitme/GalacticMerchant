package com.galacticmerchant.parser;

import com.galacticmerchant.type.Commodity;
import com.galacticmerchant.type.Currency;
import com.galacticmerchant.type.numeral.Numeral;
import com.galacticmerchant.type.numeral.util.Factory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Parser {

    public static final String NUMERAL_QUESTION_PREFIX = "how much is";
    final Map<String, Numeral> globalNumeralToBaseNumeralMap = new HashMap<>();
    final Map<String, Commodity> commodityNameToCmmodityMap = new HashMap<>();
    final List<String> answers = new LinkedList<>();

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
            } else {
                parsePricingQuestion(noteI);
            }
        });
    }

    private void parsePricingQuestion(String noteI) {
        if (noteI.startsWith(NUMERAL_QUESTION_PREFIX)) {
            String numeralStringToParse = noteI.substring(NUMERAL_QUESTION_PREFIX.length() + 1, noteI.lastIndexOf("?")).trim();

            List<Integer> globalNumeralValues = Arrays.stream(numeralStringToParse.split(" ")).map(s -> globalNumeralToBaseNumeralMap.get(s).getValue()).collect(Collectors.toList());
            double sumOfNumerals = sumNumeralValues(globalNumeralValues);

            answers.add(numeralStringToParse + " is " + (int) sumOfNumerals);
        }

    }

    private void parseCommodityDefinition(String noteI) {

        String commodityName = "";
        String currencyName = "";
        double units = -1;
        boolean previousWasNumeral = false;
        List<Integer> numeralValues = new ArrayList<>();
        String[] words = noteI.split(" ");

        for (String wordI : words) {
            boolean isNumeral = globalNumeralToBaseNumeralMap.containsKey(wordI);
            if (isNumeral) {
                previousWasNumeral = true;
                numeralValues.add(globalNumeralToBaseNumeralMap.get(wordI).getValue());
            } else if (isCommodity(previousWasNumeral)) {
                commodityName = wordI;
                previousWasNumeral = false;
            } else if (isUnits(commodityName, units, wordI)) {
                units = Integer.parseInt(wordI);
            } else if (isCurrencyName(units)) {
                currencyName = wordI;
            }

        }

        double numeralSum = sumNumeralValues(numeralValues);
        commodityNameToCmmodityMap.put(commodityName, new Commodity(commodityName, new Currency(currencyName), (units / numeralSum)));
    }

    private double sumNumeralValues(List<Integer> numeralValues) {
        double numeralSum = 0;
        boolean previousWasLessThanCurrent = false;
        for (int i = 0; i < numeralValues.size(); i++) {
            Integer currentValue = numeralValues.get(i);

            if (i != numeralValues.size() - 1) {
                Integer nextValue = numeralValues.get(i + 1);

                if (currentValue < nextValue) {
                    numeralSum += nextValue - currentValue;
                    previousWasLessThanCurrent = true;
                } else if (previousWasLessThanCurrent) {
                    previousWasLessThanCurrent = false;
                } else {
                    numeralSum += currentValue;
                }
            } else if (!previousWasLessThanCurrent) {
                numeralSum += currentValue;
            }
        }
        return numeralSum;
    }

    private boolean isCurrencyName(double units) {
        return units > -1;
    }

    private boolean isUnits(String commodityName, double units, String wordI) {
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
