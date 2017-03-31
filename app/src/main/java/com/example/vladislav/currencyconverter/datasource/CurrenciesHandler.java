package com.example.vladislav.currencyconverter.datasource;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

/**
 * This handler establishes a connection to URL holding the currencies data, downloads it and saves
 * to file on a disk.
 */

public class CurrenciesHandler {

    private static InputStream stream = null;
    private static Logger log = getLogger(CurrenciesHandler.class.getName());

    public CurrenciesHandler() throws IOException {}

    private InputStream getStreamFromUrl(String Url) throws IOException {

        URL url = null;
        url = new URL(Url);
        InputStream in = null;
        HttpURLConnection urlConnection = null;

        urlConnection = (HttpURLConnection) url.openConnection();
        // This row helps the downloading to perform correctly.
        urlConnection.getRequestMethod();
        in = new BufferedInputStream(urlConnection.getInputStream());

        return in;
    }

    public void persistCurrenciesToFile(String Url, String filePath) throws IOException {

        InputStreamReader reader = new InputStreamReader(getStreamFromUrl(Url));
        BufferedReader bufferedReader = new BufferedReader(reader);

        File file = new File(filePath);
        PrintWriter printWriter = new PrintWriter(filePath);

        if (!file.exists()) {
            log.log(Level.WARNING, "File path specified doesn't exist.");
            if (file.isDirectory()) {
                log.log(Level.WARNING, "File path specified is a directory, make it point to a file.");
                return;
            }
            file.createNewFile();
            log.log(Level.WARNING, "File " + filePath + " created.");
        }
        log.log(Level.INFO, "Saving currency data to file named - " + filePath);

        String sCurrentLine;
        while ((sCurrentLine = bufferedReader.readLine()) != null) {
            // Replacing , with . for parseDouble would not fail.
            sCurrentLine = sCurrentLine.replace(",",".");
            printWriter.println(sCurrentLine);
        }
        log.log(Level.INFO, "Data is saved.");
        reader.close();
        printWriter.close();
    }

}