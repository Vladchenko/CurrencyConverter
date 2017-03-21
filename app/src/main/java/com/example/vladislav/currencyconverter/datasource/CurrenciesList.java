package com.example.vladislav.currencyconverter.datasource;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladislav on 20.03.17.
 */

@Root(name="ValCurs")
public class CurrenciesList {

    @Attribute(name="name", required=false)
    String name;

    @Attribute(name="Date", required=false)
    String date;

    @Element(name="Valute", required=false)
    Valute valute;

    public String getName() {return this.name;}
    public void setName(String value) {this.name = value;}

    public String getDate() {return this.date;}
    public void setDate(String value) {this.date = value;}

    public Valute getValute() {return this.valute;}
    public void setValute(Valute value) {this.valute = value;}

    public static class Valute {

        @Element(name="CharCode", required=false)
        String charCode;

        @Element(name="Value", required=false)
        String value;

        @Attribute(name="ID", required=false)
        String iD;

        @Element(name="Nominal", required=false)
        String nominal;

        @Element(name="NumCode", required=false)
        String numCode;

        @Element(name="Name", required=false)
        String name;

        public String getCharCode() {return this.charCode;}
        public void setCharCode(String value) {this.charCode = value;}

        public String getValue() {return this.value;}
        public void setValue(String value) {this.value = value;}

        public String getID() {return this.iD;}
        public void setID(String value) {this.iD = value;}

        public String getNominal() {return this.nominal;}
        public void setNominal(String value) {this.nominal = value;}

        public String getNumCode() {return this.numCode;}
        public void setNumCode(String value) {this.numCode = value;}

        public String getName() {return this.name;}
        public void setName(String value) {this.name = value;}

    }

}
