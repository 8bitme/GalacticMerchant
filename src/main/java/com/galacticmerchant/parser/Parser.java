package com.galacticmerchant.parser;

import com.galacticmerchant.parser.definition.DefinitionParser;
import com.galacticmerchant.parser.definition.DefinitionParserFactory;
import com.galacticmerchant.parser.question.QuestionParserFactory;
import com.galacticmerchant.type.Commodity;
import com.galacticmerchant.type.numeral.Numeral;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class Parser {

    private static final String UNKNOWN_ANSWER = "I have no idea what you are talking about";
    final Map<String, Numeral> globalNumeralToBaseNumeralMap = new HashMap<>();
    final Map<String, Commodity> commodityNameToCommodityMap = new HashMap<>();
    final List<String> answers = new LinkedList<>();

    private String conversionNotes;

    private Parser(String conversionNotes) {
        this.conversionNotes = conversionNotes;
    }

    public static Parser fromNotesString(String conversionNotes) {
        return new Parser(conversionNotes);
    }

    public static Parser fromFile(String filePathAndFileName) throws IOException {
        String conversionNotes;
        try (FileInputStream inputStream = new FileInputStream(filePathAndFileName)) {
            conversionNotes = IOUtils.toString(inputStream, Charset.forName("UTF-8"));
        }
        return new Parser(conversionNotes);
    }

    public static void fromFileAndOutput(String filePathAndFileName) throws IOException {
        fromFile(filePathAndFileName).parse();
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
        String finalOutput = combinedResults.map(s -> s).orElse("");
        System.out.println(finalOutput);
        return finalOutput;
    }

    private void parsePricingQuestion(String noteI) {
        String answer =
                QuestionParserFactory.getParserForText(noteI)
                        .map(questionParser -> questionParser.parse(noteI, globalNumeralToBaseNumeralMap, commodityNameToCommodityMap))
                        .orElse(UNKNOWN_ANSWER);

        answers.add(answer);
    }
}
