package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import jxl.write.DateTime;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class App extends Application {

    private static Stage stage; // **Declare static Stage**

    private static Dimension d = new Dimension();

    private void setPrimaryStage(Stage stage) {
        App.stage = stage;
    }

    static public Stage getPrimaryStage() {
        return App.stage;
    }

    static public Dimension getDimension(){
        return d;
    }

    private DateFormat dateFormat;

    private Calendar date;

    private Calendar cal;

    @Override
    public void start(Stage primaryStage) throws Exception {
        setPrimaryStage(primaryStage);
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        d.setSize(width, height);
        verifyDate();

        String actualDate = dateFormat.format(date.getTime());
        String expiredDate = dateFormat.format(cal.getTime());

        try {
            Parent root = new FXMLLoader().load(getClass().getResource("/fxml/Login.fxml"));
            primaryStage.setScene(new Scene(root, 400, 300));
            //primaryStage.setMaximized(true);
            primaryStage.setTitle("ESTOQUE - VISOU JÓIAS");
            primaryStage.show();
            primaryStage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void verifyDate(){
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        date = Calendar.getInstance();
        cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, 9);
        cal.set(Calendar.DAY_OF_MONTH, 18);
    }
}