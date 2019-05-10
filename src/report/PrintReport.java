package report;

import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import connection.Connect;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

public class PrintReport extends JFrame {

    /**
     *
     */
    private Connection connection;
    private static final long serialVersionUID = 1L;
    private boolean positive;
    private List<String> reports = new ArrayList<String>();
    private int positionReport;
    private HashMap map = new HashMap();

    public void showReport(Dimension screenSize) throws JRException {
        connection = Connect.connection();
        addReports();

        //JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResource("/jasper/"+reports.get(positionReport)).getFile()); //PARA INTELLIJ
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("/jasper/"+reports.get(positionReport))); // PARA BUILD
        if(positive){
            JRDesignQuery query = new JRDesignQuery();
            query.setText("SELECT P.id,\n" +
                    "P.product_name,\n" +
                    "P.reference,\n" +
                    "U.name as unit_name,\n" +
                    "C.name as category_name,\n" +
                    "P.storage\n" +
                    "FROM PRODUCTS P\n" +
                    "INNER JOIN CATEGORY C on P.category_id = C.id\n" +
                    "INNER JOIN UNITS U on P.unit_id = U.id\n" +
                    "WHERE C.name = $P{categoria} AND P.storage > 0");
            jasperDesign.setQuery(query);
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        JasperPrint print = JasperFillManager.fillReport(jasperReport, map, connection);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        this.add(viewer);
        this.setSize(screenSize);
        this.setVisible(true);
        this.toFront();
        this.repaint();
    }

    private PrintReport addReports(){
        reports.add("reportInventario.jrxml");// 0
        reports.add("reportEntrada.jrxml");// 1
        reports.add("reportSaida.jrxml");// 2
        reports.add("reportCategory.jrxml");// 3
        return this;
    }

    public PrintReport addMap(HashMap map){
        this.map = map;
        return this;
    }

    public int getPositionReport() {
        return positionReport;
    }

    public PrintReport setPositionReport(int positionReport) {
        this.positionReport = positionReport;
        return this;
    }

    public PrintReport setPositiveCategory(boolean positive){
        this.positive = positive;
        return this;
    }
}