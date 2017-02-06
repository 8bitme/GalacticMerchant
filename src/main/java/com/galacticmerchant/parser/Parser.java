package com.galacticmerchant.parser;

import com.galacticmerchant.type.numeral.Numeral;
import com.galacticmerchant.type.numeral.util.Factory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private String conversionNotes;

    final Map<String, Numeral> globalNumeralToBaseNumeralMap = new HashMap<>();

    public Parser(String conversionNotes) {
        this.conversionNotes = conversionNotes;
    }

    public void parse() {
        Arrays.stream(conversionNotes.split("\\n")).forEach(this::parseIndividualNumeralDefinition);
    }

    private void parseIndividualNumeralDefinition(String currentLine) {
        Pattern numeralDefinitionPattern = Pattern.compile("(.*) is (.)");
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
