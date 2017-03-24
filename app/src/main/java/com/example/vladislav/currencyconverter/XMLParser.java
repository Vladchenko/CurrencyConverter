package com.example.vladislav.currencyconverter;

import com.example.vladislav.currencyconverter.beans.CurrenciesContainer;
import com.example.vladislav.currencyconverter.datasource.CurrencyDownloader;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

/**
 * Created by vladislav on 20.03.17.
 */

public class XMLParser {

    private static Logger log = getLogger(CurrencyDownloader.class.getName());
//    CurrenciesContainer currenciesContainer = null;

    public XMLParser() {
//        this.currenciesContainer = currenciesContainer;
    }

    public CurrenciesContainer parse() throws Exception {

        FileReader fileReader = null;
        Serializer serializer = new Persister();
        File source = new File(Consts.getmCurrenciesFile());
        CurrenciesContainer currenciesContainer = null;

        currenciesContainer = serializer.read(CurrenciesContainer.class, source);
        log.log(Level.INFO, "Deserialized from file: " + Consts.getmCurrenciesFile());

        return currenciesContainer;
    }

}
