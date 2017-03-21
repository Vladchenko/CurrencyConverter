package com.example.vladislav.currencyconverter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

/**
 * Created by vladislav on 18.03.17.
 */

public class CurrencyDownloader {

    private static InputStream stream = null;
    private static Logger log = getLogger(CurrencyDownloader.class.getName());
//    private String Url;

    public CurrencyDownloader(String Url, String filePath) throws IOException {

//        stream = getStreamFromUrl(Url);
//        readStream(stream);

        stream = getStreamFromUrl(Url);
        saveXMLToFile(stream, filePath);

        new XMLParser().parse();

    }

    private InputStream getStreamFromUrl(String Url) throws IOException {

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

    // TODO - Maybe make a separate class - CurrencyParser.
    private void parseXMLFromStream() {
        // Is it even possible to parse xml from inputstream ?
    }

    private void saveXMLToFile(InputStream inputStream, String filePath) throws IOException {

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
//        System.out.println(file.length());
        fileWriter = new FileWriter(file);
        bufferedWriter = new BufferedWriter(fileWriter);
        log.log(Level.INFO, "Saving currency data to file named - " + filePath);

        String sCurrentLine;
        while ((sCurrentLine = bufferedReader.readLine()) != null) {
            System.out.println(sCurrentLine);
            bufferedWriter.write(sCurrentLine);
        }
        log.log(Level.INFO, "Data is saved.");

        inputStream.close();
        fileWriter.close();
//        bufferedWriter.close();
    }

}
