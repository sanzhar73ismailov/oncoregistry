package kz.kazniioir.oncoregistry.form1000;

import kz.kazniioir.oncoregistry.OutputFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class OutputFile07Form implements OutputFile {

    File file;
    HSSFWorkbook wb;

    public OutputFile07Form(File file) throws IOException {
        this.file = file;
        //POIFSFileSystem fileSystem = new POIFSFileSystem();
        wb = new HSSFWorkbook();
    }

    @Override
    public HSSFWorkbook getWorkBook() {
        return wb;
    }

    public void write() throws FileNotFoundException, IOException {
        wb.write(new FileOutputStream(file));
    }

    @Override
    public String getFileName() {
        return file.getName();
    }
}
