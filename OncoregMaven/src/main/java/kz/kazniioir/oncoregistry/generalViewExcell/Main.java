package kz.kazniioir.oncoregistry.generalViewExcell;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kz.kazniioir.oncoregistry.FileSystem;
import kz.kazniioir.oncoregistry.OncoException;
import kz.kazniioir.oncoregistry.ServiceMorbidity1000;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Main {

    public static void main(String[] args) {
        //showRegistrySheet();
        //showSimpleSheet();
        showKRASSheet();
    }
    
    public static void showKRASSheet() {
       
        final String fileName = "S:\\DOCS\\КазНИИОиР_СекторБиостат\\Колор_Рак\\Список_колор_рак.xlsx";
        File file = new File(fileName);
        try {
            Workbook wb = WorkbookFactory.create(file);
             /*
            GeneralRawParseSheet parseSheetPatient = new GeneralRawParseSheet(wb.getSheet("patient"), 18, 156);
            InsertData insData1 = new InsertData(parseSheetPatient.getData());
            insData1.insertDataToPatientKras();
            */
           
            GeneralRawParseSheet parseSheetInvest = new GeneralRawParseSheet(wb.getSheet("invest"), 19, 156);
            InsertData insData2 = new InsertData(parseSheetInvest.getData());
            insData2.insertDataToInvestKras();
            
            //parseSheet.showFrame();

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void showSimpleSheet() {
        final String fileName = "S:\\DOCS\\КазНИИОиР_СекторБиостат\\Отдел_попул_регистра\\База\\7 форма\\2010\\Книга1.xlsx";
        File file = new File(fileName);
        try {
            Workbook wb = WorkbookFactory.create(file);
            GeneralRawParseSheet parseSheet = new GeneralRawParseSheet(wb.getSheet("Лист1"), 10, 20);
            parseSheet.showFrame();

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void showRegistrySheet() {
        try {
            int year = 2010;
            //int region = 17;
            final String generalPath = "S:\\DOCS\\КазНИИОиР_СекторБиостат\\Отдел_попул_регистра\\База\\7 форма\\";
            ServiceMorbidity1000 service = new ServiceMorbidity1000(year, 1, generalPath, "35");
            service.setSheetNameOfSourceFile("форма1");
            FileSystem fileSystem = new FileSystem(service);
            GeneralRawParseSheet parseSheet = new GeneralRawParseSheet(fileSystem.getSheet(), 25, 50);
            parseSheet.showFrame();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
