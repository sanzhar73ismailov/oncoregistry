package kz.kazniioir.oncoregistry.main;

import kz.kazniioir.oncoregistry.db.CreateTables;
import kz.kazniioir.oncoregistry.db.SQLStrings;

public class MainCreateTable {

    public static void main(String[] args) {
        CreateTables ct = new CreateTables();
       // ct.executeSql("show tables", Action.SELECT);
       // ct.dropTable("age");
        //ct.dropTable("morbiditysimple");
        //ct.dropTable("morbidityolap");
        
        
       // ct.dropTable("populationsimple");
        //ct.dropTable("population");
       // ct.dropTable("morbidityolap2100");
       // ct.createTable(SQLStrings.sqlCreatePopulationSimple);
       // ct.createTable(SQLStrings.sqlCreateMorbidityOlap2100);
        //ct.dropTable("stage");
        //ct.createTable(SQLStrings.sqlCreateStage);
        //ct.fillTable(SQLStrings.createInsertStringsToStageTable());
        

        
        //ct.deleteRowsInTable("morbiditysimple", null);
       // ct.deleteRowsInTable("morbidityolap", null);
       // ct.deleteRowsInTable("age", null);
        //ct.executeSql("show tables", Action.SELECT);
        //ct.deleteRowsInTable("age", null);

        //ct.executeSql("create table t3 (id int, name varchar(10))", Action.CREATE);


        //ct.createTable("create table t3 (id int, name varchar(10))");
        //ct.dropTable("t3");
        //ct.insertRowIntoTable("t3", "id, name", "3, '333'");
        //ct.updateRowsInTable("t3", "name = '2new'", "id=2");
        //ct.executeSql("select * from t3", Action.SELECT);
        ct.selectRowsFromTable("age", "id, name", "id<32");
        //System.out.printf("qqq %s sss", null);


        // ct.createTable(SQLStrings.sqlCreateLocalization);
        // ct.createTable(SQLStrings.sqlCreateRegion);
        // ct.createTable(SQLStrings.sqlCreateAges);
        //  ct.createTable(SQLStrings.sqlCreateSex);
        // ct.createTable(SQLStrings.sqlCreateMorbiditySimple);
        // ct.createTable(SQLStrings.sqlCreateMorbidityOlap);
        // ct.dropTable("drop table morbiditysimple2100");
        // ct.createTable(SQLStrings.sqlCreateMorbiditySimple2100);

        // ct.fillTable(SQLStrings.createInsertStringsToLocalozationTable());
        //ct.fillTable(SQLStrings.createInsertStringsToRegionTable());
       // ct.fillTable(SQLStrings.createInsertStringsToAgeTable());
        //  ct.fillTable(ct.createInsertStringsToSexTable());


    }
}
