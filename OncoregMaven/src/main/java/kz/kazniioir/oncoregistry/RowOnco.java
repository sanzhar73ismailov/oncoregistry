package kz.kazniioir.oncoregistry;

public interface RowOnco {

    int getNumberOfColumns();

    Object getValueByIndex(int i) throws OncoException;
}
