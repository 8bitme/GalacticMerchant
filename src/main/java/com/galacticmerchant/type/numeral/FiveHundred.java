package com.galacticmerchant.type.numeral;

public class FiveHundred implements Numeral {

    private int value = 500;

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public char getRomanNumeral() {
        return 'D';
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

        FiveHundred that = (FiveHundred) o;

        return value == that.value;

    }

    @Override
    public int hashCode() {
        return value;
    }
}
