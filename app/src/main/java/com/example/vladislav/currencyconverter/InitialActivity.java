package com.example.vladislav.currencyconverter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vladislav.currencyconverter.beans.CurrenciesContainer;
import com.example.vladislav.currencyconverter.datasource.CurrenciesHandlingService;
import com.example.vladislav.currencyconverter.logic.CurrencyConverter;

import java.util.ArrayList;
import java.util.List;

import static android.content.IntentFilter.SYSTEM_HIGH_PRIORITY;

/**
 * Holds the presentation and logic layer for the app.
 */
public class InitialActivity extends AppCompatActivity {

    private CurrenciesContainer mCurrencyContainer;
    private Spinner initialCurrencySpinner;      // Spinner for a initial currency (to convert from);
    private Spinner resultingCurrencySpinner;    // Spinner for a resulting currency  (to convert to);
    private EditText mInitialCurrencyEditText;   // Edit text for a currency to convert from.
    private EditText mResultingCurrencyEditText; // Edit text for a currency to convert to.
    private TextView mInitialCurrencyTextView;   // Quotation for a currency to convert from.
    private TextView mResultingCurrencyTextView; // Quotation for a currency to convert from.
    private BroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        EnvironmentVars.setmCurrenciesFile(getBaseContext().getFilesDir().getPath().toString()
                + "/" + EnvironmentVars.getmCurrenciesFileName());

        if (!CommonUtils.isURLValid(EnvironmentVars.getmUrl())) {
            Log.e(getClass().getCanonicalName(),"URL is incorrect!");
            return;
        }
        if (!CommonUtils.isFilePathValid(EnvironmentVars.getmCurrenciesFile())) {
            Log.e(getClass().getCanonicalName(), "Filepath is incorrect!, program terminated.");
            return;
        }

        // Starting a currency downloading service.
        Intent intent = new Intent(InitialActivity.this, CurrenciesHandlingService.class);
        startService(intent);

        // Showing a Progress Dialog, while a downloading is performed.
        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.Theme_MyDialog);
        progressDialog.setTitle("Downloading currencies");
        progressDialog.setMessage("Please wait for a currencies quotations to download from web.");
        progressDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        progressDialog.show();

        mCurrencyContainer = new CurrenciesContainer();

        // Checking if there was an exception message broadcasted.
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String exceptionString = intent.getExtras().getString(EnvironmentVars.SERVICE_REPLY);
                if (EnvironmentVars.SERVICE_FAIL.equals(exceptionString)) {
                    try {
                        mCurrencyContainer = new CurrenciesFileDeserializer().parse();
                        progressDialog.cancel();
                        CommonUtils.showToast(getApplicationContext(),
                                "Currencies web downloading failed, but a cache file downloading succeeded.");
                    } catch (Exception ex) {
                        progressDialog.setMessage("Currencies web downloading failed, cache file is " +
                                "also corrupt - restart the app. Make sure the internet connection " +
                                "is on the next time!");
                    }
                }
                if (EnvironmentVars.SERVICE_SUCCESS.equals(exceptionString)) {
                    progressDialog.cancel();
                    // Success in loading from a web.
                    try {
                        mCurrencyContainer = new CurrenciesFileDeserializer().parse();
                        // Success in loading from a file.
                        CommonUtils.showToast(getApplicationContext(),
                                "Currencies were successfully loaded from a web.");
                    } catch (Exception ex) {
                        progressDialog.setMessage("Currencies web downloading failed, cache file is " +
                                "also corrupt - restart the app. Make sure the internet connection " +
                                "is on the next time!");
                    }
                }
                submitCurrenciesCharCodesToSpinners();
            }
        };

        IntentFilter mIntentFilter = new IntentFilter(EnvironmentVars.SERVICE_REPLY);
        mIntentFilter.setPriority(SYSTEM_HIGH_PRIORITY);
        registerReceiver(mBroadcastReceiver, mIntentFilter);

        mInitialCurrencyEditText = (EditText) findViewById(R.id.initial_currency_edit_text);
        mResultingCurrencyEditText = (EditText) findViewById(R.id.resulting_currency_edit_text);
        mInitialCurrencyTextView = (TextView) findViewById(R.id.initial_currency_quotation_text_view);
        mResultingCurrencyTextView = (TextView) findViewById(R.id.resulting_currency_quotation_text_view);

        Button convertButton = (Button) findViewById(R.id.convert_button);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (initialCurrencySpinner.getSelectedItem().toString().equals(
                        resultingCurrencySpinner.getSelectedItem().toString())) {
                    // Saying that currencies match
                    CommonUtils.showToast(getApplicationContext(), "Currencies match, choose a different one");
                } else {
                    // Perform calculation only if an amount to be converted is present in a respective text_edit.
                    if (!mInitialCurrencyEditText.getText().toString().equals("")) {
                        try {
                            String operationResult = CurrencyConverter.convertCurrency(
                                    Double.parseDouble(mInitialCurrencyEditText.getText().toString()),
                                    Double.parseDouble(mCurrencyContainer.getmCurrenciesList().get(
                                            initialCurrencySpinner.getSelectedItemPosition()).getValue()),
                                    Double.parseDouble(mCurrencyContainer.getmCurrenciesList().get(
                                            resultingCurrencySpinner.getSelectedItemPosition()).getValue()));
                            mResultingCurrencyEditText.setText(operationResult);
                        } catch (ArithmeticException | NumberFormatException ex) {
                            mResultingCurrencyEditText.setText("n/a");
                            CommonUtils.showToast(getApplicationContext(), "Some value in currency " +
                                    "convertion is wrong. Check amount and quotations.");
                        }
                    } else {
                        CommonUtils.showToast(getApplicationContext(), "Currency amount is absent");
                    }
                }
            }
        });


    }

    private void submitCurrenciesCharCodesToSpinners() {

        ArrayAdapter<String> currenciesAdapter;
        List<String> charCodeList = new ArrayList<>();

        for (int i = 0; i < mCurrencyContainer.getmCurrenciesList().size(); i++) {
            charCodeList.add(mCurrencyContainer.getmCurrenciesList().get(i).getCharacterCode());
        }

        initialCurrencySpinner = (Spinner) findViewById(R.id.initial_currency_spinner);
        resultingCurrencySpinner = (Spinner) findViewById(R.id.resulting_currency_spinner);

        initialCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mInitialCurrencyTextView.setText(mCurrencyContainer.getmCurrenciesList().get(
                        position).getValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        resultingCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mResultingCurrencyTextView.setText(mCurrencyContainer.getmCurrenciesList().get(
                        position).getValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        currenciesAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_item, charCodeList);
        currenciesAdapter.setDropDownViewResource(R.layout.spinner_item);
        initialCurrencySpinner.setAdapter(currenciesAdapter);
        resultingCurrencySpinner.setAdapter(currenciesAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
