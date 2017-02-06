package com.galacticmerchant.type.numeral.util;

import com.galacticmerchant.type.numeral.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Factory {

    private static final Map<Character, Numeral> ROMAN_NUMERAL_TO_NUMERAL_MAPPING = buildNumeralMapping();

    private static Map<Character, Numeral> buildNumeralMapping() {
        Map<Character, Numeral> tempMap = new HashMap<>();
        tempMap.put('I', new One());
        tempMap.put('V', new Five());
        tempMap.put('X', new Ten());
        tempMap.put('L', new Fifty());
        tempMap.put('C', new OneHundred());
        tempMap.put('D', new FiveHundred());
        tempMap.put('M', new OneThousand());
        return tempMap;
    }

    public static Optional<Numeral> fromRomanNumeral(char romanNumeral) {
        Optional<Numeral> associatedNumeral = Optional.empty();
        char romanNumeralToUpperCase = Character.toUpperCase(romanNumeral);

        if (ROMAN_NUMERAL_TO_NUMERAL_MAPPING.containsKey(romanNumeralToUpperCase)) {
            associatedNumeral = Optional.of(ROMAN_NUMERAL_TO_NUMERAL_MAPPING.get(romanNumeralToUpperCase));
        }

        return associatedNumeral;
    }
}
