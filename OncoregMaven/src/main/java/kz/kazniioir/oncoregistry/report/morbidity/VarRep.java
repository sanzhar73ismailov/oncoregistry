/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.kazniioir.oncoregistry.report.morbidity;

/**
 *
 * @author sanzhar.ismailov
 */
public enum VarRep {
     MORBIDITY(75), MORTALITY(54);
     
     int numberTables;

    private VarRep(int numberTables) {
        this.numberTables = numberTables;
    }

    public int getNumberTables() {
        return numberTables;
    }

    
     
     
}
