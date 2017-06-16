package kz.kazniioir.oncoregistry.main;

import java.io.File;
import kz.kazniioir.oncoregistry.OncoException;
import kz.kazniioir.oncoregistry.Parse;
import kz.kazniioir.oncoregistry.form2200.Parse2200;

public class Test2200 {
  public static void test2200() throws OncoException {

        String fileName = "S:\\DOCS\\КазНИИОиР_СекторБиостат\\Отдел_попул_регистра\\База\\2200\\2200_для_парсинга.xls";
        File file = new File(fileName);
        Parse parse = new Parse2200(file);
        parse.parseFile();
        parse.normalizeLocalizations();
        parse.validateParse();
        parse.writeOutputDBSimpleTable();

    }
}
