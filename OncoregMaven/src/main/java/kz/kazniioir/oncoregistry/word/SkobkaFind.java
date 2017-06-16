package kz.kazniioir.oncoregistry.word;

import kz.kazniioir.oncoregistry.OncoException;

public class SkobkaFind {

    private char ch = '(';
    private int seredina;
    private String strValue;
    private String rusVal;
    private String kazVal;

    public static void main(String[] args) throws OncoException {
        String str = "sdfhjk56sdjdshfabcdefg890uiop";
        //String str = "sdfh";
        SkobkaFind sf = new SkobkaFind(str);
        System.out.println("" + sf.getRusVal());
        System.out.println("" + sf.getKazVal());

    }

    public SkobkaFind(String value) throws OncoException {

        if (value == null || value.isEmpty()) {
            throw new OncoException("значение нулл или пустое, так не должно быть!");
        }

        this.strValue = value.trim();
        if (!thereNoSkobka()) {
            if (strValue.lastIndexOf(')') != strValue.length() - 1) {
                System.out.println("strValue.lastIndexOf(')')" + strValue.lastIndexOf(')'));
                System.out.println("strValue.length() - 1" + (strValue.length() - 1));
                throw new OncoException("Последний символ у строки должен быть ), а тут " + strValue);
            }
            if (!proverkaKolZakIOtkr()) {
                throw new OncoException("кол-во откр и закр скобок не совпадает!");
            }
        }


        defineValues();




    }

    boolean proverkaKolZakIOtkr() {
        char[] charArray = strValue.toCharArray();
        int opened = 0;
        int closed = 0;
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (c == ch) {
                opened++;
            } else if (c == ')') {
                closed++;
            }
        }
        return opened == closed;
    }

    public boolean thereNoSkobka() {
        return strValue.indexOf(ch) == -1;
    }

    public boolean isMoreThanOneSkobka() {
        if (strValue.indexOf(ch) != strValue.lastIndexOf(ch)) {
            return true;
        }
        return false;
    }

    int numberSkobok() {
        char[] charArray = strValue.toCharArray();
        int num = 0;
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (c == ch) {
                num++;
            }
        }
        return num;
    }

    int nomerRazdeleniya() {
        int povtorov = numberSkobok();
        int raz = povtorov - 1;
        char[] charArray = strValue.toCharArray();
        int num = 0;
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (c == ch) {
                num++;
                if (num == raz) {
                    return i;
                }
            }

        }
        return 0;
    }

    private void defineValues() {
        if (thereNoSkobka()) {
            rusVal = strValue;
            kazVal = strValue;
        } else {
            if (!isMoreThanOneSkobka()) {
                seredina = strValue.indexOf(ch);

            } else {
                seredina = nomerRazdeleniya();
            }
            kazVal = strValue.substring(0, seredina).trim();
            rusVal = strValue.substring(seredina + 1, strValue.length() - 1);
        }

    }

    public String getRusVal() {
        return rusVal;
    }

    public String getKazVal() {
        return kazVal;
    }
}
