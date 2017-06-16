package kz.kazniioir.oncoregistry.formPopul;

import kz.kazniioir.oncoregistry.OncoException;

public class PopulationTransfer implements kz.kazniioir.oncoregistry.RowOnco {

    private int year;
    private int region;
    private int ageGroup;
    private int valueAllSexBoth;
    private int valueAllSexMale;
    private int valueAllSexFeMale;
    private int valueCitySexBoth;
    private int valueCitySexMale;
    private int valueCitySexFeMale;
    private int valueVillageSexBoth;
    private int valueVillageSexMale;
    private int valueVillageSexFeMale;

    @Override
    public int getNumberOfColumns() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValueByIndex(int i) throws OncoException {
        Object value = null;
        switch (i) {
            case 0:
                value = getValueAllSexBoth();
                break;
            case 1:
                value = getValueAllSexMale();
                break;
            case 2:
                value = getValueAllSexFeMale();
                break;
            case 3:
                value = getValueCitySexBoth();
                break;
            case 4:
                value = getValueCitySexMale();
                break;
            case 5:
                value = getValueCitySexFeMale();
                break;
            case 6:
                value = getValueVillageSexBoth();
                break;
            case 7:
                value = getValueVillageSexMale();
                break;
            case 8:
                value = getValueVillageSexFeMale();
                break;
        }
        return value;
    }

    public int getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(int ageGroup) {
        this.ageGroup = ageGroup;
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

    public int getValueAllSexBoth() {
        return valueAllSexBoth;
    }

    public void setValueAllSexBoth(int valueAllSexBoth) {
        this.valueAllSexBoth = valueAllSexBoth;
    }

    public int getValueAllSexMale() {
        return valueAllSexMale;
    }

    public void setValueAllSexMale(int valueAllSexMale) {
        this.valueAllSexMale = valueAllSexMale;
    }

    public int getValueAllSexFeMale() {
        return valueAllSexFeMale;
    }

    public void setValueAllSexFeMale(int valueAllSexFeMale) {
        this.valueAllSexFeMale = valueAllSexFeMale;
    }

    public int getValueCitySexBoth() {
        return valueCitySexBoth;
    }

    public void setValueCitySexBoth(int valueCitySexBoth) {
        this.valueCitySexBoth = valueCitySexBoth;
    }

    public int getValueCitySexMale() {
        return valueCitySexMale;
    }

    public void setValueCitySexMale(int valueCitySexMale) {
        this.valueCitySexMale = valueCitySexMale;
    }

    public int getValueCitySexFeMale() {
        return valueCitySexFeMale;
    }

    public void setValueCitySexFeMale(int valueCitySexFeMale) {
        this.valueCitySexFeMale = valueCitySexFeMale;
    }

    public int getValueVillageSexBoth() {
        return valueVillageSexBoth;
    }

    public void setValueVillageSexBoth(int valueVillageSexBoth) {
        this.valueVillageSexBoth = valueVillageSexBoth;
    }

    public int getValueVillageSexMale() {
        return valueVillageSexMale;
    }

    public void setValueVillageSexMale(int valueVillageSexMale) {
        this.valueVillageSexMale = valueVillageSexMale;
    }

    public int getValueVillageSexFeMale() {
        return valueVillageSexFeMale;
    }

    public void setValueVillageSexFeMale(int valueVillageSexFeMale) {
        this.valueVillageSexFeMale = valueVillageSexFeMale;
    }

    @Override
    public String toString() {
        return "PopulationTransfer{" + "year=" + year + ", region=" + region + ", ageGroup=" + ageGroup + ", valueAllSexBoth=" + valueAllSexBoth + ", valueAllSexMale=" + valueAllSexMale + ", valueAllSexFeMale=" + valueAllSexFeMale + ", valueCitySexBoth=" + valueCitySexBoth + ", valueCitySexMale=" + valueCitySexMale + ", valueCitySexFeMale=" + valueCitySexFeMale + ", valueVillageSexBoth=" + valueVillageSexBoth + ", valueVillageSexMale=" + valueVillageSexMale + ", valueVillageSexFeMale=" + valueVillageSexFeMale + '}';
    }
}
