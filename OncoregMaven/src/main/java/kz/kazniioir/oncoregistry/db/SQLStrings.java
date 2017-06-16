package kz.kazniioir.oncoregistry.db;

public class SQLStrings {

    public static final String sqlCreateStage = "CREATE TABLE stage "
            + "(id INTEGER not NULL, "
            + " name VARCHAR(50), "
            + " PRIMARY KEY ( id ))";
    public static final String sqlCreateLocalization = "CREATE TABLE localization "
            + "(id INTEGER not NULL, "
            + " codeICD VARCHAR(20), "
            + " name VARCHAR(100), "
            + " sex_spec INTEGER DEFAULT '0' COMMENT '0-both sex, 1-male only, 2-female only', "
            + " select_for_report INTEGER DEFAULT '1' COMMENT '1 - if should be selected in reports', "
            + " PRIMARY KEY ( id ))";
    public static final String sqlCreateRegion = "CREATE TABLE region "
            + "(id INTEGER not NULL, "
            + " name VARCHAR(100), "
            + " PRIMARY KEY ( id ))";
    public static final String sqlCreateAges = "CREATE TABLE age "
            + "(id INTEGER not NULL, "
            + " name VARCHAR(100), "
            + " PRIMARY KEY ( id ))";
    public static final String sqlCreateSex = "CREATE TABLE sex "
            + "(id INTEGER not NULL, "
            + " name VARCHAR(100), "
            + " PRIMARY KEY ( id ))";
    public static final String sqlCreateMorbidityOlap = "CREATE TABLE morbidityOlap  "
            + "(id INTEGER not NULL  AUTO_INCREMENT, "
            + " region_id INTEGER, "
            + " year INTEGER, "
            + " localization_id INTEGER, "
            + " sex_id INTEGER, "
            + " age_id INTEGER, "
            + " filename VARCHAR(100), "
            + " morbidity INTEGER, "
            + " PRIMARY KEY (id),"
            + " FOREIGN KEY (region_id) REFERENCES region (id),"
            + " FOREIGN KEY (localization_id) REFERENCES localization (id),"
            + " FOREIGN KEY (sex_id) REFERENCES sex (id),"
            + " FOREIGN KEY (age_id) REFERENCES age (id),"
            + " UNIQUE KEY complex (region_id, year, localization_id, sex_id, age_id)"
            + ")";
    public static final String sqlCreateMorbiditySimple = "CREATE TABLE morbiditySimple  "
            + "(id INTEGER not NULL  AUTO_INCREMENT, "
            + " region_id INTEGER, "
            + " year INTEGER, "
            + " localization_id INTEGER, "
            + " sex_id INTEGER, "
            + " filename VARCHAR(100), "
            + " ageall INTEGER, "
            + " age0_4 INTEGER, "
            + " age5_9 INTEGER, "
            + " age10_14 INTEGER, "
            + " age15_17 INTEGER, "
            + " age18_19 INTEGER, "
            + " age15_19 INTEGER, "
            + " age20_24 INTEGER, "
            + " age25_29 INTEGER, "
            + " age15_29 INTEGER, "
            + " age30_34 INTEGER, "
            + " age35_39 INTEGER, "
            + " age40_44 INTEGER, "
            + " age45_49 INTEGER, "
            + " age50_54 INTEGER, "
            + " age55_59 INTEGER, "
            + " age60_64 INTEGER, "
            + " age65_69 INTEGER, "
            + " age70_74 INTEGER, "
            + " age75_79 INTEGER, "
            + " age80_84 INTEGER, "
            + " age70 INTEGER, "
            + " age85 INTEGER, "
            + " PRIMARY KEY (id),"
            + " FOREIGN KEY (region_id) REFERENCES region (id),"
            + " FOREIGN KEY (localization_id) REFERENCES localization (id),"
            + " FOREIGN KEY (sex_id) REFERENCES sex (id),"
            + " UNIQUE KEY complex (region_id, year, localization_id, sex_id)"
            + ")";
    public static final String sqlCreateMorbiditySimple2100 = "CREATE TABLE morbiditySimple2100  "
            + "(id INTEGER not NULL  AUTO_INCREMENT, "
            + " region_id INTEGER, "
            + " year INTEGER, "
            + " localization_id INTEGER, "
            + " filename VARCHAR(100), "
            + " younger14Years INTEGER, "
            + "registeredInTheStartOfYear INTEGER, "
            + "vzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis INTEGER, "
            + "vzyatoNaUchetThisYear_FirstTime INTEGER, "
            + "from3Group_DetectedDuringProfAll INTEGER, "
            + "from3Group_DetectedDuringProf01And02Stage INTEGER, "
            + "from3Group_DiagnosoConfirmedMorfologically INTEGER, "
            + "from3Group_HadStage1and2 INTEGER, "
            + "from3Group_HadStage3 INTEGER, "
            + "from3Group_HadStage4 INTEGER, "
            + "struckOffTheRegisterAll INTEGER, "
            + "struckOffTheRegisterGoOut INTEGER, "
            + "struckOffTheRegisterDiagNotConfirmed INTEGER, "
            + "struckOffTheRegisterNoInfo INTEGER, "
            + "struckOffTheRegisterWithDiagBazalioma INTEGER, "
            + "struckOffTheRegisterDiedFromAnotherDisease INTEGER, "
            + "registeredInTheEndOfYearAll INTEGER, "
            + "registeredInTheEndOfYear5YearsAndMore INTEGER, "
            + " PRIMARY KEY (id),"
            + " FOREIGN KEY (region_id) REFERENCES region (id),"
            + " FOREIGN KEY (localization_id) REFERENCES localization (id),"
            + " UNIQUE KEY complex (region_id, year, localization_id, younger14Years)"
            + ")";
    public static final String sqlCreateMorbidityOlap2100 = "CREATE TABLE morbidityolap2100 (\n"
            + "  id INTEGER(11) NOT NULL AUTO_INCREMENT,\n"
            + "  region_id INTEGER(11) DEFAULT NULL,\n"
            + "  year INTEGER(11) DEFAULT NULL,\n"
            + "  localization_id INTEGER(11) DEFAULT NULL,\n"
            + "  stage_id INTEGER(11) DEFAULT NULL,\n"
            + "  value INTEGER(11) DEFAULT NULL,\n"
            + "  PRIMARY KEY (id),\n"
            + "  UNIQUE KEY complex (region_id, year, localization_id, stage_id),\n"
            + "  KEY region_id (region_id),\n"
            + "  KEY localization_id (localization_id),\n"
            + "  KEY stage_id (stage_id),\n"
            + "  FOREIGN KEY (region_id) REFERENCES region (id),\n"
            + "  FOREIGN KEY (localization_id) REFERENCES localization (id),\n"
            + "  FOREIGN KEY (stage_id) REFERENCES stage (id))";
    public static final String sqlCreatePopulationSimple = "CREATE TABLE populationsimple (\n"
            + "  id INTEGER(11) NOT NULL AUTO_INCREMENT,\n"
            + "  year INTEGER(11) NOT NULL,\n"
            + "  region_id INTEGER(11) NOT NULL,\n"
            + "  age_id INTEGER(11) NOT NULL,\n"
            + "  value_all_sex_both INTEGER(11) NOT NULL,\n"
            + "  value_all_sex_male INTEGER(11) NOT NULL,\n"
            + "  value_all_sex_female INTEGER(11) NOT NULL,\n"
            + "  value_city_sex_both INTEGER(11) NOT NULL,\n"
            + "  value_city_sex_male INTEGER(11) NOT NULL,\n"
            + "  value_city_sex_female INTEGER(11) NOT NULL,\n"
            + "  value_village_sex_both INTEGER(11) NOT NULL,\n"
            + "  value_village_sex_male INTEGER(11) NOT NULL,\n"
            + "  value_village_sex_female INTEGER(11) NOT NULL,\n"
            + "  PRIMARY KEY (id),\n"
            + "  UNIQUE KEY year (year, region_id, age_id),\n"
            + "  KEY region_id (region_id),\n"
            + "  KEY age_id (age_id),\n"
            + "  FOREIGN KEY (region_id) REFERENCES region (id),\n"
            + "  FOREIGN KEY (age_id) REFERENCES age (id))";
    public static final String sqlCreatePopulationOlap = "CREATE TABLE population (\n"
            + "  id INTEGER(11) NOT NULL AUTO_INCREMENT,\n"
            + "  year INTEGER(11) NOT NULL,\n"
            + "  region_id INTEGER(11) NOT NULL,\n"
            + "  sex_id INTEGER(11) NOT NULL,\n"
            + "  age_id INTEGER(11) NOT NULL,\n"
            + "  region_type_id INTEGER(11) NOT NULL,\n"
            + "  value INTEGER(11) NOT NULL,\n"
            + "  PRIMARY KEY (id),\n"
            + "  UNIQUE KEY year (year, region_id, sex_id, age_id, region_type_id),\n"
            + "  KEY region_id (region_id),\n"
            + "  KEY sex_id (sex_id),\n"
            + "  KEY age_id (age_id),\n"
            + "  KEY region_type_id (region_type_id),\n"
            + "  FOREIGN KEY (region_id) REFERENCES region (id),\n"
            + "  FOREIGN KEY (sex_id) REFERENCES sex (id),\n"
            + "  FOREIGN KEY (region_type_id) REFERENCES region_type (id),\n"
            + "  FOREIGN KEY (age_id) REFERENCES age (id))\n";

