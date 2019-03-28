package dao;

import com.ibm.icu.impl.UCharacterName;
import connection.Connect;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Categoria;
import model.Unidade;

import java.sql.*;

public class UnidadeDAO {

    Connection connection;

    public UnidadeDAO(){
        connection = Connect.connection();
    }

    public boolean save(Unidade c){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO units (name, created_at) VALUES (?, DATETIME('now', 'localtime'))");
            ps.setString(1, c.nomeProperty().getValue());
            int i = ps.executeUpdate();
            if(i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public ObservableList<Unidade> getAllUnits() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM units");
            ObservableList<Unidade> unidades = FXCollections.observableArrayList();
            while(rs.next())
            {
                Unidade u = extractUserFromResultSet(rs);
                unidades.add(u);
            }
            stmt.close();
            rs.close();
            return unidades;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ObservableList<Unidade> findbyId(StringProperty id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM unit WHERE id LIKE ?");
            ps.setString(1,"%"+id.getValue());
            ResultSet rs = ps.executeQuery();
            ObservableList<Unidade> unidades = FXCollections.observableArrayList();
            while(rs.next())
            {
                Unidade u = extractUserFromResultSet(rs);
                unidades.add(u);
            }
            rs.close();
            ps.close();
            return unidades;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ObservableList<Unidade> findbyName(StringProperty name) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM units WHERE name LIKE ?");
            ps.setString(1,"%"+name.getValue()+"%");
            ResultSet rs = ps.executeQuery();
            ObservableList<Unidade> unidades = FXCollections.observableArrayList();
            while(rs.next())
            {
                Unidade u = extractUserFromResultSet(rs);
                unidades.add(u);
            }
            rs.close();
            ps.close();
            return unidades;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean updateName(String name, String id) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE units SET name = ? WHERE id = ?");
            ps.setString(1, name);
            ps.setString(2, id);
            int i = ps.executeUpdate();
            if(i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Unidade checkId(StringProperty id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM units WHERE id = ?");
            ps.setString(1, id.getValue());
            ResultSet rs = ps.executeQuery();
            Unidade u = null;
            while(rs.next()) {
                u = extractUserFromResultSet(rs);
            }
            ps.close();
            rs.close();
            return u;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Unidade extractUserFromResultSet(ResultSet rs) throws SQLException {
        Unidade u = new Unidade();
        u.setId( new SimpleStringProperty(rs.getString("id")));
        u.setNome( new SimpleStringProperty(rs.getString("name")));
        u.setDate_in(new SimpleStringProperty(rs.getString("created_at")));
        return u;
    }
}
