package kz.kazniioir.oncoregistry;

import java.io.File;

public class ServiceMortality extends Service {

    private int region;
    protected int lastRow;
    protected String sheetNameOfSourceFile;
    private String baseFileName;
    private File sourceFileForParsing;// = new File(genFolder + baseFileName + ".xls"); //1
    private File targetFile; //= new File(genFolder + "out\\" + baseFileName + "_out" + ".xls");
    private File targetFileOlap; //= new File(genFolder + "out\\OLAP" + baseFileName + "_out" + ".xls");
    private String suffixForm;

    public ServiceMortality(int year, int region, String baseFolder) {
        this.year = year;
        this.region = region;
        this.baseFolder = baseFolder;
        this.genFolder = baseFolder;
        this.outFolder = baseFolder + "\\" + "out";
        this.sourceFileForParsing = new File(this.genFolder + "\\" + year + ".xls");

    }

    @Override
    public File getSourceFileForParsing() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSheetNameOfSourceFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getLastRow() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSheetNameOfSourceFile(String shName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLastRow(int number) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public File getSourceFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
