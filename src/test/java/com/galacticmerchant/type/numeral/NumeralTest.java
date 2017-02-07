package com.galacticmerchant.type.numeral;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class NumeralTest {

    @Test
    public void fromRomanNumeral_validRomanNumerals_noneEmptyOptionalReturned() throws Exception {

        char[] validRomanNumerals = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};

        for (char validRomanNumeralI : validRomanNumerals) {
            assertThat(Numeral.fromRomanNumeral(validRomanNumeralI).isPresent(), is(true));
        }
    }

    @Test
    public void fromRomanNumeral_invalidRomanNumerals_emptyOptionalReturned() throws Exception {
        assertThat(Numeral.fromRomanNumeral('Z').isPresent(), is(false));
    }


    @Test
    public void fromRomanNumeral_validRomanNumeral_optionalContainsCorrectInstance() throws Exception {
        assertThat(Numeral.fromRomanNumeral('C').get(), is(equalTo(Numeral.ONE_HUNDRED)));
    }

    @Test
    public void fromRomanNumeral_invalidRomanNumeralsString_emptyOptionalReturned() throws Exception {
        assertThat(Numeral.fromRomanNumeral("Z").isPresent(), is(false));
    }

    @Test
    public void fromRomanNumeral_validRomanNumeralString_optionalContainsCorrectInstance() throws Exception {
        assertThat(Numeral.fromRomanNumeral("C").get(), is(equalTo(Numeral.ONE_HUNDRED)));
    }

}