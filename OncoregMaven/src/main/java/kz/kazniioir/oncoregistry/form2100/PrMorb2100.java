package kz.kazniioir.oncoregistry.form2100;

import kz.kazniioir.oncoregistry.LocalizationMap;
import kz.kazniioir.oncoregistry.OncoException;
import kz.kazniioir.oncoregistry.RowOnco;
import java.util.LinkedHashMap;
import java.util.Map;
import kz.kazniioir.oncoregistry.form1000.PrMorb1000;

public class PrMorb2100 implements RowOnco, Cloneable, Comparable<PrMorb2100> {

    private String localization;
    private String codeICD;
    private int codeIcdNum;
    private int year;
    private int region;
    private String fileName;
    private boolean younger14Years;
    private int registeredInTheStartOfYear;
    private int vzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis;
    private int vzyatoNaUchetThisYear_FirstTime;
    private int from3Group_DetectedDuringProfAll;
    private int from3Group_DetectedDuringProf01And02Stage;
    private int from3Group_DiagnosoConfirmedMorfologically;
    private int from3Group_HadStage1and2;
    private int from3Group_HadStage3;
    private int from3Group_HadStage4;
    private int struckOffTheRegisterAll;
    private int struckOffTheRegisterGoOut;
    private int struckOffTheRegisterDiagNotConfirmed;
    private int struckOffTheRegisterNoInfo;
    private int struckOffTheRegisterWithDiagBazalioma;
    private int struckOffTheRegisterDiedFromAnotherDisease;
    private int registeredInTheEndOfYearAll;
    private int registeredInTheEndOfYear5YearsAndMore;
    private Map<String, Integer> localizationMap = LocalizationMap.getInstance().getLocalizationMap();

    public Map<String, Integer> getLocalizationMap() {
        return localizationMap;
    }

    @Override
    public int getNumberOfColumns() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValueByIndex(int i) throws OncoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getRegisteredInTheStartOfYear() {
        return registeredInTheStartOfYear;
    }

