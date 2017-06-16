package kz.kazniioir.oncoregistry;

import java.io.File;

public abstract class Service {

    protected int year;
    protected String baseFolder;
    protected String genFolder;
    protected String outFolder;
    protected File sourceFile;// = new File(genFolder + baseFileName + ".xls"); //1

    public abstract File getSourceFileForParsing();

    public abstract String getSheetNameOfSourceFile();

    public abstract int getLastRow();

    public abstract void setSheetNameOfSourceFile(String shName);

    public abstract void setLastRow(int number);
    
    public abstract File  getSourceFile();
}
