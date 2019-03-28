package dao;

import connection.Connect;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Entrada;
import model.EntradaProdutos;

import java.sql.*;

public class InputDAO {

    private Connection connection;

    public InputDAO(){
        connection = Connect.connection();
    }

    public boolean save(int id, EntradaProdutos e){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO inputs VALUES (NULL, ?, ?, ?, DATETIME('now', 'localtime'))");
            ps.setInt(1, id);
            ps.setString(2, e.idProdutoProperty().getValue());
            ps.setString(3, e.quantidadeProperty().getValue());
            int i = ps.executeUpdate();
            if(i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateStorage(String product_id, String quantidade){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE products SET storage = (select sum((SELECT storage FROM products WHERE id = ?) + ?)), storage_date_in = DATETIME('now', 'localtime') WHERE id = ?");
            ps.setString(1, product_id);
            ps.setString(2, quantidade);
            ps.setString(3, product_id);
            int i = ps.executeUpdate();
            if(i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public ObservableList<Entrada> getAllEntradas() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM inputs GROUP BY id_entrada");
            ObservableList<Entrada> entradas = FXCollections.observableArrayList();
            while(rs.next())
            {
                Entrada e = extractUserFromResultSet(rs);
                entradas.add(e);
            }
            stmt.close();
            rs.close();
            return entradas;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ObservableList<Entrada> findbyId(StringProperty id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM inputs WHERE id LIKE ?");
            ps.setString(1,"%"+id.getValue());
            ResultSet rs = ps.executeQuery();
            ObservableList<Entrada> entradas = FXCollections.observableArrayList();
            while(rs.next())
            {
                Entrada e = extractUserFromResultSet(rs);
                entradas.add(e);
            }
            ps.close();
            rs.close();
            return entradas;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ObservableList<Entrada> findbyProductId(StringProperty name) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM inputs WHERE product_id LIKE ?");
            ps.setString(1,"%"+name.getValue()+"%");
            ResultSet rs = ps.executeQuery();
            ObservableList<Entrada> entradas = FXCollections.observableArrayList();
            while(rs.next())
            {
                Entrada e = extractUserFromResultSet(rs);
                entradas.add(e);
            }
            ps.close();
            rs.close();
            return entradas;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Entrada extractUserFromResultSet(ResultSet rs) throws SQLException {
        Entrada e = new Entrada();
        e.setId( new SimpleStringProperty(rs.getString("id")));
        e.setId_entrada(new SimpleStringProperty(rs.getString("id_entrada")));
        e.setProduct_id( new SimpleStringProperty(rs.getString("product_id")));
        e.setStorage_in( new SimpleStringProperty(rs.getString("storage_in")));
        e.setDate_in( new SimpleStringProperty(rs.getString("created_at")));
        return e;
    }
}