    public void setRegisteredInTheStartOfYear(int registeredInTheStartOfYear) {
        this.registeredInTheStartOfYear = registeredInTheStartOfYear;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getCodeICD() {
        return codeICD;
    }

    public void setCodeICD(String codeICD) {
        this.codeICD = codeICD;
    }

    public int getCodeIcdNum() {
        return codeIcdNum;
    }

    public void setCodeIcdNum(int codeIcdNum) {
        this.codeIcdNum = codeIcdNum;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getVzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis() {
        return vzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis;
    }

    public void setVzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis(int vzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis) {
        this.vzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis = vzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis;
    }

    public int getVzyatoNaUchetThisYear_FirstTime() {
        return vzyatoNaUchetThisYear_FirstTime;
    }

    public void setVzyatoNaUchetThisYear_FirstTime(int vzyatoNaUchetThisYear_FirstTime) {
        this.vzyatoNaUchetThisYear_FirstTime = vzyatoNaUchetThisYear_FirstTime;
    }

    public int getFrom3Group_DetectedDuringProfAll() {
        return from3Group_DetectedDuringProfAll;
    }

    public void setFrom3Group_DetectedDuringProfAll(int from3Group_DetectedDuringProfAll) {
        this.from3Group_DetectedDuringProfAll = from3Group_DetectedDuringProfAll;
    }

    public int getFrom3Group_DetectedDuringProf01And02Stage() {
        return from3Group_DetectedDuringProf01And02Stage;
    }

    public void setFrom3Group_DetectedDuringProf01And02Stage(int from3Group_DetectedDuringProf01And02Stage) {
        this.from3Group_DetectedDuringProf01And02Stage = from3Group_DetectedDuringProf01And02Stage;
    }

    public int getFrom3Group_DiagnosoConfirmedMorfologically() {
        return from3Group_DiagnosoConfirmedMorfologically;
    }

    public void setFrom3Group_DiagnosoConfirmedMorfologically(int from3Group_DiagnosoConfirmedMorfologically) {
        this.from3Group_DiagnosoConfirmedMorfologically = from3Group_DiagnosoConfirmedMorfologically;
    }

    public int getFrom3Group_HadStage1and2() {
        return from3Group_HadStage1and2;
    }

    public void setFrom3Group_HadStage1and2(int from3Group_HadStage1and2) {
        this.from3Group_HadStage1and2 = from3Group_HadStage1and2;
    }

    public int getFrom3Group_HadStage3() {
        return from3Group_HadStage3;
    }

    public void setFrom3Group_HadStage3(int from3Group_HadStage3) {
        this.from3Group_HadStage3 = from3Group_HadStage3;
    }

    public int getFrom3Group_HadStage4() {
        return from3Group_HadStage4;
    }

    public void setFrom3Group_HadStage4(int from3Group_HadStage4) {
        this.from3Group_HadStage4 = from3Group_HadStage4;
    }

    public int getStruckOffTheRegisterAll() {
        return struckOffTheRegisterAll;
    }

    public void setStruckOffTheRegisterAll(int struckOffTheRegisterAll) {
        this.struckOffTheRegisterAll = struckOffTheRegisterAll;
    }

    public int getStruckOffTheRegisterGoOut() {
        return struckOffTheRegisterGoOut;
    }

    public void setStruckOffTheRegisterGoOut(int struckOffTheRegisterGoOut) {
        this.struckOffTheRegisterGoOut = struckOffTheRegisterGoOut;
    }

    public int getStruckOffTheRegisterDiagNotConfirmed() {
        return struckOffTheRegisterDiagNotConfirmed;
    }

    public void setStruckOffTheRegisterDiagNotConfirmed(int struckOffTheRegisterDiagNotConfirmed) {
        this.struckOffTheRegisterDiagNotConfirmed = struckOffTheRegisterDiagNotConfirmed;
    }

    public int getStruckOffTheRegisterNoInfo() {
        return struckOffTheRegisterNoInfo;
    }

    public void setStruckOffTheRegisterNoInfo(int struckOffTheRegisterNoInfo) {
        this.struckOffTheRegisterNoInfo = struckOffTheRegisterNoInfo;
    }

    public int getStruckOffTheRegisterWithDiagBazalioma() {
        return struckOffTheRegisterWithDiagBazalioma;
    }

    public void setStruckOffTheRegisterWithDiagBazalioma(int struckOffTheRegisterWithDiagBazalioma) {
        this.struckOffTheRegisterWithDiagBazalioma = struckOffTheRegisterWithDiagBazalioma;
    }

    public int getStruckOffTheRegisterDiedFromAnotherDisease() {
        return struckOffTheRegisterDiedFromAnotherDisease;
    }

    public void setStruckOffTheRegisterDiedFromAnotherDisease(int struckOffTheRegisterDiedFromAnotherDisease) {
        this.struckOffTheRegisterDiedFromAnotherDisease = struckOffTheRegisterDiedFromAnotherDisease;
    }

    public int getRegisteredInTheEndOfYearAll() {
        return registeredInTheEndOfYearAll;
    }

    public void setRegisteredInTheEndOfYearAll(int registeredInTheEndOfYearAll) {
        this.registeredInTheEndOfYearAll = registeredInTheEndOfYearAll;
    }

    public int getRegisteredInTheEndOfYear5YearsAndMore() {
        return registeredInTheEndOfYear5YearsAndMore;
    }

    public void setRegisteredInTheEndOfYear5YearsAndMore(int registeredInTheEndOfYear5YearsAndMore) {
        this.registeredInTheEndOfYear5YearsAndMore = registeredInTheEndOfYear5YearsAndMore;
    }

    public boolean containsCode() {
        return localizationMap.containsKey(codeICD);
    }

    @Override
    public String toString() {
        return "PrMorb2100{" + "localization=" + localization + "; younger14Years=" + younger14Years + "; codeICD=" + codeICD + "; codeIcdNum=" + codeIcdNum + "; year=" + year + "; region=" + region + "; fileName=" + fileName + "; vzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis=" + vzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis + "; vzyatoNaUchetThisYear_FirstTime=" + vzyatoNaUchetThisYear_FirstTime + "; from3Group_DetectedDuringProfAll=" + from3Group_DetectedDuringProfAll + "; from3Group_DetectedDuringProf01And02Stage=" + from3Group_DetectedDuringProf01And02Stage + "; from3Group_DiagnosoConfirmedMorfologically=" + from3Group_DiagnosoConfirmedMorfologically + "; from3Group_HadStage1and2=" + from3Group_HadStage1and2 + "; from3Group_HadStage3=" + from3Group_HadStage3 + "; from3Group_HadStage4=" + from3Group_HadStage4 + "; struckOffTheRegisterAll=" + struckOffTheRegisterAll + "; struckOffTheRegisterGoOut=" + struckOffTheRegisterGoOut + "; struckOffTheRegisterDiagNotConfirmed=" + struckOffTheRegisterDiagNotConfirmed + "; struckOffTheRegisterNoInfo=" + struckOffTheRegisterNoInfo + "; struckOffTheRegisterWithDiagBazalioma=" + struckOffTheRegisterWithDiagBazalioma + "; struckOffTheRegisterDiedFromAnotherDisease=" + struckOffTheRegisterDiedFromAnotherDisease + "; registeredInTheEndOfYearAll=" + registeredInTheEndOfYearAll + "; registeredInTheEndOfYearYearsAndMore=" + registeredInTheEndOfYear5YearsAndMore + '}';
    }

    public boolean isYounger14Years() {
        return younger14Years;
    }

    public void setYounger14Years(boolean younger14Years) {
        this.younger14Years = younger14Years;
    }

    @Override
    public int compareTo(PrMorb2100 o) {
        int result = 0;
        if (this.year != o.year) {
            result = this.year - o.year;
        } else {
            if (this.region != o.region) {
                result = this.region - o.region;
            } else {
                if (this.codeIcdNum != o.codeIcdNum) {
                    result = this.codeIcdNum - o.codeIcdNum;
                }
            }
        }
        return result;
    }

    @Override
    public  PrMorb2100 clone() throws CloneNotSupportedException {
        return (PrMorb2100) super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    public void update(PrMorb2100 other) {
        this.registeredInTheStartOfYear += other.registeredInTheStartOfYear;
        this.vzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis += other.vzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis;
        this.vzyatoNaUchetThisYear_FirstTime += other.vzyatoNaUchetThisYear_FirstTime;
        this.from3Group_DetectedDuringProfAll += other.from3Group_DetectedDuringProfAll;
        this.from3Group_DetectedDuringProf01And02Stage += other.from3Group_DetectedDuringProf01And02Stage;
        this.from3Group_DiagnosoConfirmedMorfologically += other.from3Group_DiagnosoConfirmedMorfologically;
        this.from3Group_HadStage1and2 += other.from3Group_HadStage1and2;
        this.from3Group_HadStage3 += other.from3Group_HadStage3;
        this.from3Group_HadStage4 += other.from3Group_HadStage4;
        this.struckOffTheRegisterAll += other.struckOffTheRegisterAll;
        this.struckOffTheRegisterGoOut += other.struckOffTheRegisterGoOut;
        this.struckOffTheRegisterDiagNotConfirmed += other.struckOffTheRegisterDiagNotConfirmed;
        this.struckOffTheRegisterNoInfo += other.struckOffTheRegisterNoInfo;
        this.struckOffTheRegisterWithDiagBazalioma += other.struckOffTheRegisterWithDiagBazalioma;
        this.struckOffTheRegisterDiedFromAnotherDisease += other.struckOffTheRegisterDiedFromAnotherDisease;
        this.registeredInTheEndOfYearAll += other.registeredInTheEndOfYearAll;
        this.registeredInTheEndOfYear5YearsAndMore += other.registeredInTheEndOfYear5YearsAndMore;
    }
}
