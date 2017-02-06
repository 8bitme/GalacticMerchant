package com.galacticmerchant.type.numeral;

public class Five implements Numeral {

    private int value = 5;

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public char getRomanNumeral() {
        return 'V';
    }

    @Override
    public int getMaxRepetitions() {
        return 0;
    }

    @Override
    public boolean canBeSubtractedFrom(Numeral numeralToSubtractFrom) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Five five = (Five) o;

        return value == five.value;

    }

    @Override
    public int hashCode() {
        return value;
    }
}
