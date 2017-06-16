package kz.kazniioir.oncoregistry.generalViewExcell;

import java.util.Vector;
import kz.kazniioir.oncoregistry.db.DatabaseConnectionPool;
import kz.kazniioir.oncoregistry.db.DbConnection;

public class InsertData {

    Vector<Vector<Object>> data;

    public InsertData(Vector<Vector<Object>> data) {
        this.data = data;
    }
    
    private java.sql.Connection getConnection(){
          java.sql.Connection con = null;
           con = DbConnection.getConnection("localhost", "3306", "kras", "root", "");
           return con;
    }

    public void insertDataToPatientKras() {

        java.sql.Connection con = null;
        java.sql.Statement st = null;
        String insertQuery = null;
        try {

            //con = DbConnection.getConnection("kras");
            con = getConnection();
            st = con.createStatement();
            for (Vector<Object> vector : data) {
                insertQuery = "INSERT INTO \n"
                        + "  kras_patient\n"
                        + "(\n"
                        + "  id,\n"
                        + "  last_name,\n"
                        + "  first_name,\n"
                        + "  patronymic_name,\n"
                        + "  sex_id,\n"
                        + "  date_birth,\n"
                        + "  year_birth,\n"
                        + "  weight_kg,\n"
                        + "  height_sm,\n"
                        + "  prof_or_other_hazards_yes_no_id,\n"
                        + "  prof_or_other_hazards_discr,\n"
                        + "  nationality_id,\n"
                        + "  smoke_yes_no_id,\n"
                        + "  smoke_discr,\n"
                        + "  hospital,\n"
                        + "  doctor,\n"
                        + "  coments,\n"
                        + "  user\n"
                        + ") \n"
                        + "VALUE (";
                if (vector.elementAt(0).toString().equals("id")) {
                    continue;
                }
                for (int i = 0; i < vector.size(); i++) {

                    if (vector.elementAt(i) instanceof Double) {
                        int valueInt = ((Double) vector.elementAt(i)).intValue();
                        //System.out.println("valueInt = " + valueInt);
                        insertQuery += "'" + valueInt + "'";
                    } else {
                        String valueStr = (String) vector.elementAt(i);
                        if (valueStr.isEmpty()) {
                            insertQuery += "null";
                        } else {
                            insertQuery += "'" + valueStr + "'";
                        }
                    }
                    if (i < (vector.size() - 1)) {
                        insertQuery += ", ";
                    }

                }
                insertQuery += ")";

                System.out.println(insertQuery);
                System.out.println(st.executeUpdate(insertQuery));
                //st.addBatch(insertQuery);
            }
            //st.executeBatch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void insertDataToInvestKras() {

        java.sql.Connection con = null;
        java.sql.Statement st = null;
        String insertQuery = null;
        try {

            con =  con = getConnection();
            st = con.createStatement();
            for (Vector<Object> vector : data) {
                insertQuery = "INSERT INTO \n"
                        + "  kras_investigation\n"
                        + "(\n"
                        + "  id,\n"
                        + "  patient_id,\n"
                        + "  tumor_another_existence_yes_no_id,\n"
                        + "  tumor_another_existence_discr,\n"
                        + "  diagnosis,\n"
                        + "  intestinum_crassum_part_id,\n"
                        + "  colon_part_id,\n"
                        + "  rectum_part_id,\n"
                        + "  treatment_discr,\n"
                        + "  status_gene_kras_id,\n"
                        + "  date_invest,\n"
                        + "  depth_of_invasion_id,\n"
                        + "  stage_id,\n"
                        + "  metastasis_regional_lymph_nodes_yes_no_id,\n"
                        + "  metastasis_regional_lymph_nodes_discr,\n"
                        + "  tumor_histological_type_id,\n"
                        + "  tumor_differentiation_degree_id,\n"
                        + "  block,\n"
                        + "  user\n"
                        + ") \n"
                        + "VALUE (";
                if (vector.elementAt(0).toString().equals("id")) {
                    continue;
                }
                for (int i = 0; i < vector.size(); i++) {

                    if (vector.elementAt(i) instanceof Double) {
                        int valueInt = ((Double) vector.elementAt(i)).intValue();
                        //System.out.println("valueInt = " + valueInt);
                        insertQuery += "'" + valueInt + "'";
                    } else {
                        String valueStr = (String) vector.elementAt(i);
                        if (valueStr.isEmpty()) {
                            insertQuery += "null";
                        } else {
                            insertQuery += "'" + valueStr + "'";
                        }
                    }
                    if (i < (vector.size() - 1)) {
                        insertQuery += ", ";
                    }

                }
                insertQuery += ")";

                System.out.println(insertQuery);
                System.out.println(st.executeUpdate(insertQuery));
                //st.addBatch(insertQuery);
            }
            //st.executeBatch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
