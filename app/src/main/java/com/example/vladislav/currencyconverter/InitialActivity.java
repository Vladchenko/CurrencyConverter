package com.example.vladislav.currencyconverter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
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
import com.example.vladislav.currencyconverter.beans.CurrencyBean;
import com.example.vladislav.currencyconverter.datasource.CurrenciesHandlingService;
import com.example.vladislav.currencyconverter.logic.CurrencyConverter;

import java.util.ArrayList;
import java.util.List;

import static android.content.IntentFilter.SYSTEM_HIGH_PRIORITY;

// TODO
// 0. Make a look of currency amount edit texts to have same width.
// 1. Put all the resources to resources folder
// 2. Make a landscape look the same as portrait.
// 3. Make a methods smaller


/**
 * Holds the presentation and logic layer for the app.
 */
public class InitialActivity extends AppCompatActivity {

    // Position of a USD currency in a currencies spinner's list
    private final int USD_POSITION = 10;
    // Position of a RUB currency in a currencies spinner's list
    private final int RUB_POSITION = 0;

    private CurrenciesContainer mCurrencyContainer;
    private Spinner mInitialCurrencySpinner;        // Spinner for a initial currency (to convert from);
    private Spinner mResultingCurrencySpinner;      // Spinner for a resulting currency  (to convert to);
    private EditText mInitialCurrencyEditText;      // Edit text for a currency to convert from.
    private EditText mResultingCurrencyEditText;    // Edit text for a currency to convert to.
    private TextView mInitialCurrencyTextView;      // Quotation for a currency to convert from.
    private TextView mResultingCurrencyTextView;    // Quotation for a currency to convert to.
    private BroadcastReceiver mBroadcastReceiver;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        resources = getResources();
        setContentView(R.layout.initial_activity);
        Consts.setCurrenciesFile(getBaseContext().getFilesDir().getPath().toString()
                + "/" + Consts.getCurrenciesFileName());

        if (!CommonUtils.isURLValid(Consts.getUrl())) {
            Log.e(getClass().getCanonicalName(), resources.getString(R.string.wrong_URL));
            return;
        }
        if (!CommonUtils.isFilePathValid(Consts.getCurrenciesFile())) {
            Log.e(getClass().getCanonicalName(), resources.getString(R.string.wrong_file));
            return;
        }

        // Starting a currency downloading service.
        Intent intent = new Intent(InitialActivity.this, CurrenciesHandlingService.class);
        startService(intent);

        // Showing a Progress Dialog, while a downloading is performed.
        final ProgressDialog progressDialog = prepareProgressDialog();
        progressDialog.show();

        mCurrencyContainer = new CurrenciesContainer();

        // Checking if there was an exception message broadcasted.
        mBroadcastReceiver = prepareBroadcastReceiver(progressDialog);

        IntentFilter mIntentFilter = new IntentFilter(Consts.SERVICE_REPLY);
        mIntentFilter.setPriority(SYSTEM_HIGH_PRIORITY);
        registerReceiver(mBroadcastReceiver, mIntentFilter);

