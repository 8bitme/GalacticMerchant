package com.galacticmerchant.parser.definition;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DefinitionParserFactory {

    private final static List<DefinitionParser> DEFINITION_PARSERS = buildAvailableParsers();

    private static LinkedList<DefinitionParser> buildAvailableParsers() {
        LinkedList<DefinitionParser> definitionParsers = new LinkedList<>();
        definitionParsers.add(new NumeralDefinitionParser());
        definitionParsers.add(new CommodityDefinitionParser());
        return definitionParsers;
    }

    public static Optional<DefinitionParser> getParserForText(String textToParse) {
        Optional<DefinitionParser> definitionParserOptional = Optional.empty();

        for (DefinitionParser definitionParserI : DEFINITION_PARSERS) {
            if (definitionParserI.canParse(textToParse)) {
                definitionParserOptional = Optional.of(definitionParserI.instance());
                break;
            }
        }

        return definitionParserOptional;
    }
}
