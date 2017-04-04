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

    // Currencies that will be chosen foremost.
    private static int mForemostInitialCurrency = 11;
    private static int mForemostResultingCurrency = 0;

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

    public static int getmForemostInitialCurrency() {
        return mForemostInitialCurrency;
    }

    public static void setmForemostInitialCurrency(int mForemostInitialCurrency) {
        EnvironmentVars.mForemostInitialCurrency = mForemostInitialCurrency;
    }

    public static int getmForemostResultingCurrency() {
        return mForemostResultingCurrency;
    }

    public static void setmForemostResultingCurrency(int mForemostResultingCurrency) {
        EnvironmentVars.mForemostResultingCurrency = mForemostResultingCurrency;
    }
}
