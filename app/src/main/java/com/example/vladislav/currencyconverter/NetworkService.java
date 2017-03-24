package com.example.vladislav.currencyconverter;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

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

//    public static String mCurrenciesFile = null;

    public NetworkService() {
        super("NetworkService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent intentInformInitialActivity = new Intent();
        try {
            new CurrencyDownloader().saveXMLToFile(
                    Consts.getmUrl(),
                    Consts.getmCurrenciesFile());
            intentInformInitialActivity.
                    setAction(Consts.REPLY).
                    putExtra(Consts.REPLY,"Success");
            Log.e("NetworkService","Currencies were loaded successfully.");
        } catch (IOException e) {
            intentInformInitialActivity.
                    setAction(Consts.REPLY).
                    putExtra(Consts.REPLY,"Fail");
            Log.e("NetworkService",e.getMessage());
        }
        sendBroadcast(intentInformInitialActivity);

    }

}
