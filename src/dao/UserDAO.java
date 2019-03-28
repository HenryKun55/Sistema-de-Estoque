package dao;

import connection.Connect;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Cliente;
import model.Usuario;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UserDAO {

    Connection connection;

    public UserDAO(){
        connection = Connect.connection();
    }

    public boolean save(Usuario u){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?, DATETIME('now', 'localtime'), DATETIME('now', 'localtime'))");
            ps.setString(1, u.nameProperty().getValue());
            ps.setString(2, u.passwordProperty().getValue());
            int i = ps.executeUpdate();
            if(i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Usuario checkId(String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT id, password FROM users WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            Usuario u = null;
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

    public boolean checkName(String name) {
        try {
            boolean i = false;
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                i = true;
            }
            ps.close();
            rs.close();
            return i;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static String criptografaSenha(String original)
    {
        String senha = null;
        MessageDigest algoritmo;
        byte messageDigest[];
        StringBuilder hexString;
        try {
            algoritmo = MessageDigest.getInstance("SHA-256");
            messageDigest = algoritmo.digest(original.getBytes("UTF-8"));
            hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }
            senha = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return senha;
    }

    public ObservableList<Usuario> getAllUsers() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
            while(rs.next())
            {
                Usuario u = extractAllUsersFromResultSet(rs);
                usuarios.add(u);
            }
            stmt.close();
            rs.close();
            return usuarios;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ObservableList<Usuario> findbyId(StringProperty id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE id LIKE ?");
            ps.setString(1,"%"+id.getValue());
            ResultSet rs = ps.executeQuery();
            ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
            while(rs.next())
            {
                Usuario u = extractAllUsersFromResultSet(rs);
                usuarios.add(u);
            }
            rs.close();
            ps.close();
            return usuarios;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ObservableList<Usuario> findbyName(StringProperty name) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE name LIKE ?");
            ps.setString(1,"%"+name.getValue()+"%");
            ResultSet rs = ps.executeQuery();
            ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
            while(rs.next())
            {
                Usuario u = extractAllUsersFromResultSet(rs);
                usuarios.add(u);
            }
            rs.close();
            ps.close();
            return usuarios;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean updatePassword(String password, String id) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE users SET password = ? WHERE id = ?");
            ps.setString(1, password);
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

    public boolean updateName(String name, String id) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE users SET name = ? WHERE id = ?");
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

    private Usuario extractUserFromResultSet(ResultSet rs) throws SQLException {
        Usuario user = new Usuario();
        user.setId(new SimpleStringProperty(rs.getString("id")));
        user.setPassword(new SimpleStringProperty(rs.getString("password")));
        return user;
    }

    private Usuario extractAllUsersFromResultSet(ResultSet rs) throws SQLException {
        Usuario user = new Usuario();
        user.setId(new SimpleStringProperty(rs.getString("id")));
        user.setName(new SimpleStringProperty(rs.getString("name")));
        user.setPassword(new SimpleStringProperty(rs.getString("password")));
        user.setCreated_at(new SimpleStringProperty(rs.getString("created_at")));
        user.setUpdated_at(new SimpleStringProperty(rs.getString("updated_at")));
        return user;
    }

    public String compararSenha(String senhaInserida, String senhaComparar){
        String senha = criptografaSenha(senhaInserida);
        if(senha.equals(senhaComparar)){
            return senha;
        }
        return null;
    }
}
