package com.example.vladislav.currencyconverter.beans;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Contents for the root entry of a currency XML file.
 */

@Root(name="ValCurs")
public class CurrenciesContainer {

    @ElementList(entry = "Valute", inline=true)
    private List<CurrencyBean> mCurrenciesList = new ArrayList<>();

    @Attribute(name = "name")
    private String name;

    @Attribute(name = "Date")
    private String date;

    public CurrenciesContainer() {}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getProperties() {
        return getmCurrenciesList();
    }

    public List<CurrencyBean> getmCurrenciesList() {
        return mCurrenciesList;
    }

    public void setmCurrenciesList(List<CurrencyBean> mCurrenciesList) {
        this.mCurrenciesList = mCurrenciesList;
    }

}
