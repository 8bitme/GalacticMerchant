package com.galacticmerchant.type.numeral;

import com.galacticmerchant.type.numeral.util.SetBuilder;

import java.util.Set;

public class OneHundred implements Numeral {

    private int value = 100;

    private final static Set<Numeral> NUMERALS_THAT_CAN_BE_SUBTRACTED_FROM = SetBuilder.build(new FiveHundred(), new OneThousand());

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public char getRomanNumeral() {
        return 'C';
    }

    @Override
    public int getMaxRepetitions() {
        return 3;
    }

    @Override
    public boolean canBeSubtractedFrom(Numeral numeralToSubtractFrom) {
        return NUMERALS_THAT_CAN_BE_SUBTRACTED_FROM.contains(numeralToSubtractFrom);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OneHundred that = (OneHundred) o;

        return value == that.value;

    }

    @Override
    public int hashCode() {
        return value;
    }
}
