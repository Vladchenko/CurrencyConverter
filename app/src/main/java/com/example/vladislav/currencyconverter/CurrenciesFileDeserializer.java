package com.example.vladislav.currencyconverter;

import com.example.vladislav.currencyconverter.beans.CurrenciesContainer;
import com.example.vladislav.currencyconverter.datasource.CurrenciesHandler;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

/**
 * Uploading, parsing and deserializing the currencies data into java beans.
 */

public class CurrenciesFileDeserializer {

    private static Logger sLog = getLogger(CurrenciesHandler.class.getName());

    public CurrenciesFileDeserializer() {
    }

    public CurrenciesContainer parse() throws Exception {

        Serializer serializer = new Persister();
        File source = new File(EnvironmentVars.getCurrenciesFile());
        CurrenciesContainer currenciesContainer = null;

        currenciesContainer = serializer.read(CurrenciesContainer.class, source);
        sLog.log(Level.INFO, "Deserialized from file: " + EnvironmentVars.getCurrenciesFile());

        return currenciesContainer;
    }

}
