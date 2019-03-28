package dao;

import connection.Connect;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.*;

public class ProductDAO {

    private Connection connection;

    public ProductDAO(){
        connection = Connect.connection();
    }

    public boolean save(Produto p){
        try {
            if(p.idProperty().getValue().length() == 0){
                p.idProperty().setValue(null);
            }
            PreparedStatement ps = connection.prepareStatement("INSERT INTO products VALUES (?, ?, ?, ?, ?, 0, NULL, DATETIME('now', 'localtime'), DATETIME('now', 'localtime'), DATETIME('now', 'localtime'), DATETIME('now', 'localtime'))");
            ps.setString(1, p.idProperty().getValue());
            ps.setString(2, p.categoryIdProperty().getValue());
            ps.setString(3, p.nomeProperty().getValue());
            ps.setString(4, p.unit_idProperty().getValue());
            ps.setString(5, p.referenceProperty().getValue());
            int i = ps.executeUpdate();
            if(i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public ObservableList<Produto> getAllProducts() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM products");
            ObservableList<Produto> produtos = FXCollections.observableArrayList();
            while(rs.next())
            {
                Produto p = extractUserFromResultSet(rs);
                produtos.add(p);
            }
            stmt.close();
            rs.close();
            return produtos;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ObservableList<Produto> findbyIdAndReference(StringProperty id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM products WHERE id LIKE ? COLLATE NOCASE OR reference LIKE ? COLLATE NOCASE ");
            ps.setString(1,"%"+id.getValue());
            ps.setString(2,"%"+id.getValue());
            ResultSet rs = ps.executeQuery();
            ObservableList<Produto> produtos = FXCollections.observableArrayList();
            while(rs.next())
            {
                Produto p = extractUserFromResultSet(rs);
                produtos.add(p);
            }
            ps.close();
            rs.close();
            return produtos;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean uploadImage(String path, String id) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE products SET photo_url = ? WHERE id = ?");
            ps.setString(1, path);
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

    public boolean updateNome(String nome, String id) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE products SET product_name = ? WHERE id = ?");
            ps.setString(1, nome);
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

    public boolean updateCategoria(String category_id, String id) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE products SET category_id = ? WHERE id = ?");
            ps.setString(1, category_id);
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

    public boolean updateUnidade(String unit_id, String id) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE products SET unit_id = ? WHERE id = ?");
            ps.setString(1, unit_id);
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

    public boolean updateReference(String reference, String id) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE products SET reference = ? WHERE id = ?");
            ps.setString(1, reference);
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

    public boolean checkId(StringProperty id) {
        try {
            boolean i = false;
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM products WHERE id = ?");
            ps.setString(1, id.getValue());
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

    public EntradaProdutos checkIdEntrada(String id) {
        try {
            EntradaProdutos ep = null;
                PreparedStatement ps = connection.prepareStatement(
                "SELECT products.id \n" +
                    ", products.product_name \n" +
                    ", units.name as unit_name \n" +
                    ", category.name as category_name \n" +
                    ", products.reference \n" +
                    "  FROM products \n" +
                    "  INNER JOIN category on products.category_id = category.id \n" +
                    "  INNER JOIN units on products.unit_id = units.id \n" +
                    "  WHERE products.id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ep = extractEntradaProdutoForResult(rs);
            }
            ps.close();
            rs.close();
            return ep;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public SaidaProdutos checkIdSaida(String id) {
        try {
            SaidaProdutos sp = null;
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT products.id \n" +
                        ", products.product_name \n" +
                        ", units.name as unit_name \n" +
                        ", category.name as category_name \n" +
                        ", products.reference \n" +
                        ", products.storage \n" +
                        "  FROM products \n" +
                        "  INNER JOIN category on products.category_id = category.id \n" +
                        "  INNER JOIN units on products.unit_id = units.id \n" +
                        "  WHERE products.id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                sp = extractSaidaProdutoForResult(rs);
            }
            ps.close();
            rs.close();
            return sp;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Cliente checkIdCliente(String id) {
        try {
            Cliente c = null;
            PreparedStatement ps = connection.prepareStatement("SELECT id, name FROM clients WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                c = extractSaidaUserForResult(rs);
            }
            ps.close();
            rs.close();
            return c;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ObservableList<EntradaProdutos> getAllEntradasById(String id) {
        try {
            String sql = "SELECT products.id \n" +
                    " , products.product_name \n" +
                    " , units.name as unit_name \n" +
                    " , category.name as category_name \n" +
                    " , products.reference \n" +
                    " , inputs.storage_in \n" +
                    "  FROM products \n" +
                    "  INNER JOIN inputs on products.id = inputs.product_id \n" +
                    "  INNER JOIN category on products.category_id = category.id \n" +
                    "  INNER JOIN units on products.unit_id = units.id \n" +
                    "  WHERE inputs.id_entrada = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            ObservableList<EntradaProdutos> entradaProdutos = FXCollections.observableArrayList();
            while(rs.next()) {
                EntradaProdutos ep = extractGetAllEntradaProdutoForResult(rs);
                entradaProdutos.add(ep);
            }
            ps.close();
            rs.close();
            return entradaProdutos;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ObservableList<SaidaProdutos> getAllSaidasById(String id) {
        try {
            String sql = "SELECT products.id \n" +
                    " , products.product_name \n" +
                    " , units.name as unit_name \n" +
                    " , category.name as category_name \n" +
                    " , products.reference \n" +
                    " , clients.name \n" +
                    " , outputs.storage_out \n" +
                    "  FROM products \n" +
                    "  INNER JOIN outputs on products.id = outputs.product_id \n" +
                    "  INNER JOIN clients on outputs.client_id = clients.id \n" +
                    "  INNER JOIN category on products.category_id = category.id \n" +
                    "  INNER JOIN units on products.unit_id = units.id \n" +
                    "  WHERE outputs.id_saida = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            ObservableList<SaidaProdutos> saidaProdutos = FXCollections.observableArrayList();
            while(rs.next()) {
                SaidaProdutos sp = extractGetAllSaidaProdutoForResult(rs);
                saidaProdutos.add(sp);
            }
            ps.close();
            rs.close();
            return saidaProdutos;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ObservableList<Produto> findbyName(StringProperty name) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT P.* \n" +
                    "  FROM products P \n" +
                    "  INNER JOIN category C ON P.category_id = C.id \n" +
                    "  WHERE P.product_name LIKE ? COLLATE NOCASE  \n" +
                    "  OR C.name LIKE ? COLLATE NOCASE " +
                    "  OR P.reference LIKE ? COLLATE NOCASE;");
            ps.setString(1,"%"+name.getValue()+"%");
            ps.setString(2,"%"+name.getValue()+"%");
            ps.setString(3,"%"+name.getValue()+"%");
            ResultSet rs = ps.executeQuery();
            ObservableList<Produto> produtos = FXCollections.observableArrayList();
            while(rs.next())
            {
                Produto p = extractUserFromResultSet(rs);
                produtos.add(p);
            }
            ps.close();
            rs.close();
            return produtos;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Produto extractUserFromResultSet(ResultSet rs) throws SQLException {
        Produto p = new Produto();
        p.setId( new SimpleStringProperty(rs.getString("id")));
        p.setCategory_id( new SimpleStringProperty(rs.getString("category_id")));
        p.setNome( new SimpleStringProperty(rs.getString("product_name")));
        p.setUnit_id(new SimpleStringProperty(rs.getString("unit_id")));
        p.setReference(new SimpleStringProperty(rs.getString("reference")));
        p.setQuantidade( new SimpleStringProperty(rs.getString("storage")));
        p.setPhoto_url( new SimpleStringProperty(rs.getString("photo_url")));
        p.setEntrada( new SimpleStringProperty(rs.getString("storage_date_in")));
        p.setSaida(new SimpleStringProperty(rs.getString("storage_date_out")));
        p.setCreated_at(new SimpleStringProperty(rs.getString("created_at")));
        p.setUpdated_at(new SimpleStringProperty(rs.getString("updated_at")));
        return p;
    }

    private EntradaProdutos extractEntradaProdutoForResult(ResultSet rs) throws SQLException {
        EntradaProdutos ep = new EntradaProdutos();
        ep.setIdProduto( new SimpleStringProperty(rs.getString("id")));
        ep.setNomeProduto( new SimpleStringProperty(rs.getString("product_name")));
        ep.setUnitProduto(new SimpleStringProperty(rs.getString("unit_name")));
        ep.setCatProduto(new SimpleStringProperty(rs.getString("category_name")));
        ep.setRefProduto(new SimpleStringProperty(rs.getString("reference")));
        return ep;
    }

    private EntradaProdutos extractGetAllEntradaProdutoForResult(ResultSet rs) throws SQLException {
        EntradaProdutos ep = new EntradaProdutos();
        ep.setIdProduto( new SimpleStringProperty(rs.getString("id")));
        ep.setNomeProduto( new SimpleStringProperty(rs.getString("product_name")));
        ep.setUnitProduto(new SimpleStringProperty(rs.getString("unit_name")));
        ep.setCatProduto(new SimpleStringProperty(rs.getString("category_name")));
        ep.setRefProduto(new SimpleStringProperty(rs.getString("reference")));
        ep.setQuantidade( new SimpleStringProperty(rs.getString("storage_in")));
        return ep;
    }

    private SaidaProdutos extractSaidaProdutoForResult(ResultSet rs) throws SQLException {
        SaidaProdutos sp = new SaidaProdutos();
        sp.setIdProduto( new SimpleStringProperty(rs.getString("id")));
        sp.setNomeProduto( new SimpleStringProperty(rs.getString("product_name")));
        sp.setUnitProduto(new SimpleStringProperty(rs.getString("unit_name")));
        sp.setCatProduto(new SimpleStringProperty(rs.getString("category_name")));
        sp.setRefProduto(new SimpleStringProperty(rs.getString("reference")));
        sp.setQuantidade(new SimpleStringProperty(rs.getString("storage")));
        return sp;
    }

    private SaidaProdutos extractGetAllSaidaProdutoForResult(ResultSet rs) throws SQLException {
        SaidaProdutos sp = new SaidaProdutos();
        sp.setIdProduto( new SimpleStringProperty(rs.getString("id")));
        sp.setNomeProduto( new SimpleStringProperty(rs.getString("product_name")));
        sp.setUnitProduto(new SimpleStringProperty(rs.getString("unit_name")));
        sp.setCatProduto(new SimpleStringProperty(rs.getString("category_name")));
        sp.setRefProduto(new SimpleStringProperty(rs.getString("reference")));
        sp.setNomeCliente(new SimpleStringProperty(rs.getString("name")));
        sp.setQuantidade( new SimpleStringProperty(rs.getString("storage_out")));
        return sp;
    }

    private Cliente extractSaidaUserForResult(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId( new SimpleStringProperty(rs.getString("id")));
        c.setNome( new SimpleStringProperty(rs.getString("name")));
        return c;
    }
}
