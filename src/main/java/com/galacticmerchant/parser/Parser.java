package com.galacticmerchant.parser;

import com.galacticmerchant.parser.definition.DefinitionParser;
import com.galacticmerchant.parser.definition.DefinitionParserFactory;
import com.galacticmerchant.parser.question.QuestionParserFactory;
import com.galacticmerchant.type.Commodity;
import com.galacticmerchant.type.numeral.Numeral;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Parser {

    private static final String UNKNOWN_ANSWER = "I have no idea what you are talking about";
    final Map<String, Numeral> globalNumeralToBaseNumeralMap = new HashMap<>();
    final Map<String, Commodity> commodityNameToCommodityMap = new HashMap<>();
    final List<String> answers = new LinkedList<>();

    private String conversionNotes;

    public Parser(String conversionNotes) {
        this.conversionNotes = conversionNotes;
    }

    public String parse() {
        Arrays.stream(conversionNotes.split("\\n")).forEach(noteI -> {
            Optional<DefinitionParser> definitionParserOptional = DefinitionParserFactory.getParserForText(noteI);
            if (definitionParserOptional.isPresent()) {
                definitionParserOptional.get().parse(noteI, globalNumeralToBaseNumeralMap, commodityNameToCommodityMap);
            } else {
                parsePricingQuestion(noteI);
            }
        });

        Optional<String> combinedResults = answers.stream().reduce((s, s2) -> s + "\n" + s2);
        return combinedResults.map(s -> s).orElse("");
    }

    private void parsePricingQuestion(String noteI) {
        String answer =
                QuestionParserFactory.getParserForText(noteI)
                        .map(questionParser -> questionParser.parse(noteI, globalNumeralToBaseNumeralMap, commodityNameToCommodityMap))
                        .orElse(UNKNOWN_ANSWER);

        answers.add(answer);
    }
}
