package kz.kazniioir.oncoregistry.report.morbidity;

public class Table1Cell {
private int region;
private int year;
private int absValue;
private int population;
private double morbidity;

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getAbsValue() {
        return absValue;
    }

    public void setAbsValue(int absValue) {
        this.absValue = absValue;
    }

    public double getMorbidity() {
        return morbidity;
    }

    public void setMorbidity(double morbidity) {
        this.morbidity = morbidity;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "Table1Cell{" + "region=" + region + ", year=" + year + ", absValue=" + absValue + ", population=" + population + ", morbidity=" + morbidity + '}';
    }
    
    


}
