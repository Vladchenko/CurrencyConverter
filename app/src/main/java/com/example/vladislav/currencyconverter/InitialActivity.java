package com.example.vladislav.currencyconverter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vladislav.currencyconverter.datasource.CurrenciesOperating;
import com.example.vladislav.currencyconverter.logic.CurrencyConverter;

import java.util.ArrayList;
import java.util.List;

import static android.content.IntentFilter.SYSTEM_HIGH_PRIORITY;

public class InitialActivity extends AppCompatActivity {

    private CurrenciesOperating mCurrencyOperating;
    private Spinner initialCurrencySpinner;     // Spinner for a initial currency (to convert from);
    private Spinner resultingCurrencySpinner;   // Spinner for a resulting currency  (to convert to);
    private EditText mInitialCurrencyEditText;
    private EditText mResultingCurrencyEditText;
    private TextView mInitialCurrencyTextView;
    private TextView mResultingCurrencyTextView;
    private BroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        Consts.setmCurrenciesFile(getBaseContext().getFilesDir().getPath().toString()
                + "/" + Consts.getmCurrenciesFileName());

//        if (!Utils.isURLValid(Consts.getmUrl())) {
//            Log.e("InitialActivity","URL is incorrect!");
//            return;
//        }
        if (!Utils.isFilePathValid(Consts.getmCurrenciesFile())) {
            Log.e("InitialActivity", "Filepath is incorrect!, program terminated.");
            return;
        }

        // Starting a currency downloading service.
        Intent intent = new Intent(InitialActivity.this, NetworkService.class);
        startService(intent);

        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.Theme_MyDialog);
        progressDialog.setTitle("Downloading currencies");
        progressDialog.setMessage("Please wait for a currencies quotations to download from web.");
        progressDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        progressDialog.show();
//        Utils.showToast(getApplicationContext(),
//                "Currencies are downloading from a web.");

        mCurrencyOperating = new CurrenciesOperating();

        // Checking if there was an exception message broadcasted.
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String exceptionString = intent.getExtras().getString(Consts.REPLY);
                if ("Fail".equals(exceptionString)) {
                    try {
                        mCurrencyOperating.setmCurrencyContainer((new XMLParser()).parse());
                        progressDialog.cancel();
                        Utils.showToast(getApplicationContext(),
                                "Currencies web downloading failed, but a cache file downloading succeeded.");
                    } catch (Exception ex) {
                        progressDialog.setMessage("Currencies web downloading failed, cache file is " +
                                "also corrupt - restart the app. Make sure the internet connection " +
                                "is on the next time!");
                    }
                } else {
                    progressDialog.cancel();
                    // Success in loading from a web.
                    try {
                        mCurrencyOperating.setmCurrencyContainer((new XMLParser()).parse());
                        // Success in loading from a file.
                        Utils.showToast(getApplicationContext(),
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

        IntentFilter mIntentFilter = new IntentFilter(Consts.REPLY);
        mIntentFilter.setPriority(SYSTEM_HIGH_PRIORITY);
        registerReceiver(mBroadcastReceiver, mIntentFilter);

//        try {
//            mCurrencyOperating.setmCurrencyContainer((new XMLParser()).parse());
//        } catch (Exception ex) {
//        }


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
                    Utils.showToast(getApplicationContext(), "Currencies match, choose a different one");
                } else {
                    if (!mInitialCurrencyEditText.getText().toString().equals("")) {
                        try {
                            String operationResult = CurrencyConverter.convertCurrency(
                                    Double.parseDouble(mInitialCurrencyEditText.getText().toString()),
                                    Double.parseDouble(mCurrencyOperating.getmCurrencyContainer().getmCurrenciesList().get(
                                            initialCurrencySpinner.getSelectedItemPosition()).getValue()),
                                    Double.parseDouble(mCurrencyOperating.getmCurrencyContainer().getmCurrenciesList().get(
                                            resultingCurrencySpinner.getSelectedItemPosition()).getValue()));
                            mResultingCurrencyEditText.setText(operationResult);
                        } catch (ArithmeticException | NumberFormatException ex) {
                            mResultingCurrencyEditText.setText("n/a");
                            Utils.showToast(getApplicationContext(), "Amount is wrong.");
                        }
                    } else {
                        Utils.showToast(getApplicationContext(), "Currency amount is absent");
                    }
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void submitCurrenciesCharCodesToSpinners() {

//        mCurrencyOperating = new CurrenciesOperating();
        ArrayAdapter<String> currenciesAdapter;
        List<String> charCodeList = new ArrayList<>();

        for (int i = 0; i < mCurrencyOperating.getmCurrencyContainer().getmCurrenciesList().size(); i++) {
            charCodeList.add(mCurrencyOperating.getmCurrencyContainer().getmCurrenciesList().get(i).getCharacterCode());
        }

        initialCurrencySpinner = (Spinner) findViewById(R.id.initial_currency_spinner);
        resultingCurrencySpinner = (Spinner) findViewById(R.id.resulting_currency_spinner);

        initialCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mInitialCurrencyTextView.setText(mCurrencyOperating.getmCurrencyContainer().getmCurrenciesList().get(
                        position).getValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        resultingCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mResultingCurrencyTextView.setText(mCurrencyOperating.getmCurrencyContainer().getmCurrenciesList().get(
                        position).getValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
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
