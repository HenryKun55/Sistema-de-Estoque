package dao;

import connection.Connect;
import connection.Db;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Cliente;
import model.Unidade;

import java.sql.*;

public class ClientsDAO {

    Connection connection;

    public ClientsDAO(){
        connection = Connect.connection();
    }

    public boolean save(Cliente c){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO clients (name, created_at, updated_at) VALUES (?, DATETIME('now', 'localtime'), DATETIME('now', 'localtime'))");
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

    public ObservableList<Cliente> getAllClients() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM clients");
            ObservableList<Cliente> clientes = FXCollections.observableArrayList();
            while(rs.next())
            {
                Cliente c = extractClientFromResultSet(rs);
                clientes.add(c);
            }
            stmt.close();
            rs.close();
            return clientes;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ObservableList<Cliente> findbyId(StringProperty id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM clients WHERE id LIKE ?");
            ps.setString(1,"%"+id.getValue());
            ResultSet rs = ps.executeQuery();
            ObservableList<Cliente> clientes = FXCollections.observableArrayList();
            while(rs.next())
            {
                Cliente c = extractClientFromResultSet(rs);
                clientes.add(c);
            }
            rs.close();
            ps.close();
            return clientes;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ObservableList<Cliente> findbyName(StringProperty name) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM clients WHERE name LIKE ?");
            ps.setString(1,"%"+name.getValue()+"%");
            ResultSet rs = ps.executeQuery();
            ObservableList<Cliente> clientes = FXCollections.observableArrayList();
            while(rs.next())
            {
                Cliente c = extractClientFromResultSet(rs);
                clientes.add(c);
            }
            rs.close();
            ps.close();
            return clientes;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean updateName(String name, String id) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE clients SET name = ? WHERE id = ?");
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

    public Cliente checkId(StringProperty id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM clients WHERE id = ?");
            ps.setString(1, id.getValue());
            ResultSet rs = ps.executeQuery();
            Cliente c = null;
            while(rs.next()) {
                c = extractClientFromResultSet(rs);
            }
            ps.close();
            rs.close();
            return c;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Cliente extractClientFromResultSet(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId( new SimpleStringProperty(rs.getString("id")) );
        c.setNome( new SimpleStringProperty(rs.getString("name")));
        c.setDate_in(new SimpleStringProperty(rs.getString("created_at")));
        c.setModify_date(new SimpleStringProperty(rs.getString("updated_at")));
        return c;
    }
}
