package dao;

import connection.Connect;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Saida;
import model.SaidaProdutos;

import java.sql.*;

public class OutputDAO {

    private Connection connection;

    public OutputDAO(){
        connection = Connect.connection();
    }

    public boolean save(int id, String id_cliente, SaidaProdutos s){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO outputs VALUES (NULL, ?, ?, ?, ?, DATETIME('now', 'localtime'))");
            ps.setInt(1, id);
            ps.setString(2, s.idProdutoProperty().getValue());
            ps.setString(3, id_cliente);
            ps.setString(4, s.quantidadeProperty().getValue());
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
            PreparedStatement ps = connection.prepareStatement("UPDATE products SET storage = (select sum((SELECT storage FROM products WHERE id = ?) - ?)), storage_date_out = DATETIME('now', 'localtime') WHERE id = ?");
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

    public ObservableList<Saida> getAllSaidas() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM outputs GROUP BY id_saida");
            ObservableList<Saida> saidas = FXCollections.observableArrayList();
            while(rs.next())
            {
                Saida s = extractUserFromResultSet(rs);
                saidas.add(s);
            }
            stmt.close();
            rs.close();
            return saidas;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ObservableList<Saida> findbyId(StringProperty id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM outputs WHERE id LIKE ?");
            ps.setString(1,"%"+id.getValue());
            ResultSet rs = ps.executeQuery();
            ObservableList<Saida> saidas = FXCollections.observableArrayList();
            while(rs.next())
            {
                Saida s = extractUserFromResultSet(rs);
                saidas.add(s);
            }
            ps.close();
            rs.close();
            return saidas;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ObservableList<Saida> findbyProductId(StringProperty name) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM outputs WHERE product_id LIKE ?");
            ps.setString(1,"%"+name.getValue()+"%");
            ResultSet rs = ps.executeQuery();
            ObservableList<Saida> saidas = FXCollections.observableArrayList();
            while(rs.next())
            {
                Saida s = extractUserFromResultSet(rs);
                saidas.add(s);
            }
            ps.close();
            rs.close();
            return saidas;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Saida extractUserFromResultSet(ResultSet rs) throws SQLException {
        Saida s = new Saida();
        s.setId( new SimpleStringProperty(rs.getString("id")));
        s.setId_saida(new SimpleStringProperty(rs.getString("id_saida")));
        s.setProduct_id( new SimpleStringProperty(rs.getString("product_id")));
        s.setClient_id( new SimpleStringProperty(rs.getString("client_id")));
        s.setStorage_out( new SimpleStringProperty(rs.getString("storage_out")));
        s.setDate_out( new SimpleStringProperty(rs.getString("created_at")));
        return s;
    }
}
