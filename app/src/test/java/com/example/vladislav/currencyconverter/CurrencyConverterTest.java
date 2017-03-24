package com.example.vladislav.currencyconverter;

import org.junit.Test;

import static com.example.vladislav.currencyconverter.logic.CurrencyConverter.convertCurrency;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CurrencyConverterTest {

    @Test
    public void convertCurrencyTestEquals() {
        assertEquals(convertCurrency(100,1,1),"100");
    }

    @Test
    public void convertCurrencyTestEquals2() {
        assertEquals(convertCurrency(200,2,3),"133,34");
    }

}