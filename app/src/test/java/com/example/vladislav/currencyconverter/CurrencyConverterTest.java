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

    @Test(expected=ArithmeticException.class)
    public void convertCurrencyTestNegativeAmount() {
        assertEquals(convertCurrency(-100,8,6),"49");
    }

    @Test(expected=ArithmeticException.class)
    public void convertCurrencyTestNegativeInitialCurrencyQuotation() {
        assertEquals(convertCurrency(26.56,-1,28.62),"49");
    }

    @Test(expected=ArithmeticException.class)
    public void convertCurrencyTestNegativeResultingCurrencyQuotation() {
        assertEquals(convertCurrency(50,20.34,-6.1),"49");
    }
}