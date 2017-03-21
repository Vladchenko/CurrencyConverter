package com.example.vladislav.currencyconverter.datasource;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by vladislav on 20.03.17.
 */

@Root(name="Valute")
public class CurrencyBean {

    /**
     * Temporary message to know the contents of an currency entry read from a web.
     *
     * <Valute ID="R01589">
     *      <NumCode>960</NumCode>
     *      <CharCode>XDR</CharCode>
     *      <Nominal>1</Nominal>
     *      <Name>��� (����������� ����� �������������)</Name>
     *      <Value>78,5631</Value>
     * </Valute>
     */

    @Attribute(name="ID")
    private String ID;              // say, "R01589"
    @Element
    private int numericCode;        // say, 960
    @Element
    private String characterCode;   // say, XDR
    @Element
    private String name;            // no idea what's the name of this currency.
    @Element
    private double value;           // say, 78,5631

    public CurrencyBean() {

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(int numericCode) {
        this.numericCode = numericCode;
    }

    public String getCharacterCode() {
        return characterCode;
    }

    public void setCharacterCode(String characterCode) {
        this.characterCode = characterCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