    public static String[] createInsertStringsToLocalozationTable() {

        return new String[]{
            "INSERT INTO localization VALUES (1,'С00-С97','Все злокачественные новообразования,  в том числе', 0, 1)",
            "INSERT INTO localization VALUES (2,'С00','губы', 0, 1)",
            "INSERT INTO localization VALUES (3,'С01-С14,С46.2','языка, полости рта и глотки', 0, 1)",
            "INSERT INTO localization VALUES (4,'С15','пищевода', 0, 1)",
            "INSERT INTO localization VALUES (5,'С16','желудка', 0, 1)",
            "INSERT INTO localization VALUES (6,'С18','ободочной кишки', 0, 1)",
            "INSERT INTO localization VALUES (7,'С19-С21','прямой кишки, ректосигмоидного соединения, ануса', 0, 1)",
            "INSERT INTO localization VALUES (8,'С22','печени и внутрипеченочных  желчных протоков', 0, 1)",
            "INSERT INTO localization VALUES (9,'С25','поджелудочной железы', 0, 1)",
            "INSERT INTO localization VALUES (10,'С32','гортани', 0, 1)",
            "INSERT INTO localization VALUES (11,'С33-С34','трахеи, бронхов, легкого', 0, 1)",
            "INSERT INTO localization VALUES (12,'С40-С41','костей и суставных хрящей', 0, 1)",
            "INSERT INTO localization VALUES (13,'С43','меланома кожи', 0, 1)",
            "INSERT INTO localization VALUES (14,'С44,С46','другие новообразования кожи', 0, 1)",
            "INSERT INTO localization VALUES (15,'С45,С46.1,С47,С49','соединительной и других мягких тканей', 0, 1)",
            "INSERT INTO localization VALUES (16,'С50','женской молочной железы', 2, 1)",
            "INSERT INTO localization VALUES (17,'С53','шейки матки', 2, 1)",
            "INSERT INTO localization VALUES (18,'С54','тела матки', 2, 1)",
            "INSERT INTO localization VALUES (19,'С56','яичника', 2, 1)",
            "INSERT INTO localization VALUES (21,'С61','предстательной железы', 1, 1)",
            "INSERT INTO localization VALUES (22,'С62','яичка', 1, 1)",
            "INSERT INTO localization VALUES (20,'С60','полового члена', 1, 0)",
            "INSERT INTO localization VALUES (23,'С64','почки', 0, 1)",
            "INSERT INTO localization VALUES (24,'С67','мочевого пузыря', 0, 1)",
            "INSERT INTO localization VALUES (25,'С69','глаза и его придаточного аппарата', 0, 1)",
            "INSERT INTO localization VALUES (26,'С70-С72','головного мозга и центральной нервной системы', 0, 1)",
            "INSERT INTO localization VALUES (27,'С73','щитовидной железы', 0, 1)",
            "INSERT INTO localization VALUES (28,'С81-С96','лимфоидной и кроветворной тканей, из них,', 0, 1)",
            "INSERT INTO localization VALUES (29,'С82-С85','лимфосаркомы (неходжкинская и Т-клеточная) и другие лимфомы', 0, 0)",
            "INSERT INTO localization VALUES (30,'С81','лимфогранулематоз', 0, 0)",
            "INSERT INTO localization VALUES (31,'С91.0','острый лимфолейкоз', 0, 0)",
            "INSERT INTO localization VALUES (32,'С91.1-9','другие лимфолейкозы (хронический, подострый и другие)', 0, 0)",
            "INSERT INTO localization VALUES (33,'С92.0','острый миелолейкоз', 0, 0)",
            "INSERT INTO localization VALUES (34,'С92.1-9','другие миелолейкозы (хронический, подострый и другие)', 0, 0)",
            "INSERT INTO localization VALUES (35,'С93-С95','другие лейкозы', 0, 0)",
            "INSERT INTO localization VALUES (36,'С88,С90,С96','другие гемобластозы', 0, 0)",
            "INSERT INTO localization VALUES (41,'C81-C90,C96','злокачественные лимфомы', 0, 0)",
            "INSERT INTO localization VALUES (42,'С91-С96','лейкемии', 0, 0)",
            "INSERT INTO localization VALUES (50,null,'прочие злокачественные новообразования', 0, 1)"
        };

    }

