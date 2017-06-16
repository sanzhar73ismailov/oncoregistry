package kz.kazniioir.oncoregistry;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface OutputFile {

    HSSFWorkbook getWorkBook();

    void write() throws FileNotFoundException, IOException;
    
    String getFileName();
}
