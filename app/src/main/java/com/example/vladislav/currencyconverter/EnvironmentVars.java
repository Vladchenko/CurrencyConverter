package com.example.vladislav.currencyconverter;

/**
 * Holds the variables used throught the application.
 */
public class EnvironmentVars {

    public static final String SERVICE_REPLY = "reply";
    public static final String SERVICE_FAIL = "fail";
    public static final String SERVICE_SUCCESS = "success";

    private static String mUrl = "http://www.cbr.ru/scripts/XML_daily.asp";
    private static String mCurrenciesFile;
    private static String mCurrenciesFileName = "Currencies.xml";

    private EnvironmentVars() {}

    public static String getmUrl() {
        return mUrl;
    }

    public static void setmUrl(String mUrl) {
        EnvironmentVars.mUrl = mUrl;
    }

    public static String getmCurrenciesFile() {
        return mCurrenciesFile;
    }

    public static void setmCurrenciesFile(String mCurrenciesFile) {
        EnvironmentVars.mCurrenciesFile = mCurrenciesFile;
    }

    public static String getmCurrenciesFileName() {
        return mCurrenciesFileName;
    }

    public static void setmCurrenciesFileName(String mCurrenciesFileName) {
        EnvironmentVars.mCurrenciesFileName = mCurrenciesFileName;
    }
}
