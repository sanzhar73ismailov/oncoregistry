package kz.kazniioir.oncoregistry.main;

import kz.kazniioir.oncoregistry.form2100.Parse2100;
import kz.kazniioir.oncoregistry.form2100.PrMorb2100;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kz.kazniioir.oncoregistry.CopySheet;
import kz.kazniioir.oncoregistry.FileSystem;
import kz.kazniioir.oncoregistry.OncoException;
import kz.kazniioir.oncoregistry.Parse;
import kz.kazniioir.oncoregistry.Service;
import kz.kazniioir.oncoregistry.ServiceMorbidity1000;
import kz.kazniioir.oncoregistry.ServiceMorbidity2100;
import kz.kazniioir.oncoregistry.generalViewExcell.GeneralRawParseSheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class Test2100 {

    public static final int ONLY_ONE_YEAR =0;
    public static final int YEAR = 2004;

    public static void test2100() {
        //int year = 2013;
        for (int year = 2004; year < 2014; year++) {
            if (ONLY_ONE_YEAR == 1) {
                if (year != YEAR) {
                    continue;
                }
            }
            work(year);
        }

    }

    public static void work(int year) {
        try {

            //int region = 17;
            final String generalPath = "S:\\DOCS\\КазНИИОиР_СекторБиостат\\Отдел_попул_регистра\\База\\7 форма\\";
            if (1 == 0) {
                for (int reg = 1; reg < 18; reg++) {
                    ServiceMorbidity1000 service = new ServiceMorbidity1000(year, reg, generalPath, "35");
                    service.setLastRow(75);
                    CopySheet copySheet = new CopySheet(service.getSourceFile());
                    copySheet.copySheet();
                }
            }
            if (1 == 0) {
                return;
            }

            for (int reg = 1; reg < 18; reg++) {
                if (1 == 0 && reg != 1) {
                    continue;
                }
                String suffix = "";
                if(year < 2011){
                    suffix = "35";
                }else{
                    suffix = "07";
                }
                Service service = new ServiceMorbidity2100(year, reg, generalPath, suffix);
                
                FileSystem fileSystem = new FileSystem(service);
                //rawParseSheet(fileSystem);
                Parse parse = new Parse2100(year, reg, fileSystem, new PrMorb2100());
                parse.parseFile();
                parse.validateParse();
                parse.normalizeLocalizations();
                parse.writeOutputDBSimpleTable();
                parse.writeOutputDBOlap();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static void rawParseSheet(FileSystem fileSystem) {
        try {
            GeneralRawParseSheet parseSheet = new GeneralRawParseSheet(fileSystem.getSheet(), 25, 50);
            parseSheet.showFrame();
        } catch (Exception ex) {
            Logger.getLogger(kz.kazniioir.oncoregistry.generalViewExcell.Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
