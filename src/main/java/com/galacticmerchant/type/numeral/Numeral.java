package com.galacticmerchant.type.numeral;

import java.util.Optional;

public enum Numeral {

    ONE(1, 'I', 3),
    FIVE(5, 'V', 0),
    TEN(10, 'X', 3),
    FIFTY(50, 'L', 0),
    ONE_HUNDRED(100, 'C', 3),
    FIVE_HUNDRED(500, 'D', 0),
    ONE_THOUSAND(1000, 'M', 3);

    private final int value;
    private final char romanNumeral;
    private final int maxRepetitions;

    Numeral(int value, char romanNumeral, int maxRepetitions) {
        this.value = value;
        this.romanNumeral = romanNumeral;
        this.maxRepetitions = maxRepetitions;
    }

    public int getValue() {
        return value;
    }

    public char getRomanNumeral() {
        return romanNumeral;
    }

    public int getMaxRepetitions() {
        return maxRepetitions;
    }

    public static Optional<Numeral> fromRomanNumeral(char romanNumeral) {
        Optional<Numeral> associatedNumeral = Optional.empty();

        for (Numeral numeralI : values()) {
            if (numeralI.getRomanNumeral() == romanNumeral) {
                associatedNumeral = Optional.of(numeralI);
                break;
            }
        }
        return associatedNumeral;
    }

    public static Optional<Numeral> fromRomanNumeral(String romanNumeral) {
        return fromRomanNumeral(romanNumeral.charAt(0));
    }


}
