package com.galacticmerchant.type.numeral;

import com.galacticmerchant.type.numeral.util.SetBuilder;

import java.util.Set;

public class One implements Numeral {

    private int value = 1;

    private final static Set<Numeral> NUMERALS_THAT_CAN_BE_SUBTRACTED_FROM = SetBuilder.build(new Five(), new Ten());

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public char getRomanNumeral() {
        return 'I';
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

        One one = (One) o;

        return value == one.value;

    }

    @Override
    public int hashCode() {
        return value;
    }
}
