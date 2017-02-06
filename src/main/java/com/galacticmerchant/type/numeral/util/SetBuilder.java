package com.galacticmerchant.type.numeral.util;

import com.galacticmerchant.type.numeral.Numeral;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SetBuilder {

    public static Set<Numeral> build(Numeral numeral1, Numeral numeral2, Numeral... numerals) {
        Set<Numeral> temporarySet = new HashSet<>();
        temporarySet.add(numeral1);
        temporarySet.add(numeral2);

        Collections.addAll(temporarySet, numerals);

        return Collections.unmodifiableSet(temporarySet);
    }
}
