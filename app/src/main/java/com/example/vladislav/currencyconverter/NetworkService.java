package com.example.vladislav.currencyconverter;

import android.app.IntentService;
import android.content.Intent;

import com.example.vladislav.currencyconverter.datasource.CurrencyDownloader;

import java.io.IOException;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NetworkService extends IntentService {

    public static String CURRENCIES_FILE = null;

    public NetworkService() {
        super("NetworkService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        CURRENCIES_FILE = getBaseContext().getFilesDir().getPath().toString()
                + "/Currencies.xml";
        //Environment.DIRECTORY_DOWNLOADS
        try {
            CurrencyDownloader currencyDownloader =
                    new CurrencyDownloader("http://www.cbr.ru/scripts/XML_daily.asp",
                            CURRENCIES_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            return;
//            Log.e("NetworkService",e.getMessage());
        }

    }

}
