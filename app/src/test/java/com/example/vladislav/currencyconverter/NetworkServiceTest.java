package com.example.vladislav.currencyconverter;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.test.mock.MockApplication;
import android.test.mock.MockContext;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.runners.MockitoJUnitRunner;

import static android.content.IntentFilter.SYSTEM_HIGH_PRIORITY;

/**
 * Created by vladislav on 23.03.17.
 */

//@RunWith(MockitoJUnitRunner.class)
public class NetworkServiceTest {

    IntentFilter mIntentFilter;
    Intent mIntent;
    Application mMockContext = new Application();

//    @Test
    // Too complex test, dunno how to implement it.
    public void sendBroadcastWhenURLWrongText() {

        final Boolean[] exceptionBroacastSend = {false};
        // Setting a fake incorrect URL.
        Consts.setmUrl("Лажа");
//        NetworkService myServiceMock = new NetworkService();

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("!!!");
                exceptionBroacastSend[0] = true;
            }
        };

//        mMockContext = mock(Application.class);
//        when(mMockContext.registerReceiver(broadcastReceiver, mIntentFilter)).thenReturn(null);

        mIntentFilter = new IntentFilter(Consts.EXCEPTION);
//        mIntentFilter.setPriority(SYSTEM_HIGH_PRIORITY);
        mMockContext.registerReceiver(broadcastReceiver, mIntentFilter);

        // Starting a currency downloading service.
        Intent mIntent = new Intent(mMockContext, NetworkService.class);
//        mIntent = new Intent().setAction(Consts.EXCEPTION).putExtra(Consts.EXCEPTION, "error");
        mMockContext.startService(mIntent);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(exceptionBroacastSend[0]);

    }

}
