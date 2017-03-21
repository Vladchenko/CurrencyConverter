package com.example.vladislav.currencyconverter.datasource;

import com.example.vladislav.currencyconverter.XMLParser;
import com.example.vladislav.currencyconverter.beans.CurrenciesContainer;
import com.example.vladislav.currencyconverter.beans.CurrencyBean;

/**
 * Created by vladislav on 20.03.17.
 */

public class CurrencyOperating {

    private CurrenciesContainer mCurrencyContainer = new CurrenciesContainer();
    XMLParser mXmlParser = new XMLParser();

    public CurrencyOperating() {
        populateContainerByDefault();
    }

    // This is a method that populates currency list with dummy(fake) beans.
    private void populateContainerByDefault() {

        CurrencyBean currencyBean = new CurrencyBean();
        currencyBean.setCharacterCode("USD");
        mCurrencyContainer.getmCurrenciesList().add(currencyBean);

        currencyBean = new CurrencyBean();
        currencyBean.setCharacterCode("RUB");
        mCurrencyContainer.getmCurrenciesList().add(currencyBean);

        currencyBean = new CurrencyBean();
        currencyBean.setCharacterCode("XDR");
        mCurrencyContainer.getmCurrenciesList().add(currencyBean);

        currencyBean = new CurrencyBean();
        currencyBean.setCharacterCode("CHK");
        mCurrencyContainer.getmCurrenciesList().add(currencyBean);

    }

    public CurrenciesContainer getmCurrencyContainer() {
        return mCurrencyContainer;
    }

    public void setmCurrencyContainer(CurrenciesContainer mCurrencyContainer) {
        this.mCurrencyContainer = mCurrencyContainer;
    }
}
