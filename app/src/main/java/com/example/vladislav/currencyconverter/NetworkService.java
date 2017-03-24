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
//        Consts.setmCurrenciesFile(mCurrenciesFile);
//        System.out.println(Consts.getmCurrenciesFile());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            System.out.println("onHandleIntent -  " + Consts.getmCurrenciesFile());
            System.out.println("onHandleIntent -  " + Consts.getmUrl());
            new CurrencyDownloader().saveXMLToFile(
                    Consts.getmUrl(),
                    Consts.getmCurrenciesFile());
        } catch (IOException e) {
//            e.printStackTrace();
            Intent intentInformInitialActivity = new Intent().
                    setAction(Consts.EXCEPTION).
                    putExtra(Consts.EXCEPTION,
                            e.toString());
            sendBroadcast(intentInformInitialActivity);
            Log.e("NetworkService",e.getMessage());
        }

    }

}
