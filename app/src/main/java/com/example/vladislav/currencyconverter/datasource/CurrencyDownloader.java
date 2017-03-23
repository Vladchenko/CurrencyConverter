package com.example.vladislav.currencyconverter.datasource;

import android.support.annotation.VisibleForTesting;

import com.example.vladislav.currencyconverter.Consts;
import com.example.vladislav.currencyconverter.Utils;
import com.example.vladislav.currencyconverter.WrongURLException;
import com.example.vladislav.currencyconverter.XMLParser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

/**
 * Created by vladislav on 18.03.17.
 */

public class CurrencyDownloader {

    private static InputStream stream = null;
    private static Logger log = getLogger(CurrencyDownloader.class.getName());

    public CurrencyDownloader(String Url, String filePath) throws IOException {

        saveXMLToFile(getStreamFromUrl(Url), filePath);
        new XMLParser().parse();

    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public InputStream getStreamFromUrl(String Url) throws IOException {

        URL url = null;
        url = new URL(Url);
        InputStream in = null;
        HttpURLConnection urlConnection = null;

        urlConnection = (HttpURLConnection) url.openConnection();
        // Why does it make a downloading work ?
        urlConnection.getRequestMethod();
        in = new BufferedInputStream(urlConnection.getInputStream());

        return in;
    }

    private void readStream(InputStream in) {

        InputStreamReader isw = new InputStreamReader(in);

        int data = 0;
        try {
            data = isw.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (data != -1) {
            char current = (char) data;
            try {
                data = isw.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.print(current);
        }
        try {
            isw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public void saveXMLToFile(InputStream inputStream, String filePath) throws IOException {

        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);

        File file = new File(filePath);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        if (!file.exists()) {
            log.log(Level.WARNING, "File path specified doesn't exist.");
            if (file.isDirectory()) {
                log.log(Level.WARNING, "File path specified is a directory, make it point to a file.");
                return;
            }
            file.createNewFile();
            log.log(Level.WARNING, "File " + filePath + " created.");
        }
        fileWriter = new FileWriter(file);
        bufferedWriter = new BufferedWriter(fileWriter);
        log.log(Level.INFO, "Saving currency data to file named - " + filePath);

        String sCurrentLine;
        while ((sCurrentLine = bufferedReader.readLine()) != null) {
//            System.out.println(sCurrentLine);
            bufferedWriter.write(sCurrentLine);
        }
        log.log(Level.INFO, "Data is saved.");

        inputStream.close();
        fileWriter.close();
//        bufferedWriter.close();
//        System.out.println(file.length());
    }

}
