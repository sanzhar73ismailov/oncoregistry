package kz.kazniioir.oncoregistry.form1000;

import kz.kazniioir.oncoregistry.LocalizationMap;
import kz.kazniioir.oncoregistry.OncoException;
import kz.kazniioir.oncoregistry.RowOnco;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

public class PrMorb1000 implements RowOnco, Cloneable, Comparable<PrMorb1000> {

    private String localization;
    private String codeICD;
    private int year;
    private int region;
    private int sex;
    private int ageall;
    private int age0_4;
    private int age5_9;
    private int age10_14;
    private int age15_17;
    private int age18_19;
    private int age20_24;
    private int age25_29;
    private int age30_34;
    private int age35_39;
    private int age40_44;
    private int age45_49;
    private int age50_54;
    private int age55_59;
    private int age60_64;
    private int age65_69;
    private int age70_74;
    private int age75_79;
    private int age80_84;
    private int age85Plus;
    private int age15_29;
    private int age15_19;
    private int age70Plus;
    private String fileName;
    private int codeIcdNum;
    private boolean shortAgeVariant;
    private final int NUMBER_ROWS = 30;
    private Map<String, Integer> localizationMap = LocalizationMap.getInstance().getLocalizationMap();
    private PrimaryMorbHelper primaryMorbHelper;

    public PrMorb1000(boolean shortAgeVariant) {
        this.shortAgeVariant = shortAgeVariant;
        if (shortAgeVariant) {
            primaryMorbHelper = new PrimaryMorbHelperShortVariant(this);
        } else {
            primaryMorbHelper = new PrimaryMorbHelper(this);
        }
    }

    public Map<String, Integer> getLocalizationMap() {
        return localizationMap;
    }

    public int getCodeIcdNum() {
        return codeIcdNum;
    }

