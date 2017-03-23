package com.example.vladislav.currencyconverter;

import com.example.vladislav.currencyconverter.datasource.ValCurs;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by vladislav on 20.03.17.
 */

public class XMLParser {

    public void parse() {

        FileReader fileReader = null;
        Serializer serializer = new Persister();
        File source = new File(Consts.getmCurrenciesFile());

//        System.out.println("\n\n" + Consts.getmCurrenciesFile());
//        System.out.println(source.length() + "\n\n");  //0 why?

        try {
            fileReader = new FileReader(source);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String sCurrentLine;
        int c;
        try {
            while ((c = fileReader.read()) != -1) {
                System.out.println("File data read: " + c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ValCurs example = serializer.read(ValCurs.class, source);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
