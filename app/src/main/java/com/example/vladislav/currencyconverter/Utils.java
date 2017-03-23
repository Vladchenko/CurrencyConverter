package com.example.vladislav.currencyconverter;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by vladislav on 22.03.17.
 */

public class Utils {

    public static void showToast(Context context, String messageToShow) {
        (new Toast(context).makeText(
                context,
                messageToShow,
                Toast.LENGTH_LONG)).show();
    }

}
