package kz.kazniioir.oncoregistry.report.morbidity;

public interface VariantReport {

    int getYear(int tableNo);

    int getNewTableN(int tableNo);
    
    String getXmlFile();
}


class VariantReportMorbidity implements VariantReport {

    final int coef = 7;
    final String xmlFile = "resource/queries_morb.xml";

    @Override
    public int getYear(int tableNo) {
        return 2003 + ((tableNo + 1) / coef);
    }

    @Override
    public int getNewTableN(int tableNo) {
        int numAgetables = 5; // for morbidity
        if (tableNo <= numAgetables) {
            return tableNo;
        }
        int res = 0;
        // final int coef = 7;
        int newNum = tableNo - numAgetables; // 14
        //System.out.println("newNum/coef=" + (newNum/coef));
        res = (newNum % coef);
        if (res == 0) {
            return coef + numAgetables;
        }
        return res + numAgetables;
    }

    @Override
    public String getXmlFile() {
        return xmlFile;
    }
    

}

class VariantReportMortality implements VariantReport {

    final int coef = 5;
    final String xmlFile = "resource/queries_mort.xml";

    @Override
    public int getYear(int tableNo) {
        return 2003 + ((tableNo) / coef);
    }

    @Override
    public int getNewTableN(int tableNo) {
        int numAgetables = 4; // for mortality

        if (tableNo <= numAgetables) {
            return tableNo;
        }
        int res = 0;
        // final int coef = 7;
        int newNum = tableNo - numAgetables; // 14
        //System.out.println("newNum/coef=" + (newNum/coef));
        res = (newNum % coef);
        if (res == 0) {
            return coef + numAgetables;
        }
        return res + numAgetables;
    }

    @Override
    public String getXmlFile() {
       return xmlFile;
    }
}
