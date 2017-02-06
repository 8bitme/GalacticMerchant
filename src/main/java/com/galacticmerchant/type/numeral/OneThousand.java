package com.galacticmerchant.type.numeral;

public class OneThousand implements Numeral {

    private int value = 1000;

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public char getRomanNumeral() {
        return 'M';
    }

    @Override
    public int getMaxRepetitions() {
        return 3;
    }

    @Override
    public boolean canBeSubtractedFrom(Numeral numeralToSubtractFrom) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OneThousand that = (OneThousand) o;

        return value == that.value;

    }

    @Override
    public int hashCode() {
        return value;
    }
}
