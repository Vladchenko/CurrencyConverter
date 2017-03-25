package com.example.vladislav.currencyconverter;

import com.example.vladislav.currencyconverter.beans.CurrenciesContainer;
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
        CurrenciesContainer currenciesContainer = null;

        currenciesContainer = serializer.read(CurrenciesContainer.class, source);
        log.log(Level.INFO, "Deserialized from file: " + EnvironmentVars.getmCurrenciesFile());

        return currenciesContainer;
    }

}
