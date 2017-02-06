package com.galacticmerchant.type.numeral.util;

import com.galacticmerchant.type.numeral.OneHundred;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class FactoryTest {

    @Test
    public void fromRomanNumeral_validRomanNumerals_noneEmptyOptionalReturned() throws Exception {

        char[] validRomanNumerals = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};

        for (char validRomanNumeralI : validRomanNumerals) {
            assertThat(Factory.fromRomanNumeral(validRomanNumeralI).isPresent(), is(true));
        }
    }

    @Test
    public void fromRomanNumeral_invalidRomanNumerals_emptyOptionalReturned() throws Exception {
        assertThat(Factory.fromRomanNumeral('Z').isPresent(), is(false));
    }


    @Test
    public void fromRomanNumeral_validRomanNumeral_optionalContainsCorrectInstance() throws Exception {
        assertThat(Factory.fromRomanNumeral('C').get(), is(equalTo(new OneHundred())));
    }

}