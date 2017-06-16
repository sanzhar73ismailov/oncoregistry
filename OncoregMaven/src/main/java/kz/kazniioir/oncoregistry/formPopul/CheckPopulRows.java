package kz.kazniioir.oncoregistry.formPopul;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import kz.kazniioir.oncoregistry.OncoException;
import kz.kazniioir.oncoregistry.RowOnco;

public class CheckPopulRows {

    public enum CalVariant {

        enum70, enum85
    };
    List<PopulationTransfer> list = new ArrayList<>();
    int[] excludedNumbersFor85Higher = {10, 50, 60, 100, 220};
    int[] excludedNumbersFor70Higher = {10, 50, 60, 100, 190, 200, 210,230};
    String[] methods = {
        "getValueAllSexBoth",
        "getValueAllSexMale",
        "getValueAllSexFeMale",
        "getValueCitySexBoth",
        "getValueCitySexMale",
        "getValueCitySexFeMale",
        "getValueVillageSexBoth",
        "getValueVillageSexMale",
        "getValueVillageSexFeMale",};
    private int ALL_AGE = 10;

    public CheckPopulRows(List<RowOnco> listOncoRows) {
        for (RowOnco rowOnco : listOncoRows) {
            PopulationTransfer item = (PopulationTransfer) rowOnco;
            list.add(item);
        }
    }

    private PopulationTransfer getPopulationTransferById(int i) {
        for (PopulationTransfer populationTransfer : list) {
            if (populationTransfer.getAgeGroup() == i) {
                return populationTransfer;
            }
        }
         for (PopulationTransfer populationTransfer : list) {
            // System.out.println(populationTransfer.getAgeGroup());
        }
        return null;
    }

    public void checkValueSums(CalVariant calVariant) throws OncoException {

        for (String method : methods) {
            if(getPopulationTransferById(ALL_AGE) == null){
                System.out.println("method = " + method);
            }
            int sumParsed = invokeGetValueMethod(getPopulationTransferById(ALL_AGE), method);
            int sumCalculated = sumHigherCalculate(calVariant, method);
            if (sumParsed != sumCalculated) {
                throw new kz.kazniioir.oncoregistry.OncoException(method + ": sumParsed=" + sumParsed + ", sumCalculated="+sumCalculated);
            }
        }
    }
    
    

    private int invokeGetValueMethod(Object instance, String methodName) {
        int result = 0;
        try {
            Class cl = instance.getClass();
            Method method = cl.getMethod(methodName);
            result = (int) method.invoke(instance);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            System.out.println("ex = " + ex);
        }
        return result;
    }

    public int sumHigherCalculate(CalVariant calVariant, String methodName) {
        int sum = 0;
        for (PopulationTransfer item : list) {
            switch (calVariant) {
                case enum70:
                    if (inDiapazon(item.getAgeGroup(), excludedNumbersFor70Higher)) {
                        continue;
                    }
                    break;
                case enum85:
                    if (inDiapazon(item.getAgeGroup(), excludedNumbersFor85Higher)) {
                        continue;
                    }
                    break;
            }

            sum += invokeGetValueMethod(item, methodName);
        }
        return sum;
    }

    private boolean inDiapazon(int number, int... args) {
        for (int i : args) {
            if (number == i) {
                return true;
            }
        }
        return false;
    }
}
