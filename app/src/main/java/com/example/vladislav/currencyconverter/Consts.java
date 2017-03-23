package com.example.vladislav.currencyconverter;

/**
 * Created by vladislav on 20.03.17.
 */
public class Consts {

    public static final String EXCEPTION = "exception";

    private static Consts ourInstance = new Consts();

    public static Consts getInstance() {
        return ourInstance;
    }

    private Consts() {
    }
}
