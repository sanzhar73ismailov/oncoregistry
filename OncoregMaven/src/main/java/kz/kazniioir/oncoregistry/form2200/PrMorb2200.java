package kz.kazniioir.oncoregistry.form2200;

import java.util.HashMap;
import java.util.Map;
import kz.kazniioir.oncoregistry.LocalizationMap;
import kz.kazniioir.oncoregistry.OncoException;
import kz.kazniioir.oncoregistry.RowOnco;

public class PrMorb2200 implements RowOnco, Cloneable, Comparable<PrMorb2200> {

    private int year;
    private int region;
    private int codeIcdNum;
    private int value; //из числа впервые взятых на учет в предыдущем году умерло до 1 года с момента установления диагноза

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

    public int getCodeIcdNum() {
        return codeIcdNum;
    }

    public void setCodeIcdNum(int codeIcdNum) {
        this.codeIcdNum = codeIcdNum;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int getNumberOfColumns() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValueByIndex(int i) throws OncoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        //return "PrMorb2200{" + "year=" + year + ", region=" + region + ", codeIcdNum=" + codeIcdNum + ", value=" + value + '}';
        return year + "\t" + region + "\t" + codeIcdNum + "\t" + value;
    }

    @Override
    protected PrMorb2200 clone() throws CloneNotSupportedException {
        return (PrMorb2200) super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compareTo(PrMorb2200 o) {
        int result = 0;
        if (this.region != o.region) {
            result = this.region - o.region;
        } else {
            if (this.year != o.year) {
                result = this.year - o.year;
            } else {
                if (this.codeIcdNum != o.codeIcdNum) {
                    result = this.codeIcdNum - o.codeIcdNum;
                }
            }
        }
        return result;
    }
}
