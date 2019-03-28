package dao;

import connection.Connect;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Categoria;

import java.sql.*;

public class CategoriaDAO {

    Connection connection;

    public CategoriaDAO(){
        connection = Connect.connection();
    }

    public boolean save(Categoria c){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO category (name, created_at) VALUES (?, DATETIME('now', 'localtime'))");
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

    public ObservableList<Categoria> getAllCategorys() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM category");
            ObservableList<Categoria> categorias = FXCollections.observableArrayList();
            while(rs.next())
            {
                Categoria c = extractUserFromResultSet(rs);
                categorias.add(c);
            }
            stmt.close();
            rs.close();
            return categorias;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ObservableList<Categoria> findbyId(StringProperty id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM category WHERE id LIKE ?");
            ps.setString(1,"%"+id.getValue());
            ResultSet rs = ps.executeQuery();
            ObservableList<Categoria> categorias = FXCollections.observableArrayList();
            while(rs.next())
            {
                Categoria c = extractUserFromResultSet(rs);
                categorias.add(c);
            }
            rs.close();
            ps.close();
            return categorias;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ObservableList<Categoria> findbyName(StringProperty name) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM category WHERE name LIKE ?");
            ps.setString(1,"%"+name.getValue()+"%");
            ResultSet rs = ps.executeQuery();
            ObservableList<Categoria> categorias = FXCollections.observableArrayList();
            while(rs.next())
            {
                Categoria c = extractUserFromResultSet(rs);
                categorias.add(c);
            }
            rs.close();
            ps.close();
            return categorias;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Categoria checkId(StringProperty id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM category WHERE id = ?");
            ps.setString(1, id.getValue());
            ResultSet rs = ps.executeQuery();
            Categoria c = null;
            while(rs.next()) {
                c = extractUserFromResultSet(rs);
            }
            ps.close();
            rs.close();
            return c;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean updateName(String name, String id) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE category SET name = ? WHERE id = ?");
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

    private Categoria extractUserFromResultSet(ResultSet rs) throws SQLException {
        Categoria c = new Categoria();
        c.setId( new SimpleStringProperty(rs.getString("id")));
        c.setNome( new SimpleStringProperty(rs.getString("name")));
        c.setDate_in(new SimpleStringProperty(rs.getString("created_at")));
        return c;
    }
}
