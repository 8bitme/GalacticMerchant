package com.galacticmerchant.type.numeral;

public class Fifty implements Numeral {

    private int value = 50;

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public char getRomanNumeral() {
        return 'L';
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

        Fifty fifty = (Fifty) o;

        return value == fifty.value;

    }

    @Override
    public int hashCode() {
        return value;
    }
}
