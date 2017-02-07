package com.galacticmerchant.type.numeral.util;

import com.galacticmerchant.type.numeral.Numeral;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NumeralRule {

    private static final Map<Numeral, Set<Numeral>> NUMERAL_TO_NUMERALS_CAN_BE_SUBTRACTED_FROM_MAP = buildNumeralRulesMap();

    private static HashMap<Numeral, Set<Numeral>> buildNumeralRulesMap() {
        HashMap<Numeral, Set<Numeral>> numeralSetHashMap = new HashMap<>();
        numeralSetHashMap.put(Numeral.ONE, SetBuilder.build(Numeral.FIVE, Numeral.TEN));
        numeralSetHashMap.put(Numeral.TEN, SetBuilder.build(Numeral.FIFTY, Numeral.ONE_HUNDRED));
        numeralSetHashMap.put(Numeral.ONE_HUNDRED, SetBuilder.build(Numeral.FIVE_HUNDRED, Numeral.ONE_THOUSAND));
        return numeralSetHashMap;
    }

    public static boolean canBeSubtracted(Numeral numeralToCheck) {
        return NUMERAL_TO_NUMERALS_CAN_BE_SUBTRACTED_FROM_MAP.containsKey(numeralToCheck);
    }

    public static boolean cannotSubtractNumeralFromAnother(Numeral numeralToSubtract, Numeral numeralToSubtractFrom) {
        return !NUMERAL_TO_NUMERALS_CAN_BE_SUBTRACTED_FROM_MAP.containsKey(numeralToSubtract) || !NUMERAL_TO_NUMERALS_CAN_BE_SUBTRACTED_FROM_MAP.get(numeralToSubtract).contains(numeralToSubtractFrom);
    }


}
