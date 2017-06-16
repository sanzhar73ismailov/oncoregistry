package kz.kazniioir.oncoregistry;

public class LocalizationSelected {

    public static final int vse = 1;
    public static final int pishevod = 4;
    public static final int zheludok = 5;
    public static final int obodoch_kishka = 6;
    public static final int prymaya_kishka = 7;
    public static final int legkie = 11;
    public static final int melanoma_kozhi = 13;
    public static final int molochnaya_zheleza = 16;
    public static final int sheika_matki = 17;
    public static final int predstat_zheleza = 21;
    public static final int limphat_krovet_tkani = 28;
    public static final int drugie = 50;
    
    //для заболеваемости
    public static final int pechen = 8;
    public static final int podzhel_zheleza = 9;
    
    // other male loalizations
    public static final int polovoi_chlen = 20;
    public static final int yaichko = 22;
    public static final int drugie_muzhskie_pol_organi = 109;
    // other female loalizations
    public static final int telo_matki = 18;
    public static final int yaichnik = 19;
    public static final int drugie_zhenskie_pol_organi = 105;
    //
    public static final int[] arrayLocalizations = {
        vse,
        pishevod,
        zheludok,
        obodoch_kishka,
        prymaya_kishka,
        legkie,
        melanoma_kozhi,
        molochnaya_zheleza,
        sheika_matki,
        telo_matki,
        yaichnik,
        predstat_zheleza,
        limphat_krovet_tkani,
        drugie,
        pechen, // для заболеваемости
        podzhel_zheleza // для заболеваемости
    };
    public static final int[] arrayMaleLocalizations = {
        polovoi_chlen,
        predstat_zheleza,
        yaichko,
        drugie_muzhskie_pol_organi
    };
    
    public static final int[] arrayFemaleLocalizations = {
        molochnaya_zheleza,
        sheika_matki,
        telo_matki,
        yaichnik,
        drugie_zhenskie_pol_organi
    };
    
    //
    public static final int limphomi = 41;
    public static final int leukemiya = 42;

    public static boolean isSelectedLocalization(int val) {
        for (int i : arrayLocalizations) {
            if (val == i) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMaleLocalization(int val) {
        for (int i : arrayMaleLocalizations) {
            if (val == i) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFemaleLocalization(int val) {
        for (int i : arrayFemaleLocalizations) {
            if (val == i) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSexSpecificLocalization(int val) {
        return isMaleLocalization(val) || isFemaleLocalization(val);
    }

//    public static void main(String[] args) {
//        for (int i = 0; i < 110; i++) {
//            System.out.println(i + "=" + isSexSpecificLocalization(i)+" " + isMaleLocalization(i)+ " " + isFemaleLocalization(i));
//        }
//    }
}
