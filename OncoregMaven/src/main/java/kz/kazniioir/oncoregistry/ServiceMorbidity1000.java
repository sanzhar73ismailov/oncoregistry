package kz.kazniioir.oncoregistry;

import java.io.File;

public class ServiceMorbidity1000 extends Service {

    private int region;
    protected int lastRow;
    protected String sheetNameOfSourceFile;
    private String baseFileName;
    private File sourceFileForParsing;// = new File(genFolder + baseFileName + ".xls"); //1
    private File targetFile; //= new File(genFolder + "out\\" + baseFileName + "_out" + ".xls");
    private File targetFileOlap; //= new File(genFolder + "out\\OLAP" + baseFileName + "_out" + ".xls");
    private String suffixForm;

    public ServiceMorbidity1000(int year, int region, String baseFolder, String suffixForm) throws OncoException {
        this.suffixForm = suffixForm;
        this.year = year;
        this.region = region;
        this.baseFolder = baseFolder;
        this.genFolder = baseFolder + year + "\\";
        this.outFolder = genFolder + "out\\";
        File outFolderCatalog = new File(outFolder);
        outFolderCatalog.mkdir();
        baseFileName = getFileName();

        sourceFile = new File(genFolder + baseFileName + ".xls"); //1
        String nameOfFileForParsing = sourceFile.getParent() + "\\" + sourceFile.getName().substring(0, (sourceFile.getName().length() - 4)) + "_np.xls";
        //String nameOfFileForParsing = sourceFile.getParent() + "\\" + sourceFile.getName().substring(0, (sourceFile.getName().length() - 4)) + ".xls";
        sourceFileForParsing = new File(nameOfFileForParsing);
        targetFile = new File(genFolder + "out\\" + baseFileName + "_out" + ".xls");
        targetFileOlap = new File(genFolder + "out\\OLAP" + baseFileName + "_out" + ".xls");

    }

    @Override
    public File getSourceFileForParsing() {
        return sourceFileForParsing;
    }

    @Override
    public int getLastRow() {
        return lastRow;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }

    @Override
    public String getSheetNameOfSourceFile() {
        return sheetNameOfSourceFile;
    }

    public void setSheetNameOfSourceFile(String sheetNameOfSourceFile) {
        this.sheetNameOfSourceFile = sheetNameOfSourceFile;
    }

    public String getBaseFileName() {
        return baseFileName;
    }

    public File getSourceFile() {
        return sourceFile;
    }

    public File getTargetFile() {
        return targetFile;
    }

    public File getTargetFileOlap() {
        return targetFileOlap;
    }

    String getGenFolder() {
        return genFolder;
    }

    String getOutFolder() {
        return outFolder;
    }

    private String getFileName() throws OncoException {
        String value = "";

        switch (region) {
            case 1:
                value = "01_астана_";
                break;
            case 2:
                value = "02_алматы_";
                break;
            case 3:
                value = "03_акмола_";
                break;
            case 4:
                value = "04_актюбинск_";
                break;
            case 5:
                value = "05_алматин_";
                break;
            case 6:
                value = "06_атырау_";
                break;
            case 7:
                value = "07_вко_";
                break;
            case 8:
                value = "08_жамбыл_";
                break;
            case 9:
                value = "09_зко_";
                break;
            case 10:
                value = "10_караганда_";
                break;
            case 11:
                value = "11_костанай_";
                break;
            case 12:
                value = "12_кызылорда_";
                break;
            case 13:
                value = "13_мангистау_";
                break;
            case 14:
                value = "14_павлодар_";
                break;
            case 15:
                value = "15_ско_";
                break;
            case 16:
                value = "16_юко_";
                break;
            case 17:
                value = "17_РК_";
                break;
            default:
                throw new OncoException("No such region code: " + region);
        }

        return value + suffixForm + "_" + String.valueOf(year).substring(2);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }
}