    public void setCodeIcdNum(int codeIcdNum) {
        this.codeIcdNum = codeIcdNum;
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAgeall() {
        return ageall;
    }

    public void setAgeall(int ageall) {
        this.ageall = ageall;
    }

    public int getAge0_4() {
        return age0_4;
    }

    public void setAge0_4(int age0_4) {
        this.age0_4 = age0_4;
    }

    public int getAge5_9() {
        return age5_9;
    }

    public void setAge5_9(int age5_9) {
        this.age5_9 = age5_9;
    }

    public int getAge10_14() {
        return age10_14;
    }

    public void setAge10_14(int age10_14) {
        this.age10_14 = age10_14;
    }

    public int getAge15_17() {
        return age15_17;
    }

    public void setAge15_17(int age15_17) {
        this.age15_17 = age15_17;
    }

    public int getAge18_19() {
        return age18_19;
    }

    public void setAge18_19(int age18_19) {
        this.age18_19 = age18_19;
    }

    public int getAge20_24() {
        return age20_24;
    }

    public void setAge20_24(int age20_24) {
        this.age20_24 = age20_24;
    }

    public int getAge25_29() {
        return age25_29;
    }

    public void setAge25_29(int age25_29) {
        this.age25_29 = age25_29;
    }

    public int getAge30_34() {
        return age30_34;
    }

    public void setAge30_34(int age30_34) {
        this.age30_34 = age30_34;
    }

    public int getAge35_39() {
        return age35_39;
    }

    public void setAge35_39(int age35_39) {
        this.age35_39 = age35_39;
    }

    public int getAge40_44() {
        return age40_44;
    }

    public void setAge40_44(int age40_44) {
        this.age40_44 = age40_44;
    }

    public int getAge45_49() {
        return age45_49;
    }

    public void setAge45_49(int age45_49) {
        this.age45_49 = age45_49;
    }

    public int getAge50_54() {
        return age50_54;
    }

    public void setAge50_54(int age50_54) {
        this.age50_54 = age50_54;
    }

    public int getAge55_59() {
        return age55_59;
    }

    public void setAge55_59(int age55_59) {
        this.age55_59 = age55_59;
    }

    public int getAge60_64() {
        return age60_64;
    }

    public void setAge60_64(int age60_64) {
        this.age60_64 = age60_64;
    }

    public int getAge65_69() {
        return age65_69;
    }

    public void setAge65_69(int age65_69) {
        this.age65_69 = age65_69;
    }

    public int getAge70_74() {
        return age70_74;
    }

    public void setAge70_74(int age70_74) {
        this.age70_74 = age70_74;
    }

    public int getAge75_79() {
        return age75_79;
    }

    public void setAge75_79(int age75_79) {
        this.age75_79 = age75_79;
    }

    public int getAge80_84() {
        return age80_84;
    }

    public void setAge80_84(int age80_84) {
        this.age80_84 = age80_84;
    }

    public int getAge85Plus() {
        return age85Plus;
    }

    public void setAge85Plus(int age85Plus) {
        this.age85Plus = age85Plus;
    }

    public int getAge15_29() {
        return age15_29;
    }

    public void setAge15_29(int age15_29) {
        this.age15_29 = age15_29;
    }

    public int getAge70Plus() {
        return age70Plus;
    }

    public void setAge70Plus(int age70Plus) {
        this.age70Plus = age70Plus;
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

    @Override
    public String toString() {
        return "PrimaryMorbidity{" + "localization=" + localization + ", codeICD=" + codeICD + ", year=" + year + ", region=" + region + ", sex=" + sex + ", ageall=" + ageall + ", age0_4=" + age0_4 + ", age5_9=" + age5_9 + ", age10_14=" + age10_14 + ", age15_17=" + age15_17 + ", age18_19=" + age18_19 + ", age20_24=" + age20_24 + ", age25_29=" + age25_29 + ", age30_34=" + age30_34 + ", age35_39=" + age35_39 + ", age40_44=" + age40_44 + ", age45_49=" + age45_49 + ", age50_54=" + age50_54 + ", age55_59=" + age55_59 + ", age60_64=" + age60_64 + ", age65_69=" + age65_69 + ", age70_74=" + age70_74 + ", age75_79=" + age75_79 + ", age80_84=" + age80_84 + ", age85=" + age85Plus + ", fileName=" + fileName + ", NUMBER_ROWS=" + NUMBER_ROWS + ", localizationMap=" + localizationMap + ", codeIcdNum=" + codeIcdNum + '}';
    }

    @Override
    public int getNumberOfColumns() {
        return NUMBER_ROWS;
    }

    @Override
    public Object getValueByIndex(int i) throws OncoException {
        Object retVal = null;
        switch (i) {
            case 0:
                retVal = region;
                break;
            case 1:
                retVal = year;
                break;
            case 2:
                retVal = localization;
                break;
            case 3:
                retVal = codeIcdNum;
                break;
            case 4:
                retVal = codeICD == null ? "" : codeICD;
                break;
            case 5:
                retVal = fileName;
                break;
            case 6:
                retVal = sex;
                break;
            case 7:
                retVal = ageall;
                break;
            case 8:
                retVal = age0_4;
                break;
            case 9:
                retVal = age5_9;
                break;
            case 10:
                retVal = age10_14;
                break;
            case 11:
                retVal = age15_17;
                break;
            case 12:
                retVal = age18_19;
                break;
            case 13:
                retVal = age15_19;
                break;
            case 14:
                retVal = age20_24;
                break;
            case 15:
                retVal = age25_29;
                break;
            case 16:
                retVal = age15_29;
                break;
            case 17:
                retVal = age30_34;
                break;
            case 18:
                retVal = age35_39;
                break;
            case 19:
                retVal = age40_44;
                break;
            case 20:
                retVal = age45_49;
                break;
            case 21:
                retVal = age50_54;
                break;
            case 22:
                retVal = age55_59;
                break;
            case 23:
                retVal = age60_64;
                break;
            case 24:
                retVal = age65_69;
                break;
            case 25:
                retVal = age70_74;
                break;
            case 26:
                retVal = age75_79;
                break;
            case 27:
                retVal = age80_84;
                break;
            case 28:
                retVal = age70Plus;
                break;
            case 29:
                retVal = age85Plus;
                break;
            default:
                throw new OncoException("no such index: " + i);
        }
        return retVal;
    }

    public void setValueOfMorbidity(int col, Cell cell, PrMorb1000 prMorbPrevious) throws OncoException {
        primaryMorbHelper.setValueOfMorbidity(col, cell, prMorbPrevious);
    }

    public boolean containsCode() {
        return localizationMap.containsKey(codeICD);
    }

    int getSumOfAllAges() {
        int sum = 0;
        sum = age0_4 + age5_9 + age10_14 + age30_34 + age35_39 + age40_44 + age45_49
                + age50_54 + age55_59 + age60_64 + age65_69 + age70_74
                + age75_79 + age80_84 + age85Plus;
        if (!shortAgeVariant) {
            sum += age15_17 + age18_19 + age20_24 + age25_29;
        } else {
            sum += age15_29;
        }

        return sum;

    }

    int getSumOfAllAgesWithAge70Plus() {
        int sum = 0;
        sum = age0_4 + age5_9 + age10_14 + age30_34 + age35_39 + age40_44 + age45_49
                + age50_54 + age55_59 + age60_64 + age65_69 + age70Plus;
        if (!shortAgeVariant) {
            sum += age15_19 + age20_24 + age25_29;
        } else {
            sum += age15_29;
        }

        return sum;

    }

    public int getAge15_19() {
        return age15_19;
    }

    public void setAge15_19(int age15_19) {
        this.age15_19 = age15_19;
    }

    @Override
    protected PrMorb1000 clone() throws CloneNotSupportedException {
//        PrMorb1000 clone = new PrMorb1000(shortAgeVariant);
//        clone.setLocalization(localization);
//        clone.codeICD = codeICD;
//        clone.year = year;
//        clone.region = region;
//        clone.sex = sex;
//        clone.fileName = fileName;
//        clone.codeIcdNum = codeIcdNum;
//        clone.setAgeall(getAgeall());
//        clone.setAge0_4(getAge0_4());
//        clone.setAge5_9(getAge5_9());
//        clone.setAge10_14(getAge10_14());
//        clone.setAge15_17(getAge15_17());
//        clone.setAge18_19(getAge18_19());
//        clone.setAge20_24(getAge20_24());
//        clone.setAge25_29(getAge25_29());
//        clone.setAge30_34(getAge30_34());
//        clone.setAge35_39(getAge35_39());
//        clone.setAge40_44(getAge40_44());
//        clone.setAge45_49(getAge45_49());
//        clone.setAge50_54(getAge50_54());
//        clone.setAge55_59(getAge55_59());
//        clone.setAge60_64(getAge60_64());
//        clone.setAge65_69(getAge65_69());
//        clone.setAge70_74(getAge70_74());
//        clone.setAge75_79(getAge75_79());
//        clone.setAge80_84(getAge80_84());
//        clone.setAge85Plus(getAge85Plus());
//        clone.setAge15_29(getAge15_29());
//        clone.setAge15_19(getAge15_19());
//        clone.setAge70Plus(getAge70Plus());
//        return clone;
       return (PrMorb1000) super.clone();
    }

    void addAllAgeValues(PrMorb1000 pmOther) {
        setAgeall(getAgeall() + pmOther.getAgeall());
        setAge0_4(getAge0_4() + pmOther.getAge0_4());
        setAge5_9(getAge5_9() + pmOther.getAge5_9());
        setAge10_14(getAge10_14() + pmOther.getAge10_14());
        setAge15_17(getAge15_17() + pmOther.getAge15_17());
        setAge15_19(getAge15_19() + pmOther.getAge15_19());
        setAge18_19(getAge18_19() + pmOther.getAge18_19());
        setAge20_24(getAge20_24() + pmOther.getAge20_24());
        setAge25_29(getAge25_29() + pmOther.getAge25_29());
        setAge15_29(getAge15_29() + pmOther.getAge15_29());
        setAge30_34(getAge30_34() + pmOther.getAge30_34());
        setAge35_39(getAge35_39() + pmOther.getAge35_39());
        setAge40_44(getAge40_44() + pmOther.getAge40_44());
        setAge45_49(getAge45_49() + pmOther.getAge45_49());
        setAge50_54(getAge50_54() + pmOther.getAge50_54());
        setAge55_59(getAge55_59() + pmOther.getAge55_59());
        setAge60_64(getAge60_64() + pmOther.getAge60_64());
        setAge65_69(getAge65_69() + pmOther.getAge65_69());
        setAge70_74(getAge70_74() + pmOther.getAge70_74());
        setAge75_79(getAge75_79() + pmOther.getAge75_79());
        setAge80_84(getAge80_84() + pmOther.getAge80_84());
        setAge70Plus(getAge70Plus() + pmOther.getAge70Plus());
        setAge85Plus(getAge85Plus() + pmOther.getAge85Plus());
    }

    public static void main(String[] args) {
        String[] str = {
            "etAgeall",
            "etAge0_4",
            "etAge5_9",
            "etAge10_14",
            "etAge15_17",
            "etAge18_19",
            "etAge20_24",
            "etAge25_29",
            "etAge30_34",
            "etAge35_39",
            "etAge40_44",
            "etAge45_49",
            "etAge50_54",
            "etAge55_59",
            "etAge60_64",
            "etAge65_69",
            "etAge70_74",
            "etAge75_79",
            "etAge80_84",
            "etAge85Plus",
            "etAge15_29",
            "etAge15_19",
            "etAge70Plus",};
        for (String string : str) {
            System.out.println("clone.s" + string + "(g" + string + "());");
        }

    }

    @Override
    public int compareTo(PrMorb1000 o) {
        int result = 0;
        if (this.year != o.year) {
            result = this.year - o.year;
        } else {
            if (this.region != o.region) {
                result = this.region - o.region;
            } else {
                if (this.sex != o.sex) {
                    result = this.sex - o.sex;
                } else {
                    if (this.codeIcdNum != o.codeIcdNum) {
                        result = this.codeIcdNum - o.codeIcdNum;
                    }
                }
            }
        }
        return result;
    }

    void updateAges(PrMorb1000 other) {
        this.ageall += other.ageall;
        this.age0_4 += other.age0_4;
        this.age5_9 += other.age5_9;
        this.age10_14 += other.age10_14;
        this.age15_19 += other.age15_19;
        this.age20_24 += other.age20_24;
        this.age25_29 += other.age25_29;
        this.age30_34 += other.age30_34;
        this.age35_39 += other.age35_39;
        this.age40_44 += other.age40_44;
        this.age45_49 += other.age45_49;
        this.age50_54 += other.age50_54;
        this.age55_59 += other.age55_59;
        this.age60_64 += other.age60_64;
        this.age65_69 += other.age65_69;
        this.age70Plus += other.age70Plus;

        this.age15_17 += other.age15_17;
        this.age18_19 += other.age18_19;
        this.age70_74 += other.age70_74;
        this.age75_79 += other.age75_79;
        this.age80_84 += other.age80_84;
        this.age85Plus += other.age85Plus;
        this.age15_29 += other.age15_29;

    }
    void cleareAges() {
        this.ageall = 0;
        this.age0_4 = 0;
        this.age5_9 = 0;
        this.age10_14 = 0;
        this.age15_19 = 0;
        this.age20_24 = 0;
        this.age25_29 = 0;
        this.age30_34 = 0;
        this.age35_39 = 0;
        this.age40_44 = 0;
        this.age45_49 = 0;
        this.age50_54 = 0;
        this.age55_59 = 0;
        this.age60_64 = 0;
        this.age65_69 = 0;
        this.age70Plus = 0;

        this.age15_17 = 0;
        this.age18_19 = 0;
        this.age70_74 = 0;
        this.age75_79 = 0;
        this.age80_84 = 0;
        this.age85Plus = 0;
        this.age15_29 = 0;

    }
}
