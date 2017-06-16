package kz.kazniioir.oncoregistry.formPopul;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class FabricaParsePopul {

    public static ParsePopulAbstr getParsePopulation(int year, File file) {
        ParsePopulAbstr toReturn = null;
        try {
            if (year >= 2004 && year <= 2007) {
                toReturn = new ParsePopulManySheets(file, year);

            } else {
                toReturn = new ParsePopulAllInOneSheet(file, year);
            }
        } catch (IOException ex) {
            Logger.getLogger(FabricaParsePopul.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(FabricaParsePopul.class.getName()).log(Level.SEVERE, null, ex);
        }
        return toReturn;
    }
}
