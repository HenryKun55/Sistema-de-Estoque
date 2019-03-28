package connection;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Db {

    Connection connection;

    public Db() throws IOException {
        connection =  Connect.connection();
        if(null == connection){
            System.out.println("Error in Connection");
            System.exit(1);
        }
        createDb();
    }

    public boolean isDbConnected(){
        try{
            return !connection.isClosed();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void createDb() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/db/database.sql")));
        ScriptRunner runner = new ScriptRunner(connection);
        runner.runScript(bufferedReader);
    }

}
