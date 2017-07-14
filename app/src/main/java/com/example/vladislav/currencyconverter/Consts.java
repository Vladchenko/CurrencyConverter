package com.example.vladislav.currencyconverter;

/**
 * Created by vladislav on 20.03.17.
 */
public class Consts {

    public static final String EXCEPTION = "exception";
    private static String mUrl = "http://www.cbr.ru/scripts/XML_daily.asp";
    private static String mCurrenciesFile = "Currencies.xml";
    private static Consts sConsts = new Consts();

    public static Consts getInstance() {
        return sConsts;
    }

    private Consts() {}

    public static String getUrl() {
        return mUrl;
    }

    public static void setUrl(String mUrl) {
        Consts.mUrl = mUrl;
    }

    public static String getCurrenciesFile() {
        return mCurrenciesFile;
    }

    public static void setCurrenciesFile(String mCurrenciesFile) {
        Consts.mCurrenciesFile = mCurrenciesFile;
    }

}
