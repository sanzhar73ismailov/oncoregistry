package kz.kazniioir.oncoregistry.main;

import java.util.*;
import kz.kazniioir.oncoregistry.db.HibernateUtil;
import kz.kazniioir.oncoregistry.domain.Age;
import kz.kazniioir.oncoregistry.report.morbidity.MorbidityReport;
import kz.kazniioir.oncoregistry.report.morbidity.MorbidityReportCreate;
import kz.kazniioir.oncoregistry.report.morbidity.TableNames;
import kz.kazniioir.oncoregistry.report.morbidity.VarRep;
import org.hibernate.*;

public class TestReport {

    public static void createReport() {
        //int tableNo = 12;
        VarRep varRep = VarRep.MORBIDITY;
        //VarRep varRep = VarRep.MORTALITY;
        for (int tableNo = 1; tableNo <= varRep.getNumberTables(); tableNo++) {
            if (tableNo != 12) {
                //continue;
            }
            MorbidityReport morbidityReport = MorbidityReportCreate.createMorbidityReport(tableNo, varRep);
            morbidityReport.printTableToConsole();
            morbidityReport.printTableToWordFile();
            morbidityReport.printTableToExcelFile();
        }


    }
    /* 
     public static void mainTraining(String[] args) {
       
     SessionFactory sess = HibernateUtil.getSessionFactory();
     Session session = sess.openSession();
     Transaction tr = session.beginTransaction();
     tr.begin();
     Query query = session.createQuery("from Age");
     List<Age> list = query.list();
     tr.commit();
     for (Age localization : list) {
     System.out.println("mapAges.put(\"" + localization.getName() + "\", "
     + localization.getId() + ");");
     }
       
     }
     *  */
}
