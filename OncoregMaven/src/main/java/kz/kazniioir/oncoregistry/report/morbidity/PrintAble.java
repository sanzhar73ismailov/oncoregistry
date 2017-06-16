package kz.kazniioir.oncoregistry.report.morbidity;

public interface PrintAble {

    void printTableToConsole();

    void printTableToWordFile();

    void printTableToExcelFile();
}
