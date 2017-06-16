/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.kazniioir.oncoregistry.main;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
//import javax.persistence.Query;
import kz.kazniioir.oncoregistry.db.HibernateUtil;
import kz.kazniioir.oncoregistry.domain.Age;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

/**
 *
 * @author admin
 */
public class TestCreateStandartMorbidity {
    public static void main(String[] args) {
        System.out.println("START main");
//        runQuery();
        runSQLQuery();
        System.out.println("END main");
    }

    public static void runQuery() {

        SessionFactory sess = HibernateUtil.getSessionFactory();
        Session session = sess.openSession();
        Transaction tr = session.beginTransaction();
      //  tr.begin();
        Query query = session.createQuery("from Age");
        List<Age> list = query.list();
        //tr.commit();
        for (Age localization : list) {
            System.out.println("mapAges.put(\"" + localization.getName() + "\", "
                    + localization.getId() + ");");
        }

    }
    
    public static void runSQLQuery(){
        System.out.println("START");
//        String PERSISTENCE_UNIT_NAME = "";
//         EntityManagerFactory factory = 
//    Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
         SessionFactory sessFactory = HibernateUtil.getSessionFactory();
        Session session = sessFactory.openSession();
        SQLQuery query = session.createSQLQuery("select * from age");
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Object> list = query.list();
        //tr.commit();
        for (Object  obj: list) {
            //System.out.println("mapAges.put(\"" + localization.getName() + "\", "
                  //  + localization.getId() + ");");
                  Map row = (Map)obj;
            System.out.println("obj = " + row);
        }
         session.clear();
        session.close();
       
        System.out.println("END");
         /*
        org.hibernate.
        SessionFactory sessFactory = HibernateUtil.
        Session session = sessFactory.openSession()
        session.createQuery(string)
        Query q = em.createNativeQuery("SELECT a.firstname, a.lastname FROM Author a WHERE a.id = :id");
*/
    }
}
