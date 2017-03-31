package com.example.vladislav.currencyconverter.logic;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Logic to convert the currency from one to another.
 */

public class CurrencyConverter {

    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public static String convertCurrency(double amount, double initialQuotation, double resultingQuotation) {

        String result;

        if (amount <= 0
                || initialQuotation <=0
                || resultingQuotation <=0) {
            throw new ArithmeticException("Some argument is zero or negative.");
        }

        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        double value = amount * initialQuotation / resultingQuotation;

        if (value == (int)value) {
            result = Integer.toString((int)value);
        } else {
            result = decimalFormat.format(value);
        }

        return result.replace(".",",");
    }

}
