package kz.kazniioir.oncoregistry.main;

import kz.kazniioir.oncoregistry.form1000.OutputFile07Form;
import kz.kazniioir.oncoregistry.form1000.ParseForm07;
import kz.kazniioir.oncoregistry.form1000.PrMorb1000;
import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import kz.kazniioir.oncoregistry.CopySheet;
import kz.kazniioir.oncoregistry.FileSystem;
import kz.kazniioir.oncoregistry.OncoException;
import kz.kazniioir.oncoregistry.OutputFile;
import kz.kazniioir.oncoregistry.Parse;
import kz.kazniioir.oncoregistry.RowOnco;
import kz.kazniioir.oncoregistry.Service;
import kz.kazniioir.oncoregistry.ServiceMorbidity1000;

public class Test1000 {

    //int year = 2013;
    static Logger LOGGER = Logger.getLogger(Test1000.class.getName());
    final static int NOT_WORKING_MAIN = 0;
    final static int COPY_TO_NOT_PROT_FILES = 0;
    final static int ONLY_ONE_FILE = 0;
    final static int REGION = 1;
    final static int ONLY_ONE_YEAR = 0;
    final static int YEAR = 2006;
    final static int PARSE_FILES = 1;
    final static int VALIDATE_PARSE_FILES = 1;
    final static int NORMALIZE_LOCALIZATIONS = 1;
    final static int WRITE_TO_SIMPLE_FILES = 1;
    final static int WRITE_TO_OLAP_FILES = 1;
    final static int WRITE_TO_SIMPLE_DB = 1;
    final static int WRITE_TO_OLAP_DB = 1;
    final static String generalPath = "S:\\GDnew\\DOCS\\КазНИИОиР_СекторБиостат\\Отдел_попул_регистра\\База\\7 форма\\";

    public static void test1000() throws OncoException, IOException {
        for (int myYear = 2004; myYear < 2017; myYear++) {
            if (ONLY_ONE_YEAR == 1 && myYear != YEAR) {
                continue;
            }
            workingWith7Form(myYear);
        }
    }

    public static void workingWith7Form(int year) throws OncoException {


        if (NOT_WORKING_MAIN == 1) {
            javax.swing.JOptionPane.showMessageDialog(null, "public static void main не работает");
            return;
        }

        try {
            Handler fileHandler = new FileHandler(generalPath + year + "\\log.txt", false);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            // copy files
            if (COPY_TO_NOT_PROT_FILES == 1) {
                for (int region = 1; region < 18; region++) {
                    Service service = new ServiceMorbidity1000(year, region, generalPath, "07");
                    service.setLastRow(75);
                    service.setSheetNameOfSourceFile("форма");
                    CopySheet copySheet = new CopySheet(service.getSourceFile());
                    copySheet.copySheet();
                }
            }


            for (int region = 1; region < 18; region++) {

                //for (int region = 1; region < 2; region++) {
                if (ONLY_ONE_FILE == 1 && region != REGION) {
                    continue;
                }
                ServiceMorbidity1000 service = new ServiceMorbidity1000(year, region, generalPath, "07");
                service.setLastRow(75);
                service.setSheetNameOfSourceFile("форма");


                FileSystem fileSystem = new FileSystem(service);
                OutputFile outputFile = new OutputFile07Form(service.getTargetFile());
                OutputFile outputFileOlap = new OutputFile07Form(service.getTargetFileOlap());

                if (PARSE_FILES != 1) {
                    return;
                }
                Parse parseForm07 = new ParseForm07(year, region, fileSystem, new PrMorb1000(false));
                parseForm07.parseFile();

                try {
                    if (VALIDATE_PARSE_FILES == 1) {
                        // Thread.sleep(5000);
                        parseForm07.validateParse();
                    }

                    

                    if (NORMALIZE_LOCALIZATIONS == 1) {
                        parseForm07.normalizeLocalizations();
                    }
                    
//                    final List<RowOnco> listOfParsedRows = parseForm07.getListOfParsedRows();
//                    for (int i = 0; i < listOfParsedRows.size(); i++) {
//                        RowOnco row = listOfParsedRows.get(i);
//                        System.out.println(i +". row = " + row.toString());
//                        
//                    }

                    if (WRITE_TO_SIMPLE_FILES == 1) {
                        parseForm07.writeOutputFile(outputFile);
                    }
                    if (WRITE_TO_OLAP_FILES == 1) {
                        parseForm07.writeOutputFileOlap(outputFileOlap);
                    }



                    if (WRITE_TO_SIMPLE_DB == 1) {
                        parseForm07.writeOutputDBSimpleTable();
                    }

                    if (WRITE_TO_OLAP_DB == 1) {
                        parseForm07.writeOutputDBOlap();
                    }
                } catch (Exception ex) {
                    LOGGER.severe("\r\n\t" + parseForm07.getFileName() + ":\r\n" + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
