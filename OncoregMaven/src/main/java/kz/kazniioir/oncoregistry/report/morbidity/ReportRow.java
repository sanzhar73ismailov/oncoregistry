package kz.kazniioir.oncoregistry.report.morbidity;

import java.util.ArrayList;
import java.util.List;

public class ReportRow {
    private String rowName;
    private int number;
    private List<IndicatorPart> listIndicatorParts = new ArrayList<>();
    

    public String getRowName() {
        return rowName;
    }

    public void setRowName(String rowName) {
        this.rowName = rowName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<IndicatorPart> getListIndicatorParts() {
        return listIndicatorParts;
    }

    public void setListIndicatorParts(List<IndicatorPart> listIndicatorParts) {
        this.listIndicatorParts = listIndicatorParts;
    }

    public int size() {
        return listIndicatorParts.size();
    }

    public IndicatorPart get(int index) {
        return listIndicatorParts.get(index);
    }
       
    public void add(IndicatorPart indicatorPart){
        this.listIndicatorParts.add(indicatorPart);
    }

    @Override
    public String toString() {
        return "ReportRow{" + "rowName=" + rowName + ", number=" + number + ", listIndicatorParts=" + listIndicatorParts + ", size=" + size() + '}';
    }
    
    
    
}
