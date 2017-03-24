package com.example.vladislav.currencyconverter.datasource;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by vladislav on 23.03.17.
 */

public class CurrencyDownloader2 extends AsyncTaskLoader<CurrenciesOperating> {

    public CurrencyDownloader2(Context context) {
        super(context);
    }

    @Override
    public CurrenciesOperating loadInBackground() {
        return null;
    }

    @Override
    public void deliverResult(CurrenciesOperating data) {
        super.deliverResult(data);
    }
}
