package com.example.vladislav.currencyconverter;

import com.example.vladislav.currencyconverter.datasource.CurrencyDownloader;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by vladislav on 23.03.17.
 */

public class CurrencyDownloaderTest {

    private CurrencyDownloader mCurrencyDownloader = new CurrencyDownloader(
            "http://www.cbr.ru/scripts/XML_daily.asp", Consts.getCurrenciesFile());

    public CurrencyDownloaderTest() throws IOException {
    }

    @Test(expected = IOException.class)
    public void getStreamFromURLTestWrongURL() throws IOException {
        mCurrencyDownloader.getStreamFromUrl("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getStreamFromURLTestWrongURL2() throws IOException {
        mCurrencyDownloader.getStreamFromUrl("http:");
    }

    @Test(expected = FileNotFoundException.class)
    public void getStreamFromURLTestWrongURL3() throws IOException {
        mCurrencyDownloader.getStreamFromUrl("http://www.cbr.ru/scripts");
    }

    @Test
    public void getStreamFromURLTestCorrectURL() throws IOException {
        mCurrencyDownloader.getStreamFromUrl("http://www.cbr.ru/scripts/XML_daily.asp");
    }

}
