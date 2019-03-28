package connection;

import java.io.File;
import java.sql.*;

public class Connect {

    private static Connection conn = null;

    public static Connection connection() {
        String url = "jdbc:sqlite:C:\\Temp\\Estoque\\database.db";
        try {
            if(criarDiretorio()) {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection(url);
                return conn;
            }
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            return conn;
        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }

    public static boolean criarDiretorio() {
        File theDir = new File("C:\\Temp\\Estoque");
        boolean result = false;

        // if the directory does not exist, create it
        if (!theDir.exists()) {
            System.out.println("creating directory: " + theDir.getName());

            try{
                theDir.mkdirs();
                result = true;
            }
            catch(SecurityException se){

            }
        }
        return result;
    }
}
