package com.example.vladislav.currencyconverter;

/**
 * Holds the variables used throught the application.
 */
public class EnvironmentVars {

    public static final String SERVICE_REPLY = "reply";
    public static final String SERVICE_FAIL = "fail";
    public static final String SERVICE_SUCCESS = "success";

    private static String sUrl = "http://www.cbr.ru/scripts/XML_daily.asp";
    private static String sCurrenciesFile;
    private static String sCurrenciesFileName = "Currencies.xml";

    private EnvironmentVars() {}

    public static String getUrl() {
        return sUrl;
    }

    public static void setUrl(String mUrl) {
        EnvironmentVars.sUrl = mUrl;
    }

    public static String getCurrenciesFile() {
        return sCurrenciesFile;
    }

    public static void setCurrenciesFile(String mCurrenciesFile) {
        EnvironmentVars.sCurrenciesFile = mCurrenciesFile;
    }

    public static String getCurrenciesFileName() {
        return sCurrenciesFileName;
    }

    public static void setCurrenciesFileName(String mCurrenciesFileName) {
        EnvironmentVars.sCurrenciesFileName = mCurrenciesFileName;
    }
}
