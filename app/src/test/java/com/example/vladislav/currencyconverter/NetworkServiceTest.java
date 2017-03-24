package com.example.vladislav.currencyconverter;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;

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

        mIntentFilter = new IntentFilter(Consts.REPLY);
//        mIntentFilter.setPriority(SYSTEM_HIGH_PRIORITY);
        mMockContext.registerReceiver(broadcastReceiver, mIntentFilter);

        // Starting a currency downloading service.
        Intent mIntent = new Intent(mMockContext, NetworkService.class);
//        mIntent = new Intent().setAction(Consts.REPLY).putExtra(Consts.REPLY, "error");
        mMockContext.startService(mIntent);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(exceptionBroacastSend[0]);

    }

}
