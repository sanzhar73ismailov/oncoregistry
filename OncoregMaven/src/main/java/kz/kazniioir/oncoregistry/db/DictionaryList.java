package kz.kazniioir.oncoregistry.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DictionaryList {

    private String name;
    private List<DictionaryOnco> listDic = new ArrayList<>();

    public DictionaryList(String name) {
        this.name = name;
        fillDic();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DictionaryOnco> getListDic() {
        return listDic;
    }

    private void fillDic() {
        java.sql.Connection con = null;
        java.sql.Statement st = null;
        java.sql.ResultSet rs = null;
        try {
            con = DatabaseConnectionPool.getInstance().getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select id, name from " + name);
            while (rs.next()) {
                listDic.add(new DictionaryOnco(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DictionaryList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseConnectionPool.getInstance().freeConnection(con);
            } catch (SQLException ex) {
                Logger.getLogger(DictionaryList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void main(String[] args) {
        DictionaryList dl = new DictionaryList("region");
        List<DictionaryOnco> l = dl.getListDic();
        for (DictionaryOnco dictionary : l) {
            System.out.println("dictionary = " + dictionary);
        }
    }
}
