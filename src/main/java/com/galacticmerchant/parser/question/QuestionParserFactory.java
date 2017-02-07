package com.galacticmerchant.parser.question;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class QuestionParserFactory {

    private static final List<QuestionParser> QUESTION_PARSERS = buildAvailableParsers();

    private static LinkedList<QuestionParser> buildAvailableParsers() {
        LinkedList<QuestionParser> questionParsers = new LinkedList<>();
        questionParsers.add(new PricingQuestionParser());
        questionParsers.add(new CommodityQuestionParser());
        return questionParsers;
    }

    public static Optional<QuestionParser> getParserForText(String textToParse) {
        Optional<QuestionParser> questionParserOptional = Optional.empty();

        for (QuestionParser questionParserI : QUESTION_PARSERS) {
            if (questionParserI.canParse(textToParse)) {
                questionParserOptional = Optional.of(questionParserI.instance());
                break;
            }
        }

        return questionParserOptional;
    }
}
