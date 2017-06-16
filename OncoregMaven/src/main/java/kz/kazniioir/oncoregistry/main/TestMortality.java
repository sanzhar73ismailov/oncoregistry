package kz.kazniioir.oncoregistry.main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kz.kazniioir.oncoregistry.OncoException;
import kz.kazniioir.oncoregistry.Parse;
import kz.kazniioir.oncoregistry.formAgencyStat.ParseMortality;
import static kz.kazniioir.oncoregistry.main.TestPopul.insertRows;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class TestMortality {

    public static void go() {
        for (int year = 2004; year < 2014; year++) {
            // for (int year = 2013; year < 2014; year++) {
            if(year != 2013){
               // continue;
            }
            insertRows(year);
        }
    }

    public static void insertRows(int year) {
        try {
            String generalPath = "S:\\DOCS\\КазНИИОиР_СекторБиостат\\Отдел_попул_регистра\\База\\умершие по Агентству статРК";
            // int year = 2004;
            File fileToParse = new File(generalPath + "\\" + year + ".xls");

            ParseMortality parser = new ParseMortality(fileToParse, year);
            parser.parseFile();
            parser.validateParse();
            parser.normalizeLocalizations();
           // parser.writeOutputDBSimpleTable();
            parser.writeOutputDBOlap();

        } catch (IOException ex) {
            Logger.getLogger(TestMortality.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(TestMortality.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TestMortality.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
