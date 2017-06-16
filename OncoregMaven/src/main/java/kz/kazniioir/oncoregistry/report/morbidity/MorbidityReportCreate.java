package kz.kazniioir.oncoregistry.report.morbidity;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MorbidityReportCreate {

    MorbidityReport morbidityReport;

   

    public static MorbidityReport readXMLFile(int tableNo, VarRep varRep) {
        MorbidityReport morbidityReport = null;
        VariantReport variantReport = null;
        String tableName = null;
        String rowGeneralName = null;
        String query;
        int baseOfDevision = 0;
        int year = 0;
        //int coef = 7;
        int tableNoSendToXML = tableNo;

        switch (varRep) {
            case MORBIDITY:
                variantReport = new VariantReportMorbidity();
                break;
            case MORTALITY:
                variantReport = new VariantReportMortality();
                break;
        }
        year = variantReport.getYear(tableNo);
        tableNoSendToXML = variantReport.getNewTableN(tableNo);

        //if (tableNo >= 6 && tableNo <= 75) {
//        if (tableNo >= 5 && tableNo <= 54) {
//            year = getYear(tableNo, coef);
//            tableNoSendToXML = getNewTableN(tableNo, coef);
//        }


        try {

            File fXmlFile = new File(variantReport.getXmlFile());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("table");
            // System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                //System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    int numberFromXML = Integer.parseInt(eElement.getAttribute("number"));
                    if (numberFromXML == tableNoSendToXML) {
                        tableName = eElement.getElementsByTagName("name").item(0).getTextContent();
                        rowGeneralName = eElement.getElementsByTagName("rowname").item(0).getTextContent();
                        baseOfDevision = Integer.parseInt(eElement.getElementsByTagName("base").item(0).getTextContent());
                        query = eElement.getElementsByTagName("query").item(0).getTextContent();
                        if (year != 0) {
                            tableName = String.format(tableName, year);
                            query = String.format(query, year);
                        }
                        morbidityReport = new MorbidityReport(tableNo, tableName, query, rowGeneralName, baseOfDevision);
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return morbidityReport;
    }

    public static MorbidityReport createMorbidityReport(int tableNo, VarRep varRep) {
        return readXMLFile(tableNo, varRep);
    }

    public static int getNewTableN(int tableNo, int coef) {
//        if (tableNo < 6) {

        //int numAgetables = 5; // for morbidity
        int numAgetables = 4; // for mortality

        if (tableNo < 5) {
            return tableNo;
        }
        int res = 0;
        // final int coef = 7;
        int newNum = tableNo - numAgetables; // 14
        //System.out.println("newNum/coef=" + (newNum/coef));
        res = (newNum % coef);
        if (res == 0) {
            return coef + numAgetables;
        }
        return res + numAgetables;
    }

    public static int getYear(int tableNo, int coef) {
//        return 2003 + ((tableNo + 1) / coef);
        return 2003 + ((tableNo) / coef);
    }

    public static void main1(String[] args) {
//        for (int i = 6; i < 76; i++) {
//            int tN = i;
//            int res = getNewTableN(tN);
//            System.out.print(tN + "->" + res + "\t");
//            if ((res) % 12 == 0) {
//                System.out.println("");
//            }
//        }
    }

    public static void main(String[] args) {
        int tableN = 27;
        //     System.out.println(getYear(tableN));
    }
}