    public static String[] createInsertStringsToRegionTable() {
        String[] regions = {
            "г. Астана",
            "г. Алматы",
            "Акмолинская область",
            "Актюбинская область",
            "Алматинская область",
            "Атырауская область",
            "Восточно-Казахстанская область",
            "Жамбыльская область",
            "Западно-Казахстанская область",
            "Карагандинская область",
            "Костанайская область",
            "Кызылординская область",
            "Мангыстауская область",
            "Павлодарская область",
            "Северно-Казахстанская область",
            "Южно-Казахстанская область",
            "Республика Казахстан"
        };
        String[] insertRows = new String[regions.length];
        for (int i = 0; i < regions.length; i++) {
            insertRows[i] = String.format("INSERT INTO REGION VALUES ('%s', '%s')", (i + 1), regions[i]);
        }
        return insertRows;
    }

    public static String[] createInsertStringsToAgeTable() {
        String[] ages = {
            "все возрасты",
            "0-4",
            "5-9",
            "10-14",
            "15-17",
            "18-19",
            "15-19",
            "20-24",
            "25-29",
            "15-29",
            "30-34",
            "35-39",
            "40-44",
            "45-49",
            "50-54",
            "55-59",
            "60-64",
            "65-69",
            "70-74",
            "75-79",
            "80-84",
            "70 +",
            "85 +"
        };
        String[] insertRows = new String[ages.length];
        for (int i = 0; i < ages.length; i++) {
            insertRows[i] = String.format("INSERT INTO AGE VALUES ('%s', '%s')", ((i + 1) * 10), ages[i]);
        }
        return insertRows;
    }

    public static String[] createInsertStringsToSexTable() {
        String[] sexes = {"м", "ж"};
        String[] insertRows = new String[sexes.length];
        for (int i = 0; i < sexes.length; i++) {
            insertRows[i] = String.format("INSERT INTO SEX VALUES ('%s', '%s')", (i + 1), sexes[i]);
        }
        return insertRows;
    }

    public static String[] createInsertStringsToStageTable() {

        return new String[]{
            "INSERT INTO stage VALUES (1,'I-II')",
            "INSERT INTO stage VALUES (2,'III')",
            "INSERT INTO stage VALUES (3,'IV')",
            "INSERT INTO stage VALUES (4,'не стадируется')",
            "INSERT INTO stage VALUES (5,'все')",};

    }
}
