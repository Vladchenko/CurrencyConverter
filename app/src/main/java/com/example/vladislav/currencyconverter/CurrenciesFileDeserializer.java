package com.example.vladislav.currencyconverter;

import com.example.vladislav.currencyconverter.beans.CurrenciesContainer;
import com.example.vladislav.currencyconverter.beans.CurrencyBean;
import com.example.vladislav.currencyconverter.datasource.CurrenciesHandler;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

/**
 * Uploading, parsing and deserializing the currencies data into java beans.
 */

public class CurrenciesFileDeserializer {

    private static Logger log = getLogger(CurrenciesHandler.class.getName());

    public CurrenciesFileDeserializer() {
    }

    public CurrenciesContainer parse() throws Exception {

        FileReader fileReader = null;
        Serializer serializer = new Persister();
        File source = new File(EnvironmentVars.getmCurrenciesFile());
        CurrenciesContainer currenciesContainer = new CurrenciesContainer();

        // Adding a ruble currency to the start of a currencies list.
        currenciesContainer.addCurrency(getRubleCurrencyBean());
        // Adding all the rest currencies from a deserializer.
        currenciesContainer.addCurrencies(serializer.read(CurrenciesContainer.class, source));
        log.log(Level.INFO, "Deserialized from file: " + EnvironmentVars.getmCurrenciesFile());

        return currenciesContainer;
    }

    private CurrencyBean getRubleCurrencyBean() {
        CurrencyBean currencyBean = new CurrencyBean();
        currencyBean.setCharacterCode("RUB");
        currencyBean.setNumericCode(643);
        currencyBean.setName("Ruble");
        currencyBean.setValue("1");
        return currencyBean;
    }

}
