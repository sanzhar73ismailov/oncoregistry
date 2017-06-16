package kz.kazniioir.oncoregistry;

import java.util.List;
import org.apache.poi.ss.usermodel.Row;

public interface Parse {

    void parseFile();
    
    void normalizeLocalizations() throws OncoException;

    void validateParse() throws OncoException;

    void writeOutputFile(OutputFile ouputFile);

    void writeOutputFileOlap(OutputFile ouputFile);
    
    void writeOutputDBOlap();
    
    void writeOutputDBSimpleTable();

    int getNumberOfRowsCouldBeParsed() throws OncoException;
    
    String getFileName();
    
    List<RowOnco> getListOfParsedRows();
    
    boolean isRowWithData(Row row);
}
