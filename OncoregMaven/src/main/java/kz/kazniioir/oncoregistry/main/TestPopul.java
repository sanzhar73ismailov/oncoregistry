package kz.kazniioir.oncoregistry.main;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import kz.kazniioir.oncoregistry.OncoException;
import kz.kazniioir.oncoregistry.Parse;
import kz.kazniioir.oncoregistry.formPopul.FabricaParsePopul;

public class TestPopul {
    public static void go(){
        //for (int year = 2004; year < 2017; year++) {
        for (int year = 2014; year < 2017; year++) {
            insertRows(year);
        }
    }
    public static void insertRows(int year) {
        String generalPath = "S:\\GDnew\\DOCS\\КазНИИОиР_СекторБиостат\\Отдел_попул_регистра\\База\\Среднегод.числен.населения";
       // int year = 2004;
        File fileToParse = new File(generalPath + "\\" + year + ".xls");
        Parse parser = FabricaParsePopul.getParsePopulation(year, fileToParse);
        parser.parseFile();
        try {
//            for (Object object : parser.getListOfParsedRows()) {
//                System.out.println(object);
//            }
            parser.validateParse();
            parser.writeOutputDBSimpleTable();
            parser.writeOutputDBOlap();
        } catch (Exception ex) {
            Logger.getLogger(TestPopul.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