        prepareViews();
        initializeConvertButton();
        initializerSwapCurrenciesButton();


    }

    private ProgressDialog prepareProgressDialog() {
        ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme);
        progressDialog.setTitle(resources.getString(R.string.progress_dialog_title_text));
        progressDialog.setMessage(resources.getString(R.string.progress_dialog_message_text));
        progressDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        return progressDialog;
    }

    private BroadcastReceiver prepareBroadcastReceiver(final ProgressDialog progressDialog) {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String exceptionString = intent.getExtras().getString(Consts.SERVICE_REPLY);
                if (Consts.SERVICE_FAIL.equals(exceptionString)) {
                    try {
                        mCurrencyContainer = new CurrenciesFileDeserializer().parse();
                        progressDialog.cancel();
                        CommonUtils.showToast(getApplicationContext(),
                                resources.getString(R.string.web_downloading_failed));
                    } catch (Exception ex) {
                        progressDialog.setMessage(resources.getString(R.string.web_and_file_downloading_failed));
                    }
                }
                if (Consts.SERVICE_SUCCESS.equals(exceptionString)) {
                    progressDialog.cancel();
                    // Success in loading from a web.
                    try {
                        mCurrencyContainer = new CurrenciesFileDeserializer().parse();
                        // Success in loading from a file.
                        CommonUtils.showToast(getApplicationContext(),
                                resources.getString(R.string.web_downloading_success));
                    } catch (Exception ex) {
                        progressDialog.setMessage(resources.getString(R.string.web_and_file_downloading_failed));
                    }
                }
                submitCurrenciesCharCodesToSpinners();
                setInitialCurrenciesInSpinners();
            }
        };
    }

    private void initializeConvertButton() {
        Button convertButton = (Button) findViewById(R.id.convert_button);
        convertButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInitialCurrencySpinner.getSelectedItem().toString().equals(
                        mResultingCurrencySpinner.getSelectedItem().toString())) {
                    // Saying that currencies match
                    resources.getString(R.string.same_currencies);
                } else {
                    // Perform calculation only if an amount to be converted is present in a respective text_edit.
                    if (!mInitialCurrencyEditText.getText().toString().equals("")) {
                        try {
                            String operationResult = CurrencyConverter.convertCurrency(
                                    Double.parseDouble(mInitialCurrencyEditText.getText().toString()),
                                    Double.parseDouble(mCurrencyContainer.getmCurrenciesList().get(
                                            mInitialCurrencySpinner.getSelectedItemPosition()).getValue()),
                                    Double.parseDouble(mCurrencyContainer.getmCurrenciesList().get(
                                            mResultingCurrencySpinner.getSelectedItemPosition()).getValue()));
                            mResultingCurrencyEditText.setText(operationResult);
                        } catch (ArithmeticException | NumberFormatException ex) {
                            mResultingCurrencyEditText.setText("n/a");
                            CommonUtils.showToast(getApplicationContext(), resources.getString(
                                    R.string.currency_is_wrong));
                        }
                    } else {
                        CommonUtils.showToast(getApplicationContext(),
                                resources.getString(R.string.currency_amount_absent));
                    }
                }
            }
        });
    }

    /**
     * Since the list of a currencies loaded from inet does not have a RUB currency, this method
     * adds it to this list.
     */
    private CurrencyBean collectRUBCurrency() {
        CurrencyBean currencyBean = new CurrencyBean();
        currencyBean.setCharacterCode("RUB");
        currencyBean.setNumericCode(643);
        currencyBean.setValue("1");
        return currencyBean;
    }

    private void submitCurrenciesCharCodesToSpinners() {

        ArrayAdapter<String> currenciesAdapter;
        List<String> charCodeList = new ArrayList<>();

        CurrencyBean rubCurrencyBean = collectRUBCurrency();
        mCurrencyContainer.getmCurrenciesList().set(0, rubCurrencyBean);
        mInitialCurrencyTextView.setText(rubCurrencyBean.getCharacterCode());

        for (int i = 0; i < mCurrencyContainer.getmCurrenciesList().size(); i++) {
            charCodeList.add(mCurrencyContainer.getmCurrenciesList().get(i).getCharacterCode());
        }

        mInitialCurrencySpinner = (Spinner) findViewById(R.id.initial_currency_spinner);
        mResultingCurrencySpinner = (Spinner) findViewById(R.id.resulting_currency_spinner);

        mInitialCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mInitialCurrencyTextView.setText(mCurrencyContainer.getmCurrenciesList().get(
                        position).getValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mResultingCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mResultingCurrencyTextView.setText(mCurrencyContainer.getmCurrenciesList().get(
                        position).getValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        currenciesAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.item_spinner, charCodeList);
        currenciesAdapter.setDropDownViewResource(R.layout.item_spinner);
        mInitialCurrencySpinner.setAdapter(currenciesAdapter);
        mResultingCurrencySpinner.setAdapter(currenciesAdapter);
    }

    /**
     * Setting a currencies in a spinners when an application starts.
     */
    private void setInitialCurrenciesInSpinners() {
        mInitialCurrencySpinner.setSelection(USD_POSITION);
        mResultingCurrencySpinner.setSelection(RUB_POSITION);
    }

    private void prepareViews() {
        mInitialCurrencyEditText = (EditText) findViewById(R.id.initial_currency_edit_text);
        mResultingCurrencyEditText = (EditText) findViewById(R.id.resulting_currency_edit_text);
        mInitialCurrencyTextView = (TextView) findViewById(R.id.initial_currency_quotation_text_view);
        mResultingCurrencyTextView = (TextView) findViewById(R.id.resulting_currency_quotation_text_view);
    }

    private void initializerSwapCurrenciesButton() {
        Button swapButton = (Button) findViewById(R.id.swap_button);
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int swapperPosition;
                String swapperText;
                swapperPosition = mInitialCurrencySpinner.getSelectedItemPosition();
                mInitialCurrencySpinner.setSelection(mResultingCurrencySpinner.getSelectedItemPosition());
                mResultingCurrencySpinner.setSelection(swapperPosition);
                swapperText = mInitialCurrencyTextView.getText().toString();
                mInitialCurrencyTextView.setText(mResultingCurrencyTextView.getText());
                mResultingCurrencyTextView.setText(swapperText);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
