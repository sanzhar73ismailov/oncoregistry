package kz.kazniioir.oncoregistry;

import java.util.ArrayList;
import java.util.List;

public abstract class ParseDefault implements Parse {

    protected List<RowOnco> listOfParsedRows = new ArrayList<>();
    protected int year;
    //protected int region;

    public List<RowOnco> getListOfParsedRows() {
        return listOfParsedRows;
    }
     @Override
    public void normalizeLocalizations() throws OncoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
