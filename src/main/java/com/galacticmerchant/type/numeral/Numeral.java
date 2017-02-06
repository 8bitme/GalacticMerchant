package com.galacticmerchant.type.numeral;

public interface Numeral {
    int getValue();

    char getRomanNumeral();

    int getMaxRepetitions();

    boolean canBeSubtractedFrom(Numeral numeralToSubtractFrom);
}
