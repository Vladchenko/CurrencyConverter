package com.example.vladislav.currencyconverter;

/**
 * Created by vladislav on 20.03.17.
 */
public class Consts {

    public static final String REPLY = "reply";
    private static String mUrl = "http://www.cbr.ru/scripts/XML_daily.asp";
    private static String mCurrenciesFile;
    private static String mCurrenciesFileName = "Currencies.xml";

    private static Consts ourInstance = new Consts();

    public static Consts getInstance() {
        return ourInstance;
    }

    private Consts() {}

    public static String getmUrl() {
        return mUrl;
    }

    public static void setmUrl(String mUrl) {
        Consts.mUrl = mUrl;
    }

    public static String getmCurrenciesFile() {
        return mCurrenciesFile;
    }

    public static void setmCurrenciesFile(String mCurrenciesFile) {
        Consts.mCurrenciesFile = mCurrenciesFile;
    }

    public static String getmCurrenciesFileName() {
        return mCurrenciesFileName;
    }

    public static void setmCurrenciesFileName(String mCurrenciesFileName) {
        Consts.mCurrenciesFileName = mCurrenciesFileName;
    }
}
