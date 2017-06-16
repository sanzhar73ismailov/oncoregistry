package kz.kazniioir.oncoregistry.formAgencyStat;

import kz.kazniioir.oncoregistry.OncoException;

public class PrMortality implements kz.kazniioir.oncoregistry.RowOnco, Comparable<PrMortality> {

    private String localization;
    private String codeICD;
    private String fileName;
    private int codeIcdNum;
    private int year;
    private int region;
    private int sex;
    private int ageall;
    private int age0_4;
    private int age5_9;
    private int age10_14;
    private int age15_19;
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
    private int age70Plus;

    @Override
    public int getNumberOfColumns() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                retVal = age15_19;
                break;
            case 12:
                retVal = age20_24;
                break;
            case 13:
                retVal = age25_29;
                break;
            case 14:
                retVal = age30_34;
                break;
            case 15:
                retVal = age35_39;
                break;
            case 16:
                retVal = age40_44;
                break;
            case 17:
                retVal = age45_49;
                break;
            case 18:
                retVal = age50_54;
                break;
            case 19:
                retVal = age55_59;
                break;
            case 20:
                retVal = age60_64;
                break;
            case 21:
                retVal = age65_69;
                break;
            case 22:
                retVal = age70Plus;
                break;
            default:
                throw new OncoException("no such index: " + i);
        }
        return retVal;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public int getAge15_19() {
        return age15_19;
    }

    public void setAge15_19(int age15_19) {
        this.age15_19 = age15_19;
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

    public int getAge70Plus() {
        return age70Plus;
    }

    public void setAge70Plus(int age70Plus) {
        this.age70Plus = age70Plus;
    }

    public void updateAgeall(int ageall) {
        this.ageall += ageall;
    }

    public void updateAge0_4(int age0_4) {
        this.age0_4 += age0_4;
    }

    public void updateAge5_9(int age5_9) {
        this.age5_9 += age5_9;
    }

    public void updateAge10_14(int age10_14) {
        this.age10_14 += age10_14;
    }

    public void updateAge15_19(int age15_19) {
        this.age15_19 += age15_19;
    }

    public void updateAge20_24(int age20_24) {
        this.age20_24 += age20_24;
    }

    public void updateAge25_29(int age25_29) {
        this.age25_29 += age25_29;
    }

    public void updateAge30_34(int age30_34) {
        this.age30_34 += age30_34;
    }

    public void updateAge35_39(int age35_39) {
        this.age35_39 += age35_39;
    }

    public void updateAge40_44(int age40_44) {
        this.age40_44 += age40_44;
    }

    public void updateAge45_49(int age45_49) {
        this.age45_49 += age45_49;
    }

    public void updateAge50_54(int age50_54) {
        this.age50_54 += age50_54;
    }

    public void updateAge55_59(int age55_59) {
        this.age55_59 += age55_59;
    }

    public void updateAge60_64(int age60_64) {
        this.age60_64 += age60_64;
    }

    public void updateAge65_69(int age65_69) {
        this.age65_69 += age65_69;
    }

    public void updateAge70Plus(int age70Plus) {
        this.age70Plus += age70Plus;
    }
    
      public void updateAges(PrMortality other) {
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
    }

    int getSumOfAllAges() {
        int sum = 0;
        sum = age0_4 + age5_9 + age10_14 + age15_19 + age20_24 + age25_29 + age30_34 + age35_39 + age40_44 + age45_49
                + age50_54 + age55_59 + age60_64 + age65_69 + age70Plus;
        return sum;

    }

    
    public String toString2() {
        return "PrMortality{" + "localization=" + localization + ", codeICD=" + codeICD + ", fileName=" + fileName + ", codeIcdNum=" + codeIcdNum + ", year=" + year + ", region=" + region + ", sex=" + sex + ", ageall=" + ageall + ", age0_4=" + age0_4 + ", age5_9=" + age5_9 + ", age10_14=" + age10_14 + ", age15_19=" + age15_19 + ", age20_24=" + age20_24 + ", age25_29=" + age25_29 + ", age30_34=" + age30_34 + ", age35_39=" + age35_39 + ", age40_44=" + age40_44 + ", age45_49=" + age45_49 + ", age50_54=" + age50_54 + ", age55_59=" + age55_59 + ", age60_64=" + age60_64 + ", age65_69=" + age65_69 + ", age70Plus=" + age70Plus + '}';
    }
 
    
    public String toString() {
        return region + "\t" + year + "\t" + sex + "\t" +  codeICD + "\t" + ageall + "\t" + age0_4 + "\t" + age5_9 + "\t" + age10_14 + "\t" + age15_19 + "\t" + age20_24 + "\t" + age25_29 + "\t" + age30_34 + "\t" + age35_39 + "\t" + age40_44 + "\t" + age45_49 + "\t" + age50_54 + "\t" + age55_59 + "\t" + age60_64 + "\t" + age65_69 + "\t" + age70Plus;
    }
    @Override
    public int compareTo(PrMortality o) {
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
}
