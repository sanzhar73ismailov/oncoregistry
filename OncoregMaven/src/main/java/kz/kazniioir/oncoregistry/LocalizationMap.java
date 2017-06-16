package kz.kazniioir.oncoregistry;


import java.util.LinkedHashMap;
import java.util.Map;

public class LocalizationMap {

    private Map<String, Integer> localizationMap = new LinkedHashMap<>();
    private static LocalizationMap instance = new LocalizationMap();
        public static final int CODE_INT_ALL_LOC = 50;
    
    

    private LocalizationMap() {
        fillLocalizationMap();
    }
    
    public static LocalizationMap getInstance(){
        return instance;
    }

    public Map<String, Integer> getLocalizationMap() {
        return localizationMap;
    }

    
  

    private void fillLocalizationMap() {
        localizationMap.put("С00-С97", 1);
        localizationMap.put("С00", 2);
        localizationMap.put("С01-С14, С46.2", 3);
        localizationMap.put("С01-С14,С46.2", 3);
        localizationMap.put("С15", 4);
        localizationMap.put("С16", 5);
        localizationMap.put("С18", 6);
        localizationMap.put("С19-С21", 7);
        localizationMap.put("С22", 8);
        localizationMap.put("С22, С23", 8);
        localizationMap.put("С25", 9);
        localizationMap.put("С32", 10);
        localizationMap.put("С33-С34", 11);
        localizationMap.put("С33,С34", 11);
        localizationMap.put("С40-С41", 12);
        localizationMap.put("С43", 13);
        localizationMap.put("С44, С46.0", 14);
        localizationMap.put("С44,С46.0", 14);
        localizationMap.put("С45,С46.1,С47,С49", 15);
        localizationMap.put("С46.1,С47,С49", 15);
        localizationMap.put("С50", 16);
        localizationMap.put("С53", 17);
        localizationMap.put("С54", 18);
        localizationMap.put("С56", 19);
        localizationMap.put("С60", 20);
        localizationMap.put("С61", 21);
        localizationMap.put("С62", 22);
        localizationMap.put("С64", 23);
        localizationMap.put("С67", 24);
        localizationMap.put("С69", 25);
        localizationMap.put("С70-С72", 26);
        localizationMap.put("С73", 27);
        localizationMap.put("С81-С96", 28);
        localizationMap.put("С82-С85", 29);
        localizationMap.put("С81", 30);
        localizationMap.put("С91.0", 31);
        localizationMap.put("С91.1-9", 32);
        localizationMap.put("С92.0", 33);
        localizationMap.put("С92.1-9", 34);
        localizationMap.put("С93-С95", 35);
        localizationMap.put("С88, С90, С96", 36);
        localizationMap.put("С81-С90,С96", 41);
        localizationMap.put("С81-С90, С96", 41);
        localizationMap.put("С91-С96", 42);
        localizationMap.put("С91-С95, С96", 42);

        localizationMap.put(null, CODE_INT_ALL_LOC);
        localizationMap.put("", CODE_INT_ALL_LOC);

    }
}
