package kz.kazniioir.oncoregistry.form1000;

import kz.kazniioir.oncoregistry.OncoException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

public class PrimaryMorbHelperShortVariant extends PrimaryMorbHelper {

    public PrimaryMorbHelperShortVariant(PrMorb1000 primaryMorbidity) {
        super(primaryMorbidity);
    }

    @Override
    public void setValueOfMorbidity(int col, Cell cell, PrMorb1000 prMorbPrevious) throws OncoException {

        int celType = cell.getCellType();
        String valueStr = null;
        int valueInt = 0;
        if (celType == Cell.CELL_TYPE_STRING) {
            valueStr = cell.getStringCellValue().trim();
        } else if (celType == Cell.CELL_TYPE_NUMERIC) {
            valueInt = (int) cell.getNumericCellValue();
        } else {
            if (celType != Cell.CELL_TYPE_BLANK) {
                return;
            }
        }

        switch (col) {
            case 0:
                if (celType == Cell.CELL_TYPE_BLANK) {
                    primaryMorbidity.setLocalization(prMorbPrevious.getLocalization());
                } else {
                    primaryMorbidity.setLocalization(valueStr);
                }
                break;
            case 1:
                if (valueStr.equals("м")) {
                    primaryMorbidity.setSex(1);
                } else if (valueStr.equals("ж")) {
                    primaryMorbidity.setSex(2);
                } else {
                    throw new OncoException("no such Sex: " + valueStr);
                }
                break;
            case 3:
                if (celType == Cell.CELL_TYPE_BLANK) {
                    primaryMorbidity.setCodeICD(null);
                    primaryMorbidity.setCodeIcdNum(primaryMorbidity.getLocalizationMap().get(null));
                } else {
                    primaryMorbidity.setCodeICD(valueStr);
                    if (primaryMorbidity.getLocalizationMap().containsKey(valueStr)) {
                        primaryMorbidity.setCodeIcdNum(primaryMorbidity.getLocalizationMap().get(valueStr));
                        //   System.out.println("codeIcdNum in cont = " + codeIcdNum);
                    } else {
                        throw new OncoException("проблема с определением локализации: " + valueStr);
                    }
                }
                break;
            case 4:
                primaryMorbidity.setAgeall(valueInt);
                break;
            case 5:
                primaryMorbidity.setAge0_4(valueInt);
                break;
            case 6:
                primaryMorbidity.setAge5_9(valueInt);
                break;
            case 7:
                primaryMorbidity.setAge10_14(valueInt);
                break;
            case 8:
                primaryMorbidity.setAge15_29(valueInt);
                primaryMorbidity.setAge15_17(-1);
                primaryMorbidity.setAge18_19(-1);
                primaryMorbidity.setAge20_24(-1);
                primaryMorbidity.setAge25_29(-1);
                primaryMorbidity.setAge15_19(-1);
                break;
            case 9:
                primaryMorbidity.setAge30_34(valueInt);
                break;
            case 10:
                primaryMorbidity.setAge35_39(valueInt);
                break;
            case 11:
                primaryMorbidity.setAge40_44(valueInt);
                break;
            case 12:
                primaryMorbidity.setAge45_49(valueInt);
                break;
            case 13:
                primaryMorbidity.setAge50_54(valueInt);
                break;
            case 14:
                primaryMorbidity.setAge55_59(valueInt);
                break;
            case 15:
                primaryMorbidity.setAge60_64(valueInt);
                break;
            case 16:
                primaryMorbidity.setAge65_69(valueInt);
                break;
            case 17:
                primaryMorbidity.setAge70_74(valueInt);
                break;
            case 18:
                primaryMorbidity.setAge75_79(valueInt);
                break;
            case 19:
                primaryMorbidity.setAge80_84(valueInt);
                break;
            case 20:
                primaryMorbidity.setAge85Plus(valueInt);
                primaryMorbidity.setAge70Plus(
                        primaryMorbidity.getAge70_74()
                        + primaryMorbidity.getAge75_79()
                        + primaryMorbidity.getAge80_84()
                        + valueInt);
                break;
        }
    }
}
