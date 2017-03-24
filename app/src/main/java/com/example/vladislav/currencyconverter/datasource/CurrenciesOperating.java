package com.example.vladislav.currencyconverter.datasource;

import com.example.vladislav.currencyconverter.XMLParser;
import com.example.vladislav.currencyconverter.beans.CurrenciesContainer;
import com.example.vladislav.currencyconverter.beans.CurrencyBean;

/**
 * Created by vladislav on 20.03.17.
 */

public class CurrenciesOperating {

    private CurrenciesContainer mCurrencyContainer = new CurrenciesContainer();
//    private XMLParser mXmlParser = new XMLParser(mCurrencyContainer);

    public CurrenciesOperating() {
        populateContainerByDefault();
    }

    // This is a method that populates currency list with dummy(fake) beans.
    private void populateContainerByDefault() {

        CurrencyBean currencyBean = new CurrencyBean();
        currencyBean.setCharacterCode("USD");
        currencyBean.setValue("56.4204");
        mCurrencyContainer.getmCurrenciesList().add(currencyBean);

        currencyBean = new CurrencyBean();
        currencyBean.setCharacterCode("RUB");
        currencyBean.setValue("1");
        mCurrencyContainer.getmCurrenciesList().add(currencyBean);

        currencyBean = new CurrencyBean();
        currencyBean.setCharacterCode("XDR");
        currencyBean.setValue("32.6018");
        mCurrencyContainer.getmCurrenciesList().add(currencyBean);

        currencyBean = new CurrencyBean();
        currencyBean.setCharacterCode("CHK");
        currencyBean.setValue("16.2729");
        mCurrencyContainer.getmCurrenciesList().add(currencyBean);

    }

    public CurrenciesContainer getmCurrencyContainer() {
        return mCurrencyContainer;
    }

    public void setmCurrencyContainer(CurrenciesContainer mCurrencyContainer) {
        this.mCurrencyContainer = mCurrencyContainer;
    }

}