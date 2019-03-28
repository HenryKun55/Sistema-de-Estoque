package fxml.controller;

import app.App;
import com.jfoenix.controls.*;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;
import net.sf.jasperreports.engine.JRException;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import report.PrintReport;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MenuPrincipal implements Initializable {

    private UserDAO udao = new UserDAO();
    private InputDAO edao = new InputDAO();
    private OutputDAO odao = new OutputDAO();
    private ClientsDAO cdao = new ClientsDAO();
    private ProductDAO pdao = new ProductDAO();
    private CategoriaDAO ctdao = new CategoriaDAO();
    private UnidadeDAO undao = new UnidadeDAO();

    private Stage app = App.getPrimaryStage();

    private boolean relatorioCategoria;

    @FXML
    VBox vBox;

    @FXML
    VBox vboxRelatorioInventario;

    @FXML
    VBox vboxRelatorioCategoria;

    @FXML
    VBox vboxRelatorioEntrada;

    @FXML
    VBox vboxRelatorioSaida;

    @FXML
    JFXButton btnRelatorioInvetario;

    @FXML
    JFXButton btnRelatorioCategoria;

    @FXML
    JFXButton btnRelatorioEntrada;

    @FXML
    JFXButton btnRelatorioSaida;

    @FXML
    Label labelSenha;

    @FXML
    JFXDatePicker dataInicioEntrada;

    @FXML
    JFXDatePicker dataFimEntrada;

    @FXML
    JFXDatePicker dataInicioSaida;

    @FXML
    JFXDatePicker dataFimSaida;

    @FXML
    JFXToggleButton toggle;

    @FXML
    JFXToggleButton toggleCategoriaRelatorio;

    private StackPane stackPaneEntrada = new StackPane();

    private StackPane stackPaneSaida = new StackPane();

    private StackPane stackPaneUsuario = new StackPane();

    private StackPane stackPaneCliente = new StackPane();

    private StackPane stackPaneCategoria = new StackPane();

    private StackPane stackPaneUnidade = new StackPane();

    private StackPane stackPaneProduto = new StackPane();

    private TableView<EntradaProdutos> tabelaEntrada;

    private TableView<SaidaProdutos> tabelaSaida;

    @FXML
    private VBox vboxUsuario;

    private VBox vboxUsuarioExtra;

    @FXML
    private VBox vboxProduto;

    private VBox vboxProdutoExtra;

    @FXML
    private VBox vboxCliente;

    private VBox vboxClienteExtra;

    @FXML
    private VBox vboxCategoria;

    private VBox vboxCategoriaExtra;

    @FXML
    private VBox vboxUnidade;

    private VBox vboxUnidadeExtra;

    @FXML
    private VBox vboxEntrada;

    private VBox vboxEntradaExtra = new VBox();

    private VBox vboxShowEntrada = new VBox();

    private JFXButton btnIncluirEntradaProduto;

    @FXML
    private VBox vboxSaida;

    private VBox vboxSaidaExtra = new VBox();

    private VBox vboxShowSaida = new VBox();

    private JFXButton btnIncluirSaidaProduto;

    @FXML
    private JFXTextField idProduto;

    @FXML
    private JFXTextField nomeProduto;

    @FXML
    private JFXTextField referenciaProduto;

    @FXML
    private JFXTextField nomeCliente;

    @FXML
    private JFXTextField nomeUsuario;

    @FXML
    private JFXPasswordField senhaUsuario;

    @FXML
    private JFXTextField nomeCategoria;

    @FXML
    private JFXTextField nomeUnidade;

    private JFXTextField idEntradaProduto;

    private JFXTextField quantidadeEntradaProduto;

    private JFXTextField idSaidaProduto;

    private JFXTextField idClienteSaidaProduto;

    private JFXTextField quantidadeSaidaProduto;

    @FXML
    private JFXTextField searchUsuario;

    @FXML
    private JFXTextField searchProduto;

    @FXML
    private JFXTextField searchCliente;

    @FXML
    private JFXTextField searchCategoria;

    @FXML
    private JFXTextField searchUnidade;

    @FXML
    private JFXComboBox comboBox;

    @FXML
    private JFXComboBox<Categoria> comboBoxCategoria;

    @FXML
    private JFXComboBox<Unidade> comboBoxUnidade;

    @FXML
    private JFXComboBox<Categoria> comboBoxCategoriaRelatorio;

    @FXML
    private JFXTabPane jfxTabPane;

    @FXML
    private JFXTabPane subJfxTabPane;

    @FXML JFXTreeTableView<Usuario> listUsuario;

    @FXML
    private JFXTreeTableView<Produto> listProduto;

    @FXML
    private JFXTreeTableView<Cliente> listCliente;

    @FXML
    private JFXTreeTableView<Entrada> listEntrada;

    @FXML
    private JFXTreeTableView<Saida> listSaida;

    @FXML
    private JFXTreeTableView<Categoria> listCategoria;

    @FXML
    private JFXTreeTableView<Unidade> listUnidade;

    private EventHandler eventHandler;

    @FXML
    private AnchorPane anchorPane;

    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();

    private ObservableList<Produto> produtos = FXCollections.observableArrayList();

    private ObservableList<Cliente> clientes = FXCollections.observableArrayList();

    private ObservableList<Entrada> entradas = FXCollections.observableArrayList();

    private ObservableList<Saida> saidas = FXCollections.observableArrayList();

    private ObservableList<Unidade> unidades = FXCollections.observableArrayList();

    private ObservableList<Categoria> categorias = FXCollections.observableArrayList();

    private ObservableList<EntradaProdutos> entradaProdutos = FXCollections.observableArrayList();

    private ObservableList<SaidaProdutos> saidaProdutos = FXCollections.observableArrayList();

    private ObservableList<EntradaProdutos> getAllEntradaProdutos = FXCollections.observableArrayList();

    private ObservableList<SaidaProdutos> getAllSaidaProdutos = FXCollections.observableArrayList();

    private JFXSnackbar jfxSnackbar = null;

    private JFXSnackbar jfxSnackbarOld = null;

    private String messageSnackBar;

    public MenuPrincipal() throws IOException { }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        atualizeListUsuarios();
        atualizeListProducts();
        atualizeListClientes();
        atualizeListEntradas();
        atualizaListSaidas();
        atualizeListCategorias();
        //atualizeListUnidades();
        getAllUsuarios();
        getAllProducts();
        getAllClientes();
        getAllEntradas();
        getAllSaidas();
        getAllCategorias();
        //getAllUnidades();
        sizeComponents();
        //populateCombo();
        populateComboCategory();
        //populateComboUnits();
        createColumnsUsuario();
        createColumnsProduto();
        createColumnsCliente();
        createColumnsEntrada();
        createColumnsSaida();
        createColumnsCategoria();
        //createColumnsUnidade();
        justNumbers(idProduto);
        setSearchUsuario();
        setSearchProduto();
        setSearchClients();
        setSearchCategoria();
        //setSearchUnits();
    }

    public void setSearchUsuario(){
        searchUsuario.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("[0-9]+")) {
                    usuarios.clear();
                    usuarios.addAll(udao.findbyId(new SimpleStringProperty(newValue)));
                    atualizeListUsuarios();
                }else{
                    usuarios.clear();
                    usuarios.addAll(udao.findbyName(new SimpleStringProperty(newValue)));
                    atualizeListUsuarios();
                }
                if(searchUsuario.getLength() == 0){
                    getAllUsuarios();
                    atualizeListUsuarios();
                }
            }
        });
    }

    public void setSearchProduto(){
        searchProduto.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("[0-9]+")) {
                    produtos.clear();
                    produtos.addAll(pdao.findbyIdAndReference(new SimpleStringProperty(newValue)));
                    atualizeListProducts();
                }else{
                    produtos.clear();
                    produtos.addAll(pdao.findbyName(new SimpleStringProperty(newValue)));
                    atualizeListProducts();
                }
                if(searchProduto.getLength() == 0){
                    getAllProducts();
                    atualizeListProducts();
                }
            }
        });
    }

    private void setSearchCategoria(){
        searchCategoria.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("[0-9]+")) {
                    categorias.clear();
                    categorias.addAll(ctdao.findbyId(new SimpleStringProperty(newValue)));
                    atualizeListCategorias();
                }else{
                    categorias.clear();
                    categorias.addAll(ctdao.findbyName(new SimpleStringProperty(newValue)));
                    atualizeListCategorias();
                }
                if(searchCategoria.getLength() == 0){
                    getAllCategorias();
                    atualizeListCategorias();
                }
            }
        });
    }

    private void setSearchClients(){
        searchCliente.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("[0-9]+")) {
                    clientes.clear();
                    clientes.addAll(cdao.findbyId(new SimpleStringProperty(newValue)));
                    atualizeListClientes();
                }else {
                    clientes.clear();
                    clientes.addAll(cdao.findbyName(new SimpleStringProperty(newValue)));
                    atualizeListClientes();
                }
                if(searchCliente.getLength() == 0){
                    getAllClientes();
                    atualizeListClientes();
                }
            }
        });
    }

    private void setSearchUnits(){
        searchUnidade.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("[0-9]+")) {
                    unidades.clear();
                    unidades.addAll(undao.findbyId(new SimpleStringProperty(newValue)));
                    atualizeListUnidades();
                }else {
                    unidades.clear();
                    unidades.addAll(undao.findbyName(new SimpleStringProperty(newValue)));
                    atualizeListUnidades();
                }
                if(searchUnidade.getLength() == 0){
                    getAllUnidades();
                    atualizeListUnidades();
                }
            }
        });
    }

    private void searchIdProdutoEntrada(){
        AutoCompletionBinding<Produto> bind = TextFields.bindAutoCompletion(idEntradaProduto, produtos);
        bind.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<Produto>>() {
            @Override
            public void handle(AutoCompletionBinding.AutoCompletionEvent<Produto> event) {
                idEntradaProduto.setText(event.getCompletion().idProperty().getValue());
                quantidadeEntradaProduto.requestFocus();
            }
        });
    }

    private void searchIdProdutoSaida(){
        AutoCompletionBinding<Produto> bind = TextFields.bindAutoCompletion(idSaidaProduto, produtos);
        bind.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<Produto>>() {
            @Override
            public void handle(AutoCompletionBinding.AutoCompletionEvent<Produto> event) {
                idSaidaProduto.setText(event.getCompletion().idProperty().getValue());
                quantidadeSaidaProduto.requestFocus();
            }
        });
    }

    private void searchIdClienteSaida(){
        AutoCompletionBinding<Cliente> bind = TextFields.bindAutoCompletion(idClienteSaidaProduto, clientes);
        bind.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<Cliente>>() {
            @Override
            public void handle(AutoCompletionBinding.AutoCompletionEvent<Cliente> event) {
                idClienteSaidaProduto.setText(event.getCompletion().idProperty().getValue());
                idSaidaProduto.requestFocus();
            }
        });
    }

    private void populateComboCategory(){
        comboBoxCategoria.getItems().removeAll(comboBoxCategoria.getItems());
        comboBoxCategoria.getItems().addAll(ctdao.getAllCategorys());
        comboBoxCategoria.getSelectionModel().select(-1);
        comboBoxCategoriaRelatorio.getItems().removeAll(comboBoxCategoriaRelatorio.getItems());
        comboBoxCategoriaRelatorio.getItems().addAll(ctdao.getAllCategorys());
        comboBoxCategoriaRelatorio.getSelectionModel().select(-1);
    }

    private void populateComboUnits(){
        comboBoxUnidade.getItems().removeAll(comboBoxUnidade.getItems());
        comboBoxUnidade.getItems().addAll(undao.getAllUnits());
        comboBoxUnidade.getSelectionModel().select(-1);
    }

    @FXML
    public void setBtnGerarRelatorioEntrada(){
        if(dataInicioEntrada.getValue().isEqual(dataFimEntrada.getValue()) || dataInicioEntrada.getValue().isBefore( dataFimEntrada.getValue() )) {
            if (null != dataInicioEntrada.getValue() && null != dataFimEntrada.getValue()) {
                String dataInicio = dataInicioEntrada.getValue() + " 00:00:00";
                String dataFim = dataFimEntrada.getValue() + " 23:59:59";

                HashMap map = new HashMap();
                map.put("Data_Inicio", dataInicio);
                map.put("Data_Fim", dataFim);

                JFXSpinner spinner = new JFXSpinner();
                spinner.setId("spinner");
                if (null != vboxRelatorioEntrada.lookup("#spinner")) {
                    vboxRelatorioEntrada.getChildren().remove(vboxRelatorioEntrada.lookup("#spinner"));
                    vboxRelatorioEntrada.getChildren().add(spinner);
                } else {
                    vboxRelatorioEntrada.getChildren().add(spinner);
                }
                Thread t = new Thread(new report().setPositionReport(1).setHashMap(map).setPositionCategory(false));
                t.start();
                Thread second = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            t.join();
                            spinner.setVisible(false);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                second.start();
            } else {
                snackbar("Por favor, insira uma data de Entrada de Início e Fim pra gerar o relatório");
            }
        }else{
            snackbar("Por favor, insira uma data de entrada menor que o de saída");
        }
    }

    @FXML
    public void setBtnGerarRelatorioSaida(){
        if(dataInicioSaida.getValue().isEqual(dataFimSaida.getValue()) || dataInicioSaida.getValue().isBefore( dataFimSaida.getValue() )) {
            if (null != dataInicioSaida.getValue() && null != dataFimSaida.getValue()) {
                String dataInicio = dataInicioSaida.getValue() + " 00:00:00";
                String dataFim = dataFimSaida.getValue() + " 23:59:59";

                HashMap map = new HashMap();
                map.put("Data_Inicio", dataInicio);
                map.put("Data_Fim", dataFim);

                JFXSpinner spinner = new JFXSpinner();
                spinner.setId("spinner");
                if (null != vboxRelatorioSaida.lookup("#spinner")) {
                    vboxRelatorioSaida.getChildren().remove(vboxRelatorioSaida.lookup("#spinner"));
                    vboxRelatorioSaida.getChildren().add(spinner);
                } else {
                    vboxRelatorioSaida.getChildren().add(spinner);
                }
                Thread t = new Thread(new report().setPositionReport(2).setHashMap(map).setPositionCategory(false));
                t.start();
                Thread second = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            t.join();
                            spinner.setVisible(false);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                second.start();
            } else {
                snackbar("Por favor, insira uma data de Entrada de Início e Fim pra gerar o relatório");
            }
        }else{
            snackbar("Por favor, insira uma data de entrada menor que o de saída");
        }
    }

    @FXML
    public void setBtnGerarRelatorioCategoria(){
        if(!comboBoxCategoriaRelatorio.getSelectionModel().isEmpty()) {
            HashMap map = new HashMap();
            map.put("categoria", comboBoxCategoriaRelatorio.getSelectionModel().getSelectedItem().nomeProperty().getValue());

            JFXSpinner spinner = new JFXSpinner();
            spinner.setId("spinner");
            if (null != vboxRelatorioCategoria.lookup("#spinner")) {
                vboxRelatorioCategoria.getChildren().remove(vboxRelatorioCategoria.lookup("#spinner"));
                vboxRelatorioCategoria.getChildren().add(spinner);
            } else {
                vboxRelatorioCategoria.getChildren().add(spinner);
            }
            Thread t = new Thread(new report().setPositionReport(3).setHashMap(map).setPositionCategory(relatorioCategoria));
            t.start();
            Thread second = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        t.join();
                        spinner.setVisible(false);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            second.start();
        }else{
            snackbar("Por favor, escolha uma Categoria para gerar o relatório");
        }
    }

    @FXML
    public void setBtnGerarRelatorioInvetario(){
        JFXSpinner spinner = new JFXSpinner();
        spinner.setId("spinner");
        if (null != vboxRelatorioInventario.lookup("#spinner")) {
            vboxRelatorioInventario.getChildren().remove(vboxRelatorioInventario.lookup("#spinner"));
            vboxRelatorioInventario.getChildren().add(spinner);
        } else {
            vboxRelatorioInventario.getChildren().add(spinner);
        }
        Thread t = new Thread(new report().setPositionReport(0).setPositionCategory(false));
        t.start();
        Thread second = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t.join();
                    spinner.setVisible(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        second.start();
    }

    @FXML
    public void setBtnInserirUsuario(){
        if(nomeUsuario.getText().length() > 0 && senhaUsuario.getText().length() > 0){
            Usuario usuario = new Usuario(nomeUsuario.getText(), senhaUsuario.getText());
            if(!udao.checkName(usuario.nameProperty().getValue())){
                if(udao.save(usuario)){
                    usuarios.add(usuario);
                }
                getAllUsuarios();
                atualizeListUsuarios();
                nomeUsuario.setText("");
                senhaUsuario.setText("");
                nomeUsuario.requestFocus();
            }else{
                nomeUsuario.requestFocus();
                snackbar("Nome do usuário já existente, escolha um nome diferente");
            }
        }else{
            nomeUsuario.requestFocus();
            snackbar("Insira um nome e uma senha para inserir um Usuário");
        }
    }

    @FXML
    public void setBtnIncluirProduto(){
        if(nomeProduto.getText().length() > 0 && referenciaProduto.getText().length() > 0 && !comboBoxCategoria.getSelectionModel().isEmpty() && !comboBoxUnidade.getSelectionModel().isEmpty()) {
            Produto produto = new Produto(
                    idProduto.getText(),
                    comboBoxCategoria.getSelectionModel().getSelectedItem().idProperty().getValue(),
                    nomeProduto.getText(),
                    referenciaProduto.getText(),
                    comboBoxUnidade.getSelectionModel().getSelectedItem().idProperty().getValue()
            );

            if(!pdao.checkId(produto.idProperty())){
                if(pdao.save(produto)){
                    produtos.add(produto);
                }
                getAllProducts();
                atualizeListProducts();
                nomeProduto.setText("");
                idProduto.setText("");
                referenciaProduto.setText("");
                comboBoxCategoria.getSelectionModel().select(-1);
                comboBoxUnidade.getSelectionModel().select(-1);
                idProduto.requestFocus();
            }else{
                idProduto.requestFocus();
                snackbar("O produto já existe, escolha um Código diferente");
            }
        }else{
            snackbar("Insira um Nome, Unidade, Categoria e Referência para inserir um Produto");
        }
    }

    @FXML
    public void setBtnIncluirCliente(){
        if(nomeCliente.getText().length() > 0) {
            Cliente cliente = new Cliente(nomeCliente.getText());
            if(cdao.save(cliente)){
                clientes.add(cliente);
            }
            getAllClientes();
            atualizeListClientes();
            //searchIdClienteSaida();
            nomeCliente.setText("");
        }else{
            snackbar("Insira um nome para incluir um Cliente");
        }
    }

    @FXML
    public void setBtnIncluirCategoria(){
        if(nomeCategoria.getText().length() > 0) {
            Categoria categoria = new Categoria(nomeCategoria.getText());
            if(ctdao.save(categoria)){
                categorias.add(categoria);
            }
            getAllCategorias();
            atualizeListCategorias();
            populateComboCategory();
            nomeCategoria.setText("");
        }else{
            snackbar("Insira um nome para incluir uma Categoria");
        }
    }

    @FXML
    public void setBtnIncluirUnidade(){
        if(nomeUnidade.getText().length() > 0) {
            Unidade unidade = new Unidade(nomeUnidade.getText());
            if(undao.save(unidade)){
                unidades.add(unidade);
            }
            getAllUnidades();
            atualizeListUnidades();
            populateComboUnits();
            nomeUnidade.setText("");
        }else{
            snackbar("Insira um nome para incluir uma Unidade");
        }
    }

    /**
     * *Início*
     *
     * Saída de Produtos
     */

    /** Botão para Iniciar a Saída de produtos*/

    @FXML
    public void setBtnIncluirSaida(){
        if(null == vboxSaidaExtra || null == vboxShowSaida && null == stackPaneSaida && null == tabelaSaida || null == getAllSaidaProdutos){
            vboxSaidaExtra = new VBox();
            stackPaneSaida = new StackPane();
            saidaProdutos = FXCollections.observableArrayList();
        }
        vboxSaidaExtra.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());
        stackPaneSaida.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());
        JFXDialogLayout content = new JFXDialogLayout();
        content.setPrefSize(subJfxTabPane.getPrefWidth()/1.2, subJfxTabPane.getPrefHeight()/1.2);
        content.setHeading(new Text("Saída"));
        idClienteSaidaProduto = genericTextField("Código Cliente");
        searchIdClienteSaida();
        justNumbers((idClienteSaidaProduto));
        HBox hBox = new HBox(idClienteSaidaProduto);
        hBox.setPadding(new Insets(0,0,15,0));
        content.setBody(new VBox(hBox, HboxbuttonsSaidaProdutos(),tabelaSaida()));
        JFXDialog dialog = new JFXDialog(stackPaneSaida, content, JFXDialog.DialogTransition.CENTER);
        content.setActions(cancelSaidaProdutos(dialog), saveSaidaProdutos());
        dialog.setOverlayClose(false);

        if(vboxSaidaExtra.getChildren().size() <= 0){
            vboxSaidaExtra.getChildren().addAll(vboxSaida.getChildren());
            vboxSaida.getChildren().removeAll(vboxSaida.getChildren());
            vboxSaida.getChildren().add(stackPaneSaida);
            dialog.show();
        }else{
            dialog.show();
        }
    }

    /** */

    /** Tabela da saída de produtos*/

    private TableView<SaidaProdutos> tabelaSaida(){
        createColumnsSaidaProdutos(1);
        tabelaSaida.setItems(saidaProdutos);
        return tabelaSaida;
    }

    /** */

    /** Tabela de visualização saída de produtos*/

    private TableView<SaidaProdutos> tabelaSaidaVisualizar(){
        createColumnsSaidaProdutos(0);
        getAllSaidaProdutos.clear();
        tabelaSaida.setItems(getAllSaidaProdutos);
        return tabelaSaida;
    }

    /** */

    /** HBOX para dar saída nos produtos ( complemento do Método setBtnIncluirSaida() )*/

    private HBox HboxbuttonsSaidaProdutos(){
        HBox hBox = new HBox();
        idSaidaProduto = genericTextField("Código Produto");
        searchIdProdutoSaida();
        justNumbers(idSaidaProduto);
        quantidadeSaidaProduto = genericTextField("Quantidade");
        justNumbers(quantidadeSaidaProduto);
        btnIncluirSaidaProduto = genericButton("Incluir", "#00BCD4");
        btnIncluirSaidaProduto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                insertSaidaProduto();
            }
        });
        hBox.getChildren().add(idSaidaProduto);
        hBox.getChildren().add(quantidadeSaidaProduto);
        hBox.getChildren().add(btnIncluirSaidaProduto);
        hBox.setPadding(new Insets(0,0,10,0));
        HBox.setMargin(hBox.getChildren().get(0), new Insets(0,0,0,0));
        HBox.setMargin(hBox.getChildren().get(1), new Insets(0,0,0,10));
        HBox.setMargin(hBox.getChildren().get(2), new Insets(0,0,0,10));
        return hBox;
    }

    /** */

    /** Confirmar Produtos de Saída*/

    private JFXButton saveSaidaProdutos(){
        JFXButton save = genericButton("Concluir", "#00BF5D");
        save.setOnAction(event -> {
            if(tabelaSaida.getItems().size() > 0 ) {
                String id = idClienteSaidaProduto.getText();
                if(pdao.checkIdCliente(id) != null) {
                    JFXDialogLayout content = new JFXDialogLayout();
                    content.setPrefSize(subJfxTabPane.getPrefWidth() / 6, subJfxTabPane.getPrefHeight() / 6);
                    content.setHeading(new Text("Confirmar ?"));
                    JFXDialog dialog = new JFXDialog(stackPaneSaida, content, JFXDialog.DialogTransition.CENTER);
                    content.setActions(confirmSaida(dialog), cancelSaida(dialog));
                    dialog.setOverlayClose(false);
                    dialog.show();
                }else{
                    snackbar("Cliente Inexistente");
                }
            }else{
                snackbar("Insira pelo menos um produto para confirmar a Saída");
            }
        });
        return save;
    }

    /** */

    /** Cancelar Saída de Produtos*/

    private JFXButton cancelSaidaProdutos(JFXDialog dialog){
        JFXButton cancel = genericButton("Cancelar", "#FF4043");
        cancel.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                dialog.close();
                vboxSaida.getChildren().removeAll(vboxSaida.getChildren());
                vboxSaida.getChildren().addAll(vboxSaidaExtra.getChildren());
            }
        });
        return cancel;
    }

    /** */

    /** Fechar Entrada de Visualização de Produtos*/

    private JFXButton closeSaidaProdutos(JFXDialog dialog){
        JFXButton close = genericButton("Fechar", "#00BCD4");
        close.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                dialog.close();
                vboxSaida.getChildren().removeAll(vboxSaida.getChildren());
                vboxSaida.getChildren().addAll(vboxShowSaida.getChildren());
            }
        });
        return close;
    }

    /** */

    /** *ALERTA* Salvar Entrada */

    private JFXButton confirmSaida(JFXDialog dialog){
        JFXButton btnConfirm = genericButton("Confirmar", "#00BF5D");
        btnConfirm.setOnAction(event -> {
            setSaida();
            snackbar("Saída feita com Sucesso!");
            dialog.close();
            vboxSaida.getChildren().removeAll(vboxSaida.getChildren());
            vboxSaida.getChildren().addAll(vboxSaidaExtra.getChildren());
            tabelaSaida = null;
            vboxSaidaExtra = null;
            stackPaneSaida = null;
            saidaProdutos = null;
        });
        return btnConfirm;
    }

    /** */

    /** *ALERTA* Cancelar Entrada */

    private JFXButton cancelSaida(JFXDialog dialog){
        JFXButton btnCancel = genericButton("Cancelar", "#FF4043");
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        return btnCancel;
    }

    /** */

    /** Criando Colunas para dar entrada nos produtos*/

    private void createColumnsSaidaProdutos(int i){
        tabelaSaida = new TableView<>();
        tabelaSaida.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());

        TableColumn<SaidaProdutos, String> idColumn = new TableColumn<>("Código");
        idColumn.setMinWidth(tabelaSaida.getPrefWidth()/12);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idProduto"));

        TableColumn<SaidaProdutos, String> nameColumn = new TableColumn<>("Produto");
        nameColumn.setMinWidth(tabelaSaida.getPrefWidth()/10);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));

        TableColumn<SaidaProdutos, String> unitColumn = new TableColumn<>("Unidade");
        unitColumn.setMinWidth(tabelaSaida.getPrefWidth()/10);
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unitProduto"));

        TableColumn<SaidaProdutos, String> refColumn = new TableColumn<>("Referência");
        refColumn.setMinWidth(tabelaSaida.getPrefWidth()/10);
        refColumn.setCellValueFactory(new PropertyValueFactory<>("refProduto"));

        TableColumn<SaidaProdutos, String> catColumn = new TableColumn<>("Categoria");
        catColumn.setMinWidth(tabelaSaida.getPrefWidth()/10);
        catColumn.setCellValueFactory(new PropertyValueFactory<>("catProduto"));

        TableColumn<SaidaProdutos, String> quantityColumn = new TableColumn<>("Quantidade");
        quantityColumn.setMinWidth(tabelaSaida.getPrefWidth()/10);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        if(1 == i){
            TableColumn<SaidaProdutos, String> actionColumn = new TableColumn<>("Ações");
            actionColumn.setMinWidth(tabelaSaida.getPrefWidth()/10);
            actionColumn.setId("columnAction");
            Callback<TableColumn<SaidaProdutos, String>, TableCell<SaidaProdutos, String>> cell
                    = new Callback<TableColumn<SaidaProdutos, String>, TableCell<SaidaProdutos, String>>() {
                @Override
                public TableCell<SaidaProdutos, String> call(final TableColumn<SaidaProdutos, String> param) {
                    final TableCell<SaidaProdutos, String> cell = new TableCell<SaidaProdutos, String>() {

                        private final JFXButton btnDelete = genericButton("Remover", "#00BCD4");

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                btnDelete.setButtonType(JFXButton.ButtonType.RAISED);
                                btnDelete.setId("btnDelete");
                                btnDelete.setOnAction(event -> {
                                    getTableView().getItems().remove(getIndex());
                                });
                                setGraphic(btnDelete);
                            }
                        }
                    };
                    return cell;
                }
            };
            actionColumn.setCellFactory(cell);
            tabelaSaida.getColumns().addAll(idColumn, nameColumn, unitColumn, refColumn, catColumn, quantityColumn, actionColumn);
        }else {
            tabelaSaida.getColumns().addAll(idColumn, nameColumn, unitColumn, refColumn, catColumn, quantityColumn);
        }
    }

    /** */

    /** Método para inserir os produtos na Saída*/

    private void insertSaidaProduto(){
        if(idSaidaProduto.getText().length() > 0 && quantidadeSaidaProduto.getText().length() > 0) {
            SaidaProdutos sp = pdao.checkIdSaida(idSaidaProduto.getText());
            if(sp != null ) {
                int quantidadeAtual = Integer.parseInt(sp.quantidadeProperty().getValue());
                int quantidadeInserida = Integer.parseInt(quantidadeSaidaProduto.getText());
                if (quantidadeInserida > 0) {
                    if (quantidadeInserida <= quantidadeAtual) {
                        StringProperty quantidade = new SimpleStringProperty(quantidadeSaidaProduto.getText());
                        sp.setQuantidade(quantidade);
                        idSaidaProduto.setText("");
                        quantidadeSaidaProduto.setText("");
                        idSaidaProduto.requestFocus();
                        tabelaSaida.getItems().add(sp);
                    } else {
                        snackbar("Quantidade maior do que o estoque atual");
                    }
                } else {
                    snackbar("Insira uma quantidade positivo");
                }
            }else{
                snackbar("Produto Inexistente, Insira um Código válido");
            }
        }else{
            snackbar("Insira um Código e uma quantidade");
        }
    }

    /** */

    /** Método para finalizar a entrada de produtos , que é chamado no Botão Salvar logo acima (confirmEntrada())*/

    private void setSaida(){
        for(SaidaProdutos sp : saidaProdutos){
            odao.save(saidas.size()+1, idClienteSaidaProduto.getText() ,sp);
            odao.updateStorage(sp.idProdutoProperty().getValue(), sp.quantidadeProperty().getValue());
        }
        getAllSaidas();
        atualizaListSaidas();
        getAllProducts();
        atualizeListProducts();
    }

    /** */

    /** Método para visualizar os produtos que foram dado saida*/

    private void showTableAllSaidaProdutos(String id, String name){
        if(null == vboxSaidaExtra || null == vboxShowSaida && null == stackPaneSaida && null == tabelaSaida || null == getAllSaidaProdutos){
            vboxShowSaida = new VBox();
            stackPaneSaida = new StackPane();
            getAllSaidaProdutos = FXCollections.observableArrayList();
        }
        vboxShowSaida.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());
        stackPaneSaida.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());
        JFXDialogLayout content = new JFXDialogLayout();
        content.setPrefSize(subJfxTabPane.getPrefWidth()/1.2, subJfxTabPane.getPrefHeight()/1.2);
        content.setHeading(new Text("Nota de Saída: "+id+" Cliente: "+name));
        content.setBody(new VBox( tabelaSaidaVisualizar()) );
        JFXDialog dialog = new JFXDialog(stackPaneSaida, content, JFXDialog.DialogTransition.CENTER);
        content.setActions( closeSaidaProdutos(dialog) );
        dialog.setOverlayClose(false);

        if(vboxShowSaida.getChildren().size() <= 0){
            vboxShowSaida.getChildren().addAll(vboxSaida.getChildren());
            vboxSaida.getChildren().removeAll(vboxSaida.getChildren());
            vboxSaida.getChildren().add(stackPaneSaida);
            dialog.show();
        }else{
            dialog.show();
        }
    }

    /** */

    /**
     * *Final*
     *
     * Saída de Produtos
     */

    /**
     * *Início*
     *
     * Entrada de Produtos
     */

    /** Botão para Iniciar a Entrada de produtos*/

    @FXML
    public void setBtnIncluirEntrada(){
        if(null == vboxEntradaExtra || null == vboxShowEntrada && null == stackPaneEntrada && null == tabelaEntrada || null == getAllEntradaProdutos){
            vboxEntradaExtra = new VBox();
            stackPaneEntrada = new StackPane();
            entradaProdutos = FXCollections.observableArrayList();
        }
        vboxEntradaExtra.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());
        stackPaneEntrada.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());
        JFXDialogLayout content = new JFXDialogLayout();
        content.setPrefSize(subJfxTabPane.getPrefWidth()/1.2, subJfxTabPane.getPrefHeight()/1.2);
        content.setHeading(new Text("Entrada"));
        content.setBody(new VBox(HboxbuttonsEntradaProdutos(),tabelaEntrada()));
        JFXDialog dialog = new JFXDialog(stackPaneEntrada, content, JFXDialog.DialogTransition.CENTER);
        content.setActions(cancelEntradaProdutos(dialog), saveEntradaProdutos());
        dialog.setOverlayClose(false);

        if(vboxEntradaExtra.getChildren().size() <= 0){
            vboxEntradaExtra.getChildren().addAll(vboxEntrada.getChildren());
            vboxEntrada.getChildren().removeAll(vboxEntrada.getChildren());
            vboxEntrada.getChildren().add(stackPaneEntrada);
            dialog.show();
        }else{
            dialog.show();
        }
    }

    /** */

    /** Tabela da entrada de produtos*/

    private TableView<EntradaProdutos> tabelaEntrada(){
        createColumnsEntradaProdutos(1);
        tabelaEntrada.setItems(entradaProdutos);
        return tabelaEntrada;
    }

    /** */

    /** Tabela de visualização entrada de produtos*/

    private TableView<EntradaProdutos> tabelaEntradaVisualizar(){
        createColumnsEntradaProdutos(0);
        getAllEntradaProdutos.clear();
        tabelaEntrada.setItems(getAllEntradaProdutos);
        return tabelaEntrada;
    }

    /** */

    /** HBOX para dar entrada nos produtos ( complemento do Método setBtnIncluirEntrada() )*/

    private HBox HboxbuttonsEntradaProdutos(){
        HBox hBox = new HBox();
        idEntradaProduto = genericTextField("Código Produto");
        searchIdProdutoEntrada();
        justNumbers(idEntradaProduto);
        quantidadeEntradaProduto = genericTextField("Quantidade");
        justNumbers(quantidadeEntradaProduto);
        btnIncluirEntradaProduto = genericButton("Incluir", "#00BCD4");
        btnIncluirEntradaProduto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                insertEntradaProduto();
            }
        });
        hBox.getChildren().add(idEntradaProduto);
        hBox.getChildren().add(quantidadeEntradaProduto);
        hBox.getChildren().add(btnIncluirEntradaProduto);
        hBox.setPadding(new Insets(0,0,10,0));
        HBox.setMargin(hBox.getChildren().get(0), new Insets(0,0,0,0));
        HBox.setMargin(hBox.getChildren().get(1), new Insets(0,0,0,10));
        HBox.setMargin(hBox.getChildren().get(2), new Insets(0,0,0,10));
        return hBox;
    }

    /** */

    /** Confirmar Produtos de Entrada*/

    private JFXButton saveEntradaProdutos(){
        JFXButton save = genericButton("Concluir", "#00BF5D");
        save.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                if(tabelaEntrada.getItems().size() > 0) {
                    JFXDialogLayout content = new JFXDialogLayout();
                    content.setPrefSize(subJfxTabPane.getPrefWidth() / 6, subJfxTabPane.getPrefHeight() / 6);
                    content.setHeading(new Text("Confirmar ?"));
                    JFXDialog dialog = new JFXDialog(stackPaneEntrada, content, JFXDialog.DialogTransition.CENTER);
                    content.setActions(confirmEntrada(dialog), cancelEntrada(dialog));
                    dialog.setOverlayClose(false);
                    dialog.show();
                }else{
                    snackbar("Insira pelo menos um produto para confirmar a Entrada");
                }
            }
        });
        return save;
    }

    /** */

    /** Cancelar Entrada de Produtos*/

    private JFXButton cancelEntradaProdutos(JFXDialog dialog){
        JFXButton cancel = genericButton("Cancelar", "#FF4043");
        cancel.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                dialog.close();
                vboxEntrada.getChildren().removeAll(vboxEntrada.getChildren());
                vboxEntrada.getChildren().addAll(vboxEntradaExtra.getChildren());
            }
        });
        return cancel;
    }

    /** */

    /** Fechar Entrada de Visualização de Produtos*/

    private JFXButton closeEntradaProdutos(JFXDialog dialog){
        JFXButton close = genericButton("Fechar", "#00BCD4");
        close.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                dialog.close();
                vboxEntrada.getChildren().removeAll(vboxEntrada.getChildren());
                vboxEntrada.getChildren().addAll(vboxShowEntrada.getChildren());
            }
        });
        return close;
    }

    /** */

    /** *ALERTA* Salvar Entrada */

    private JFXButton confirmEntrada(JFXDialog dialog){
        JFXButton btnConfirm = genericButton("Confirmar", "#00BF5D");
        btnConfirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setEntrada();
                snackbar("Entrada feita com Sucesso!");
                dialog.close();
                vboxEntrada.getChildren().removeAll(vboxEntrada.getChildren());
                vboxEntrada.getChildren().addAll(vboxEntradaExtra.getChildren());
                tabelaEntrada = null;
                vboxEntradaExtra = null;
                stackPaneEntrada = null;
                entradaProdutos = null;
            }
        });
        return btnConfirm;
    }

    /** */

    /** *ALERTA* Cancelar Entrada */

    private JFXButton cancelEntrada(JFXDialog dialog){
        JFXButton btnCancel = genericButton("Cancelar", "#FF4043");
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        return btnCancel;
    }

    /** */

    /** Criando Colunas para dar entrada nos produtos*/

    private void createColumnsEntradaProdutos(int i){
        tabelaEntrada = new TableView<>();
        tabelaEntrada.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());

        TableColumn<EntradaProdutos, String> idColumn = new TableColumn<>("Código");
        idColumn.setMinWidth(tabelaEntrada.getPrefWidth()/10);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idProduto"));

        TableColumn<EntradaProdutos, String> nameColumn = new TableColumn<>("Produto");
        nameColumn.setMinWidth(tabelaEntrada.getPrefWidth()/10);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));

        TableColumn<EntradaProdutos, String> unitColumn = new TableColumn<>("Unidade");
        unitColumn.setMinWidth(tabelaEntrada.getPrefWidth()/10);
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unitProduto"));

        TableColumn<EntradaProdutos, String> refColumn = new TableColumn<>("Referência");
        refColumn.setMinWidth(tabelaEntrada.getPrefWidth()/10);
        refColumn.setCellValueFactory(new PropertyValueFactory<>("refProduto"));

        TableColumn<EntradaProdutos, String> catColumn = new TableColumn<>("Categoria");
        catColumn.setMinWidth(tabelaEntrada.getPrefWidth()/10);
        catColumn.setCellValueFactory(new PropertyValueFactory<>("catProduto"));

        TableColumn<EntradaProdutos, String> quantityColumn = new TableColumn<>("Quantidade");
        quantityColumn.setMinWidth(tabelaEntrada.getPrefWidth()/10);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        if(1 == i){
            TableColumn<EntradaProdutos, String> actionColumn = new TableColumn<>("Ações");
            actionColumn.setMinWidth(tabelaEntrada.getPrefWidth()/10);
            actionColumn.setId("columnAction");
            Callback<TableColumn<EntradaProdutos, String>, TableCell<EntradaProdutos, String>> cell
                    = new Callback<TableColumn<EntradaProdutos, String>, TableCell<EntradaProdutos, String>>() {
                @Override
                public TableCell<EntradaProdutos, String> call(final TableColumn<EntradaProdutos, String> param) {
                    final TableCell<EntradaProdutos, String> cell = new TableCell<EntradaProdutos, String>() {

                        private final JFXButton btnDelete = genericButton("Remover", "#00BCD4");

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                btnDelete.setButtonType(JFXButton.ButtonType.RAISED);
                                btnDelete.setId("btnDelete");
                                btnDelete.setOnAction(event -> {
                                    getTableView().getItems().remove(getIndex());
                                });
                                setGraphic(btnDelete);
                            }
                        }
                    };
                    return cell;
                }
            };
            actionColumn.setCellFactory(cell);
            tabelaEntrada.getColumns().addAll(idColumn, nameColumn, unitColumn, refColumn, catColumn, quantityColumn, actionColumn);
        }else {
            tabelaEntrada.getColumns().addAll(idColumn, nameColumn, unitColumn, refColumn, catColumn, quantityColumn);
        }
    }

    /** */

    /** Método para inserir os produtos na Entrada*/

    private void insertEntradaProduto(){
        if(idEntradaProduto.getText().length() > 0 && quantidadeEntradaProduto.getText().length() > 0) {
            EntradaProdutos e = pdao.checkIdEntrada(idEntradaProduto.getText());
            if(e != null ) {
                if(Integer.parseInt(quantidadeEntradaProduto.getText()) > 0 ){
                    StringProperty quantidade = new SimpleStringProperty(quantidadeEntradaProduto.getText());
                    e.setQuantidade(quantidade);
                    idEntradaProduto.setText("");
                    quantidadeEntradaProduto.setText("");
                    idEntradaProduto.requestFocus();
                    tabelaEntrada.getItems().add(e);
                }else{
                    snackbar("Insira uma quantidade positivo");
                }
            }else{
                snackbar("Produto Inexistente, Insira um Código válido");
            }
        }else{
            snackbar("Insira um Código e uma quantidade");
        }
    }

    /** */

    /** Método para finalizar a entrada de produtos , que é chamado no Botão Salvar logo acima (confirmEntrada())*/

    private void setEntrada(){
        for(EntradaProdutos e : entradaProdutos){
            edao.save(entradas.size()+1 ,e);
            edao.updateStorage(e.idProdutoProperty().getValue(), e.quantidadeProperty().getValue());
        }
        getAllEntradas();
        atualizeListEntradas();
        getAllProducts();
        atualizeListProducts();
    }

    /** */

    /** Método para visualizar os produtos que foram dado entrada*/

    private void showTableAllEntradaProdutos(String id){
        if(null == vboxEntradaExtra || null == vboxShowEntrada && null == stackPaneEntrada && null == tabelaEntrada || null == getAllEntradaProdutos){
            vboxShowEntrada = new VBox();
            stackPaneEntrada = new StackPane();
            getAllEntradaProdutos = FXCollections.observableArrayList();
        }
        vboxShowEntrada.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());
        stackPaneEntrada.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());
        JFXDialogLayout content = new JFXDialogLayout();
        content.setPrefSize(subJfxTabPane.getPrefWidth()/1.2, subJfxTabPane.getPrefHeight()/1.2);
        content.setHeading(new Text("Nota de Entrada: "+id));
        content.setBody(tabelaEntradaVisualizar());
        JFXDialog dialog = new JFXDialog(stackPaneEntrada, content, JFXDialog.DialogTransition.CENTER);
        content.setActions( closeEntradaProdutos(dialog) );
        dialog.setOverlayClose(false);

        if(vboxShowEntrada.getChildren().size() <= 0){
            vboxShowEntrada.getChildren().addAll(vboxEntrada.getChildren());
            vboxEntrada.getChildren().removeAll(vboxEntrada.getChildren());
            vboxEntrada.getChildren().add(stackPaneEntrada);
            dialog.show();
        }else{
            dialog.show();
        }
    }

    /** */

    /**
     * *Final*
     *
     * Entrada de Produtos
     */

    @FXML
    private void sizeComponents(){
        vBox.setPrefSize(sizeScreen().width, sizeScreen().height);
        jfxTabPane.setPrefSize(vBox.getPrefWidth(),vBox.getPrefHeight());
        subJfxTabPane.setPrefSize(jfxTabPane.getPrefWidth()/1.1, jfxTabPane.getPrefHeight()/1.1);
        //areaChart.setPrefSize((jfxTabPane.getPrefWidth()/1.2),(jfxTabPane.getPrefHeight()/1.15));
        listProduto.setMinHeight(subJfxTabPane.getPrefHeight()/1.2);
        listCliente.setMinHeight(subJfxTabPane.getPrefHeight()/1.2);
        listEntrada.setMinHeight(subJfxTabPane.getPrefHeight()/1.2);
        listSaida.setMinHeight(subJfxTabPane.getPrefHeight()/1.2);
        listUsuario.setMinHeight(subJfxTabPane.getPrefHeight()/1.2);
        listCategoria.setMinHeight(subJfxTabPane.getPrefHeight()/1.2);
//        listUnidade.setMinHeight(subJfxTabPane.getPrefHeight()/1.2);
    }

    private Dimension sizeScreen(){
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize;
    }

    private void justNumbers(JFXTextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    textField.setText(oldValue);
                }
            }
        });
    }

    private void createColumnsUsuario() {
        JFXTreeTableColumn<Usuario, String> depthId = new JFXTreeTableColumn<>("Código");
        depthId.setPrefWidth(listUsuario.getPrefWidth() / 2);
        depthId.setCellValueFactory(param -> param.getValue().getValue().idProperty());
        listUsuario.getColumns().add(depthId);

        JFXTreeTableColumn<Usuario, String> depthName = new JFXTreeTableColumn<>("Nome");
        depthName.setPrefWidth(listUsuario.getPrefWidth() / 2);
        depthName.setCellValueFactory(param -> param.getValue().getValue().nameProperty());
        listUsuario.getColumns().add(depthName);

        JFXTreeTableColumn<Usuario, String> depthDate = new JFXTreeTableColumn<>("Criado em");
        depthDate.setPrefWidth(listUsuario.getPrefWidth() / 2);
        depthDate.setCellValueFactory(param -> param.getValue().getValue().created_atProperty());
        listUsuario.getColumns().add(depthDate);

        JFXTreeTableColumn<Usuario, String> depthAction = new JFXTreeTableColumn<>("Ações");
        depthAction.setPrefWidth(listUsuario.getPrefWidth() /6);
        depthAction.setId("columnAcion");
        Callback<TreeTableColumn<Usuario, String>, TreeTableCell<Usuario, String>> callback
                = //
                new Callback<TreeTableColumn<Usuario, String>, TreeTableCell<Usuario, String>>() {
                    @Override
                    public TreeTableCell call(final TreeTableColumn<Usuario, String> param) {
                        final TreeTableCell<Usuario, String> cell = new TreeTableCell<Usuario, String>() {
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    VBox vbox = new VBox(5);
                                    vbox.setPadding(new Insets(18,0,0,0));

                                    //System.out.println("Usuário :"+ getTreeTableRow().getItem());

                                    String id = getTreeTableRow().getItem().idProperty().getValue();

                                    JFXButton btnConfirma = genericButton("Alterar", "#00BF5D");

                                    JFXButton btnCancel = genericButton("Cancelar" ,"#FF4043");

                                    /** Alterar o Nome do usuário*/

                                    JFXButton btn = new JFXButton("Alterar Nome");
                                    btn.setId("btnAlteraProduto");
                                    btn.setButtonType(JFXButton.ButtonType.RAISED);

                                    JFXTextField newName = new JFXTextField();
                                    newName.setLabelFloat(true);
                                    newName.setPromptText("Novo Usuário");

                                    /** Ação do botão Alterar senha do Usuário */

                                    btn.setOnAction(event -> {
                                        if(null == vboxUsuarioExtra || null == stackPaneUsuario ){
                                            vboxUsuarioExtra = new VBox();
                                            stackPaneUsuario = new StackPane();
                                        }

                                        JFXDialogLayout content = new JFXDialogLayout();
                                        content.setPrefSize(subJfxTabPane.getPrefWidth() / 4, subJfxTabPane.getPrefHeight() / 4);
                                        content.setHeading(new Text("Digite o novo nome do Usuário"));
                                        content.setBody(newName);
                                        content.setActions(btnConfirma, btnCancel);
                                        JFXDialog dialog = new JFXDialog(stackPaneUsuario, content, JFXDialog.DialogTransition.CENTER);
                                        btnConfirma.setOnAction(event1 -> {
                                            if(newName.getText().length() > 0) {
                                                if(udao.updateName(newName.getText(), id)) {
                                                    snackbar("Nome Alterado com Sucesso!");
                                                }else{
                                                    snackbar("Falha ao alterar o usuário");
                                                }
                                                atualizeListUsuarios();
                                                getAllUsuarios();
                                                dialog.close();
                                                vboxUsuario.getChildren().removeAll(vboxUsuario.getChildren());
                                                vboxUsuario.getChildren().addAll(vboxUsuarioExtra.getChildren());
                                                vboxUsuarioExtra = null;
                                                stackPaneUsuario = null;
                                            }else{
                                                snackbar("Digite um nome pra Alterar");
                                            }
                                        });
                                        btnCancel.setOnAction(event2 -> {
                                            dialog.close();
                                            vboxUsuario.getChildren().removeAll(vboxUsuario.getChildren());
                                            vboxUsuario.getChildren().addAll(vboxUsuarioExtra.getChildren());});
                                        dialog.setOverlayClose(false);

                                        stackPaneUsuario.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());
                                        vboxUsuarioExtra.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());

                                        if(vboxUsuarioExtra.getChildren().size() <= 0) {
                                            vboxUsuarioExtra.getChildren().addAll(vboxUsuario.getChildren());
                                            vboxUsuario.getChildren().removeAll(vboxUsuario.getChildren());
                                            vboxUsuario.getChildren().add(stackPaneUsuario);
                                            dialog.show();
                                        }else{
                                            dialog.show();
                                        }
                                    });

                                    /** Alterar o Senha do usuário*/

                                    JFXButton btn2 = new JFXButton("Alterar Senha");
                                    btn2.setId("btnAlteraProduto");
                                    btn2.setButtonType(JFXButton.ButtonType.RAISED);

                                    JFXTextField newPassword = new JFXTextField();
                                    newPassword.setLabelFloat(true);
                                    newPassword.setPromptText("Nova Senha");

                                    /** Ação do botão Alterar senha do Usuário */

                                    btn2.setOnAction(event -> {
                                        if(null == vboxUsuarioExtra || null == stackPaneUsuario ){
                                            vboxUsuarioExtra = new VBox();
                                            stackPaneUsuario = new StackPane();
                                        }

                                        JFXDialogLayout content = new JFXDialogLayout();
                                        content.setPrefSize(subJfxTabPane.getPrefWidth() / 4, subJfxTabPane.getPrefHeight() / 4);
                                        content.setHeading(new Text("Digite a nova senha do Produto"));
                                        content.setBody(newPassword);
                                        content.setActions(btnConfirma, btnCancel);
                                        JFXDialog dialog = new JFXDialog(stackPaneUsuario, content, JFXDialog.DialogTransition.CENTER);
                                        btnConfirma.setOnAction(event1 -> {
                                            if(newPassword.getText().length() > 0) {
                                                if(udao.updatePassword(newPassword.getText(), id)) {
                                                    snackbar("Senha Alterada com Sucesso!");
                                                }else{
                                                    snackbar("Falha ao alterar a senha");
                                                }

                                                atualizeListUsuarios();
                                                getAllUsuarios();
                                                dialog.close();
                                                vboxUsuario.getChildren().removeAll(vboxUsuario.getChildren());
                                                vboxUsuario.getChildren().addAll(vboxUsuarioExtra.getChildren());
                                                vboxUsuarioExtra = null;
                                                stackPaneUsuario = null;
                                            }else{
                                                snackbar("Digite uma senha pra Alterar");
                                            }
                                        });
                                        btnCancel.setOnAction(event2 -> {
                                            dialog.close();
                                            vboxUsuario.getChildren().removeAll(vboxUsuario.getChildren());
                                            vboxUsuario.getChildren().addAll(vboxUsuarioExtra.getChildren());});
                                        dialog.setOverlayClose(false);

                                        stackPaneUsuario.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());
                                        vboxUsuarioExtra.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());

                                        if(vboxUsuarioExtra.getChildren().size() <= 0) {
                                            vboxUsuarioExtra.getChildren().addAll(vboxUsuario.getChildren());
                                            vboxUsuario.getChildren().removeAll(vboxUsuario.getChildren());
                                            vboxUsuario.getChildren().add(stackPaneUsuario);
                                            dialog.show();
                                        }else{
                                            dialog.show();
                                        }
                                    });

                                    vbox.getChildren().addAll(btn, btn2);

                                    setGraphic(vbox);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        depthAction.setCellFactory(callback);
        listUsuario.getColumns().addAll(depthAction);
    }

    private void createColumnsProduto(){

        JFXTreeTableColumn<Produto, String> depthPhoto = new JFXTreeTableColumn<>("Foto");
        depthPhoto.setPrefWidth(listProduto.getPrefWidth()/3);
        depthPhoto.setStyle("-fx-alignment: CENTER");
        Callback<TreeTableColumn<Produto, String>, TreeTableCell<Produto, String>> cellPhoto
            = new Callback<TreeTableColumn<Produto, String>, TreeTableCell<Produto, String>>() {
            @Override
            public TreeTableCell<Produto, String> call(TreeTableColumn<Produto, String> param) {
                final TreeTableCell<Produto, String> cell = new TreeTableCell<Produto, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            Produto p = getTreeTableRow().getItem();
                            if(null != p.photo_UrlProperty().getValue()){
                                ImageView imageView = new ImageView(new Image(p.photo_UrlProperty().getValue()));
                                setGraphic(imageView);
                                setText("");
                            }else{
                                setGraphic(null);
                                setText("Sem Foto");
                            }
                        }
                    }
                };
                return cell;
            }
        };
        depthPhoto.setCellFactory(cellPhoto);
        listProduto.getColumns().add(depthPhoto);

        JFXTreeTableColumn<Produto, String> depthId = new JFXTreeTableColumn<>("Código");
        depthId.setPrefWidth(listProduto.getPrefWidth()/6);
        depthId.setCellValueFactory(param -> param.getValue().getValue().idProperty());
        listProduto.getColumns().add(depthId);

        JFXTreeTableColumn<Produto, String> depthProduto = new JFXTreeTableColumn<>("Produto");
        depthProduto.setPrefWidth(listProduto.getPrefWidth()/2);
        depthProduto.setCellValueFactory(param -> param.getValue().getValue().nomeProperty());
        listProduto.getColumns().add(depthProduto);

        JFXTreeTableColumn<Produto, String> depthUnit = new JFXTreeTableColumn<>("Unidade");
        depthUnit.setPrefWidth(listProduto.getPrefWidth()/6);
        depthUnit.setCellValueFactory(param -> {
            Unidade u = undao.checkId(param.getValue().getValue().unit_idProperty());
            if(u != null ){
                return u.nomeProperty();
            }
            return param.getValue().getValue().unit_idProperty();
        });

        listProduto.getColumns().add(depthUnit);

        JFXTreeTableColumn<Produto, String> depthReference = new JFXTreeTableColumn<>("Referência");
        depthReference.setPrefWidth(listProduto.getPrefWidth()/6);
        depthReference.setCellValueFactory(param -> param.getValue().getValue().referenceProperty());
        listProduto.getColumns().add(depthReference);

        JFXTreeTableColumn<Produto, String> depthQuantidade = new JFXTreeTableColumn<>("Quantidade");
        depthQuantidade.setPrefWidth(listProduto.getPrefWidth()/6);
        depthQuantidade.setCellValueFactory(param -> param.getValue().getValue().quantidadeProperty());
        listProduto.getColumns().add(depthQuantidade);

        JFXTreeTableColumn<Produto, String> depthEntrada = new JFXTreeTableColumn<>("E");
        depthEntrada.setPrefWidth(listProduto.getPrefWidth()/4);
        depthEntrada.setCellValueFactory(param -> {
            Produto p = param.getValue().getValue();
            if(p.entradaProperty().getValue().equals(p.createdAtProperty().getValue())){
                p.entradaProperty().setValue("Não deu entrada");
                return p.entradaProperty();
            }else{
                return p.entradaProperty();
            }
        });
        listProduto.getColumns().add(depthEntrada);

        JFXTreeTableColumn<Produto, String> depthSaida = new JFXTreeTableColumn<>("S");
        depthSaida.setPrefWidth(listProduto.getPrefWidth()/4);
        depthSaida.setCellValueFactory(param -> {
            Produto p = param.getValue().getValue();
            if(p.saidaProperty().getValue().equals(p.createdAtProperty().getValue())){
                p.saidaProperty().setValue("Não deu saída");
                return p.saidaProperty();
            }else{
                return p.saidaProperty();
            }
        });
        listProduto.getColumns().add(depthSaida);

        JFXTreeTableColumn<Produto, String> depthCategoryName = new JFXTreeTableColumn<>("Categoria");
        depthCategoryName.setPrefWidth(listProduto.getPrefWidth()/6);
        depthCategoryName.setCellValueFactory(param -> {
            Categoria c = ctdao.checkId(param.getValue().getValue().categoryIdProperty());
            if(c != null ){
                return c.nomeProperty();
            }
            return param.getValue().getValue().categoryIdProperty();
        });

        listProduto.getColumns().add(depthCategoryName);

        JFXTreeTableColumn<Produto, String> actionColumn = new JFXTreeTableColumn<>("Ações");
        actionColumn.setId("columnAcion");
        actionColumn.setPrefWidth(listProduto.getPrefWidth()/3);
        Callback<TreeTableColumn<Produto, String>, TreeTableCell<Produto, String>> cellFactory
        = //
        new Callback<TreeTableColumn<Produto, String>, TreeTableCell<Produto, String>>() {
            @Override
            public TreeTableCell call(final TreeTableColumn<Produto, String> param) {
                final TreeTableCell<Produto, String> cell = new TreeTableCell<Produto, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        //System.out.println(getTreeTableRow().getItem().allString());
                        VBox vbox = new VBox(5);
                        HBox hBox = new HBox(5);
                        HBox hBox2 = new HBox(5);
                        HBox hBox3 = new HBox(5);
                        vbox.setPadding(new Insets(20,0,0,0));
                        hBox.setPadding(new Insets(0,0,0,0));
                        hBox2.setPadding(new Insets(0,0,0,0));
                        hBox3.setPadding(new Insets(0,0,0,0));

                        String id = getTreeTableRow().getItem().idProperty().getValue();

                        JFXButton btn = new JFXButton("Alterar Foto");
                        btn.setId("btnAlteraProduto");
                        btn.setButtonType(JFXButton.ButtonType.RAISED);
                        btn.setMinWidth(vbox.getPrefWidth());
                        btn.setOnAction(event -> {
                            //Ações do botão aqui, fazer depois
                            String path = uploadImage(id);
                            if(null != path) {
                                if (changeImage(depthPhoto, path)) {
                                    snackbar("Imagem alterada com Sucesso");
                                } else {
                                    snackbar("Erro no caminho");

                                }
                            }
                        });

                        /** Alterar o Nome do produto*/

                        JFXButton btn2 = new JFXButton("Alterar Nome");
                        btn2.setId("btnAlteraProduto");
                        btn2.setButtonType(JFXButton.ButtonType.RAISED);

                        JFXButton btnConfirma = genericButton("Alterar", "#00BF5D");

                        JFXButton btnCancel = genericButton("Cancelar" ,"#FF4043");

                        JFXTextField newName = new JFXTextField();
                        newName.setLabelFloat(true);
                        newName.setPromptText("Nome do Produto");

                        /** Ação do botão Alterar Nome */

                        btn2.setOnAction(event -> {
                            if(null == vboxProdutoExtra || null == stackPaneProduto ){
                                vboxProdutoExtra = new VBox();
                                stackPaneProduto = new StackPane();
                            }

                            JFXDialogLayout content = new JFXDialogLayout();
                            content.setPrefSize(subJfxTabPane.getPrefWidth() / 4, subJfxTabPane.getPrefHeight() / 4);
                            content.setHeading(new Text("Digite o novo Nome do Produto"));
                            content.setBody(newName);
                            content.setActions(btnConfirma, btnCancel);
                            JFXDialog dialog = new JFXDialog(stackPaneProduto, content, JFXDialog.DialogTransition.CENTER);
                            btnConfirma.setOnAction(event1 -> {
                                if(newName.getText().length() > 0) {
                                    if(pdao.updateNome(newName.getText(), id)) {
                                        snackbar("Nome Alterado com Sucesso!");
                                    }else{
                                        snackbar("Falha ao alterar o nome");
                                    }

                                    atualizeListProducts();
                                    getAllProducts();
                                    dialog.close();
                                    vboxProduto.getChildren().removeAll(vboxProduto.getChildren());
                                    vboxProduto.getChildren().addAll(vboxProdutoExtra.getChildren());
                                    vboxProdutoExtra = null;
                                    stackPaneProduto = null;
                                }else{
                                    snackbar("Digite um nome pra Alterar");
                                }
                            });
                            btnCancel.setOnAction(event2 -> {
                                dialog.close();
                                vboxProduto.getChildren().removeAll(vboxProduto.getChildren());
                                vboxProduto.getChildren().addAll(vboxProdutoExtra.getChildren());});
                            dialog.setOverlayClose(false);

                            stackPaneProduto.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());
                            vboxProdutoExtra.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());

                            if(vboxProdutoExtra.getChildren().size() <= 0) {
                                vboxProdutoExtra.getChildren().addAll(vboxProduto.getChildren());
                                vboxProduto.getChildren().removeAll(vboxProduto.getChildren());
                                vboxProduto.getChildren().add(stackPaneProduto);
                                dialog.show();
                            }else{
                                dialog.show();
                            }
                        });

                        /** */

                        /** Alterar a categoria do produto*/

                        JFXButton btn3 = new JFXButton("Alterar Categoria");
                        btn3.setStyle("-fx-padding: 5px; -fx-font-size: 10px;");
                        btn3.setId("btnAlteraProduto");
                        btn3.setButtonType(JFXButton.ButtonType.RAISED);

                        JFXComboBox<Categoria> newCategoria = new JFXComboBox<>();

                        /** Ação do botão Alterar Categoria */

                        btn3.setOnAction(event -> {
                            if(null == vboxProdutoExtra || null == stackPaneProduto ){
                                vboxProdutoExtra = new VBox();
                                stackPaneProduto = new StackPane();
                            }

                            newCategoria.getItems().removeAll(newCategoria.getItems());
                            newCategoria.getItems().addAll(ctdao.getAllCategorys());
                            newCategoria.getSelectionModel().select(-1);
                            newCategoria.setPromptText("Categoria");

                            JFXDialogLayout content = new JFXDialogLayout();
                            content.setPrefSize(subJfxTabPane.getPrefWidth() / 4, subJfxTabPane.getPrefHeight() / 4);
                            content.setHeading(new Text("Selecione a nova Categoria do Produto"));
                            content.setBody(newCategoria);
                            content.setActions(btnConfirma, btnCancel);
                            JFXDialog dialog = new JFXDialog(stackPaneProduto, content, JFXDialog.DialogTransition.CENTER);
                            btnConfirma.setOnAction(event1 -> {
                                if(!newCategoria.getSelectionModel().isEmpty()) {
                                    String newCategoryId = newCategoria.getSelectionModel().getSelectedItem().idProperty().getValue();
                                    String oldCategoryId = getTreeTableRow().getItem().categoryIdProperty().getValue();
                                    if (!
                                            newCategoryId.equals(oldCategoryId)) {
                                        //System.out.println(newCategoryId);
                                        //System.out.println(oldCategoryId);
                                        if (pdao.updateCategoria(newCategoryId, id)) {
                                            snackbar("Categoria Alterado com Sucesso!");
                                        } else {
                                            snackbar("Falha ao alterar a categoria");
                                        }
                                        atualizeListProducts();
                                        getAllProducts();
                                        populateComboCategory();
                                        dialog.close();
                                        vboxProduto.getChildren().removeAll(vboxProduto.getChildren());
                                        vboxProduto.getChildren().addAll(vboxProdutoExtra.getChildren());
                                        vboxProdutoExtra = null;
                                        stackPaneProduto = null;
                                    } else {
                                        snackbar("Escolha uma categoria diferente da atual!");
                                    }
                                }else{
                                    snackbar("Escolha uma categoria");
                                }
                            });
                            btnCancel.setOnAction(event2 -> {
                                dialog.close();
                                vboxProduto.getChildren().removeAll(vboxProduto.getChildren());
                                vboxProduto.getChildren().addAll(vboxProdutoExtra.getChildren());});
                            dialog.setOverlayClose(false);

                            stackPaneProduto.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());
                            vboxProdutoExtra.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());

                            if(vboxProdutoExtra.getChildren().size() <= 0) {
                                vboxProdutoExtra.getChildren().addAll(vboxProduto.getChildren());
                                vboxProduto.getChildren().removeAll(vboxProduto.getChildren());
                                vboxProduto.getChildren().add(stackPaneProduto);
                                dialog.show();
                            }else{
                                dialog.show();
                            }
                        });

                        /** */

                        /** Alterar a unidade do produto*/

                        JFXButton btn5 = new JFXButton("Alterar Unidade");
                        btn5.setStyle("-fx-padding: 5px; -fx-font-size: 10px;");
                        btn5.setId("btnAlteraProduto");
                        btn5.setButtonType(JFXButton.ButtonType.RAISED);

                        JFXComboBox<Unidade> newUnidade = new JFXComboBox<>();

                        /** Ação do botão Alterar Unidade */

                        btn5.setOnAction(event -> {
                            if(null == vboxProdutoExtra || null == stackPaneProduto ){
                                vboxProdutoExtra = new VBox();
                                stackPaneProduto = new StackPane();
                            }

                            newUnidade.getItems().removeAll(newUnidade.getItems());
                            newUnidade.getItems().addAll(undao.getAllUnits());
                            newUnidade.getSelectionModel().select(-1);
                            newUnidade.setPromptText("Unidade");

                            JFXDialogLayout content = new JFXDialogLayout();
                            content.setPrefSize(subJfxTabPane.getPrefWidth() / 4, subJfxTabPane.getPrefHeight() / 4);
                            content.setHeading(new Text("Selecione a nova Unidade do Produto"));
                            content.setBody(newUnidade);
                            content.setActions(btnConfirma, btnCancel);
                            JFXDialog dialog = new JFXDialog(stackPaneProduto, content, JFXDialog.DialogTransition.CENTER);
                            btnConfirma.setOnAction(event1 -> {
                                if(!newUnidade.getSelectionModel().isEmpty()) {
                                    String newCategoryId = newUnidade.getSelectionModel().getSelectedItem().idProperty().getValue();
                                    String oldCategoryId = getTreeTableRow().getItem().unit_idProperty().getValue();
                                    if (!newCategoryId.equals(oldCategoryId)) {
                                        //System.out.println(newCategoryId);
                                        //System.out.println(oldCategoryId);
                                        if (pdao.updateUnidade(newCategoryId, id)) {
                                            snackbar("Unidade Alterado com Sucesso!");
                                        } else {
                                            snackbar("Falha ao alterar a unidade");
                                        }
                                        atualizeListProducts();
                                        getAllProducts();
                                        populateComboUnits();
                                        dialog.close();
                                        vboxProduto.getChildren().removeAll(vboxProduto.getChildren());
                                        vboxProduto.getChildren().addAll(vboxProdutoExtra.getChildren());
                                        vboxProdutoExtra = null;
                                        stackPaneProduto = null;
                                    } else {
                                        snackbar("Escolha uma categoria unidade da atual!");
                                    }
                                }else{
                                    snackbar("Escolha uma unidade");
                                }
                            });
                            btnCancel.setOnAction(event2 -> {
                                dialog.close();
                                vboxProduto.getChildren().removeAll(vboxProduto.getChildren());
                                vboxProduto.getChildren().addAll(vboxProdutoExtra.getChildren());});
                            dialog.setOverlayClose(false);

                            stackPaneProduto.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());
                            vboxProdutoExtra.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());

                            if(vboxProdutoExtra.getChildren().size() <= 0) {
                                vboxProdutoExtra.getChildren().addAll(vboxProduto.getChildren());
                                vboxProduto.getChildren().removeAll(vboxProduto.getChildren());
                                vboxProduto.getChildren().add(stackPaneProduto);
                                dialog.show();
                            }else{
                                dialog.show();
                            }
                        });

                        /** */

                        /** Alterar a referencia do produto*/

                        JFXButton btn4 = new JFXButton("Alterar Referência");
                        btn4.setStyle("-fx-padding: 5px; -fx-font-size: 10px;");
                        btn4.setId("btnAlteraProduto");
                        btn4.setButtonType(JFXButton.ButtonType.RAISED);

                        JFXTextField newReference = new JFXTextField();
                        newReference.setLabelFloat(true);
                        newReference.setPromptText("Nome da Referência");

                        btn4.setOnAction(event -> {
                            if(null == vboxProdutoExtra || null == stackPaneProduto ){
                                vboxProdutoExtra = new VBox();
                                stackPaneProduto = new StackPane();
                            }

                            JFXDialogLayout content = new JFXDialogLayout();
                            content.setPrefSize(subJfxTabPane.getPrefWidth() / 4, subJfxTabPane.getPrefHeight() / 4);
                            content.setHeading(new Text("Selecione a nova Categoria do Produto"));
                            content.setBody(newReference);
                            content.setActions(btnConfirma, btnCancel);
                            JFXDialog dialog = new JFXDialog(stackPaneProduto, content, JFXDialog.DialogTransition.CENTER);
                            btnConfirma.setOnAction(event1 -> {
                                if(newReference.getText().length() > 0) {
                                    if (pdao.updateReference(newReference.getText(), id)) {
                                        snackbar("Referência Alterada com Sucesso!");
                                    } else {
                                        snackbar("Falha ao alterar a referência");
                                    }
                                    atualizeListProducts();
                                    getAllProducts();
                                    dialog.close();
                                    vboxProduto.getChildren().removeAll(vboxProduto.getChildren());
                                    vboxProduto.getChildren().addAll(vboxProdutoExtra.getChildren());
                                    vboxProdutoExtra = null;
                                    stackPaneProduto = null;
                                } else {
                                    snackbar("Insira uma referência para Alterar!");
                                }
                            });
                            btnCancel.setOnAction(event2 -> {
                                dialog.close();
                                vboxProduto.getChildren().removeAll(vboxProduto.getChildren());
                                vboxProduto.getChildren().addAll(vboxProdutoExtra.getChildren());});
                            dialog.setOverlayClose(false);

                            stackPaneProduto.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());
                            vboxProdutoExtra.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());

                            if(vboxProdutoExtra.getChildren().size() <= 0) {
                                vboxProdutoExtra.getChildren().addAll(vboxProduto.getChildren());
                                vboxProduto.getChildren().removeAll(vboxProduto.getChildren());
                                vboxProduto.getChildren().add(stackPaneProduto);
                                dialog.show();
                            }else{
                                dialog.show();
                            }
                        });

                        /** */
                        hBox.getChildren().addAll(btn, btn2);
                        hBox2.getChildren().addAll(btn3, btn4);
                        hBox3.getChildren().addAll(btn5);
                        vbox.getChildren().addAll(hBox, hBox2, hBox3);

                        setGraphic(vbox);
                        setText(null);
                    }
                    }
                };
                return cell;
            }
        };
        actionColumn.setCellFactory(cellFactory);
        listProduto.getColumns().add(actionColumn);
    }

    private void createColumnsCliente(){
        JFXTreeTableColumn<Cliente, String> depthId = new JFXTreeTableColumn<>("Código");
        depthId.setPrefWidth(listCliente.getPrefWidth()/2);
        depthId.setCellValueFactory(param -> param.getValue().getValue().idProperty());
        listCliente.getColumns().add(depthId);

        JFXTreeTableColumn<Cliente, String> depthCliente = new JFXTreeTableColumn<>("Cliente");
        depthCliente.setPrefWidth(listCliente.getPrefWidth()/2);
        depthCliente.setCellValueFactory(param -> param.getValue().getValue().nomeProperty());
        listCliente.getColumns().add(depthCliente);

        JFXTreeTableColumn<Cliente, String> depthDateIn = new JFXTreeTableColumn<>("Criado em");
        depthDateIn.setPrefWidth(listCliente.getPrefWidth()/2);
        depthDateIn.setCellValueFactory(param -> param.getValue().getValue().date_inProperty());
        listCliente.getColumns().add(depthDateIn);

        JFXTreeTableColumn<Cliente, String> depthAction = new JFXTreeTableColumn<>("Ações");
        depthAction.setPrefWidth(listCliente.getPrefWidth()/6);
        Callback<TreeTableColumn<Cliente, String>, TreeTableCell<Cliente, String>> callback
                = //
                new Callback<TreeTableColumn<Cliente, String>, TreeTableCell<Cliente, String>>() {
                    @Override
                    public TreeTableCell call(final TreeTableColumn<Cliente, String> param) {
                        final TreeTableCell<Cliente, String> cell = new TreeTableCell<Cliente, String>() {
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    VBox vbox = new VBox(5);
                                    vbox.setPadding(new Insets(30,0,0,0));

                                    String id = getTreeTableRow().getItem().idProperty().getValue();

                                    JFXButton btnConfirma = genericButton("Alterar", "#00BF5D");

                                    JFXButton btnCancel = genericButton("Cancelar" ,"#FF4043");

                                    /** Alterar o Nome do Cliente*/

                                    JFXButton btn = new JFXButton("Alterar Nome");
                                    btn.setId("btnAlteraProduto");
                                    btn.setButtonType(JFXButton.ButtonType.RAISED);

                                    JFXTextField newName = new JFXTextField();
                                    newName.setLabelFloat(true);
                                    newName.setPromptText("Novo Cliente");

                                    /** Ação do botão Alterar nome do Cliente */

                                    btn.setOnAction(event -> {
                                        if(null == vboxClienteExtra || null == stackPaneCliente ){
                                            vboxClienteExtra = new VBox();
                                            stackPaneCliente = new StackPane();
                                        }

                                        JFXDialogLayout content = new JFXDialogLayout();
                                        content.setPrefSize(subJfxTabPane.getPrefWidth() / 4, subJfxTabPane.getPrefHeight() / 4);
                                        content.setHeading(new Text("Digite o novo nome do Cliente"));
                                        content.setBody(newName);
                                        content.setActions(btnConfirma, btnCancel);
                                        JFXDialog dialog = new JFXDialog(stackPaneCliente, content, JFXDialog.DialogTransition.CENTER);
                                        btnConfirma.setOnAction(event1 -> {
                                            if(newName.getText().length() > 0) {
                                                if(cdao.updateName(newName.getText(), id)) {
                                                    snackbar("Nome Alterado com Sucesso!");
                                                }else{
                                                    snackbar("Falha ao alterar o cliente");
                                                }
                                                atualizeListClientes();
                                                getAllClientes();
                                                dialog.close();
                                                vboxCliente.getChildren().removeAll(vboxCliente.getChildren());
                                                vboxCliente.getChildren().addAll(vboxClienteExtra.getChildren());
                                                vboxClienteExtra = null;
                                                stackPaneCliente = null;
                                            }else{
                                                snackbar("Digite um nome pra Alterar");
                                            }
                                        });
                                        btnCancel.setOnAction(event2 -> {
                                            dialog.close();
                                            vboxCliente.getChildren().removeAll(vboxCliente.getChildren());
                                            vboxCliente.getChildren().addAll(vboxClienteExtra.getChildren());});
                                        dialog.setOverlayClose(false);

                                        stackPaneCliente.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());
                                        vboxClienteExtra.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());

                                        if(vboxClienteExtra.getChildren().size() <= 0) {
                                            vboxClienteExtra.getChildren().addAll(vboxCliente.getChildren());
                                            vboxCliente.getChildren().removeAll(vboxCliente.getChildren());
                                            vboxCliente.getChildren().add(stackPaneCliente);
                                            dialog.show();
                                        }else{
                                            dialog.show();
                                        }
                                    });

                                    vbox.getChildren().addAll(btn);

                                    setGraphic(vbox);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        depthAction.setCellFactory(callback);
        listCliente.getColumns().add(depthAction);
    }

    private void createColumnsEntrada() {
        JFXTreeTableColumn<Entrada, String> depthId = new JFXTreeTableColumn<>("Movimento");
        depthId.setPrefWidth(listEntrada.getPrefWidth() / 2);
        depthId.setCellValueFactory(param -> param.getValue().getValue().idEntradaProperty());
        listEntrada.getColumns().add(depthId);

        JFXTreeTableColumn<Entrada, String> depthDataEntrada = new JFXTreeTableColumn<>("Data e Hora");
        depthDataEntrada.setPrefWidth(listEntrada.getPrefWidth() / 2);
        depthDataEntrada.setCellValueFactory(param -> param.getValue().getValue().date_inProperty());
        listEntrada.getColumns().add(depthDataEntrada);

        JFXTreeTableColumn<Entrada, String> depthInformation = new JFXTreeTableColumn<>("Informações");
        depthInformation.setId("columnAction");
        depthInformation.setPrefWidth(listEntrada.getPrefWidth() / 2);
        Callback<TreeTableColumn<Entrada, String>, TreeTableCell<Entrada, String>> cellFactory
            = new Callback<TreeTableColumn<Entrada, String>, TreeTableCell<Entrada, String>>() {
                @Override
                public TreeTableCell<Entrada, String> call(TreeTableColumn<Entrada, String> param) {
                    final TreeTableCell<Entrada, String> cell = new TreeTableCell<Entrada, String>() {

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                JFXButton btn = new JFXButton("Visualizar");
                                btn.setId("btnAlteraProduto");
                                btn.setButtonType(JFXButton.ButtonType.RAISED);
                                btn.setOnAction(event -> {
                                    String id = getTreeTableRow().getItem().idEntradaProperty().getValue();
                                    showTableAllEntradaProdutos(id);
                                    getAllEntradaProdutos.addAll(pdao.getAllEntradasById(id));
                                });
                                setGraphic(btn);
                                setText(null);
                            }
                        }
                    };
                    return cell;
                }
            };
            depthInformation.setCellFactory(cellFactory);
            listEntrada.getColumns().add(depthInformation);
    }

    private void createColumnsSaida() {
        JFXTreeTableColumn<Saida, String> depthId = new JFXTreeTableColumn<>("Movimento");
        depthId.setPrefWidth(listSaida.getPrefWidth() / 2);
        depthId.setCellValueFactory(param -> param.getValue().getValue().id_saidaProperty());
        listSaida.getColumns().add(depthId);

        JFXTreeTableColumn<Saida, String> depthClient = new JFXTreeTableColumn<>("Cliente");
        depthClient.setPrefWidth(listSaida.getPrefWidth() / 2);
        depthClient.setCellValueFactory(param -> {
                    Cliente c = cdao.checkId(param.getValue().getValue().client_idProperty());
                    if (c != null) {
                        return c.nomeProperty();
                    }
                    return param.getValue().getValue().client_idProperty();
                }
        );
        listSaida.getColumns().add(depthClient);

        JFXTreeTableColumn<Saida, String> depthSaida = new JFXTreeTableColumn<>("Data e Hora");
        depthSaida.setPrefWidth(listSaida.getPrefWidth() / 2);
        depthSaida.setCellValueFactory(param -> param.getValue().getValue().date_outProperty());
        listSaida.getColumns().add(depthSaida);

        JFXTreeTableColumn<Saida, String> depthInformation = new JFXTreeTableColumn<>("Informações");
        depthInformation.setId("columnAction");
        depthInformation.setPrefWidth(listSaida.getPrefWidth() / 2);
        Callback<TreeTableColumn<Saida, String>, TreeTableCell<Saida, String>> cellFactory
                = new Callback<TreeTableColumn<Saida, String>, TreeTableCell<Saida, String>>() {
            @Override
            public TreeTableCell<Saida, String> call(TreeTableColumn<Saida, String> param) {
                final TreeTableCell<Saida, String> cell = new TreeTableCell<Saida, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            JFXButton btn = new JFXButton("Visualizar");
                            btn.setId("btnAlteraProduto");
                            btn.setButtonType(JFXButton.ButtonType.RAISED);
                            btn.setOnAction(event -> {
                                String id = getTreeTableRow().getItem().id_saidaProperty().getValue();
                                String name = "";
                                Cliente c = cdao.checkId(getTreeTableRow().getItem().client_idProperty());
                                if (c != null) {
                                    name = c.nomeProperty().getValue();
                                }else{
                                    name = "";
                                }
                                showTableAllSaidaProdutos(id, name);
                                getAllSaidaProdutos.addAll(pdao.getAllSaidasById(id));
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        depthInformation.setCellFactory(cellFactory);
        listSaida.getColumns().add(depthInformation);
    }

    private void createColumnsCategoria() {
        JFXTreeTableColumn<Categoria, String> depthId = new JFXTreeTableColumn<>("Código");
        depthId.setPrefWidth(listCategoria.getPrefWidth() / 2);
        depthId.setCellValueFactory(param -> param.getValue().getValue().idProperty());
        listCategoria.getColumns().add(depthId);

        JFXTreeTableColumn<Categoria, String> depthName = new JFXTreeTableColumn<>("Nome");
        depthName.setPrefWidth(listCategoria.getPrefWidth() / 2);
        depthName.setCellValueFactory(param -> param.getValue().getValue().nomeProperty());
        listCategoria.getColumns().add(depthName);

        JFXTreeTableColumn<Categoria, String> depthDate = new JFXTreeTableColumn<>("Criado em");
        depthDate.setPrefWidth(listCategoria.getPrefWidth() / 2);
        depthDate.setCellValueFactory(param -> param.getValue().getValue().date_inProperty());
        listCategoria.getColumns().add(depthDate);

        JFXTreeTableColumn<Categoria, String> depthAction = new JFXTreeTableColumn<>("Ações");
        depthAction.setPrefWidth(listCategoria.getPrefWidth() / 6);
        Callback<TreeTableColumn<Categoria, String>, TreeTableCell<Categoria, String>> callback
                = //
                new Callback<TreeTableColumn<Categoria, String>, TreeTableCell<Categoria, String>>() {
                    @Override
                    public TreeTableCell call(final TreeTableColumn<Categoria, String> param) {
                        final TreeTableCell<Categoria, String> cell = new TreeTableCell<Categoria, String>() {
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    VBox vbox = new VBox(5);
                                    vbox.setPadding(new Insets(30,0,0,0));

                                    String id = getTreeTableRow().getItem().idProperty().getValue();

                                    JFXButton btnConfirma = genericButton("Alterar", "#00BF5D");

                                    JFXButton btnCancel = genericButton("Cancelar" ,"#FF4043");

                                    /** Alterar o Nome da Categoria*/

                                    JFXButton btn = new JFXButton("Alterar Nome");
                                    btn.setId("btnAlteraProduto");
                                    btn.setButtonType(JFXButton.ButtonType.RAISED);

                                    JFXTextField newName = new JFXTextField();
                                    newName.setLabelFloat(true);
                                    newName.setPromptText("Nova Categoria");

                                    /** Ação do botão Alterar nome da Categoria */

                                    btn.setOnAction(event -> {
                                        if(null == vboxCategoriaExtra || null == stackPaneCategoria ){
                                            vboxCategoriaExtra = new VBox();
                                            stackPaneCategoria = new StackPane();
                                        }

                                        JFXDialogLayout content = new JFXDialogLayout();
                                        content.setPrefSize(subJfxTabPane.getPrefWidth() / 4, subJfxTabPane.getPrefHeight() / 4);
                                        content.setHeading(new Text("Digite o novo nome da Categoria"));
                                        content.setBody(newName);
                                        content.setActions(btnConfirma, btnCancel);
                                        JFXDialog dialog = new JFXDialog(stackPaneCategoria, content, JFXDialog.DialogTransition.CENTER);
                                        btnConfirma.setOnAction(event1 -> {
                                            if(newName.getText().length() > 0) {
                                                if(ctdao.updateName(newName.getText(), id)) {
                                                    snackbar("Categoria Alterado com Sucesso!");
                                                }else{
                                                    snackbar("Falha ao alterar a categoria");
                                                }
                                                atualizeListCategorias();
                                                getAllCategorias();
                                                atualizeListProducts();
                                                getAllProducts();
                                                populateComboCategory();
                                                dialog.close();
                                                vboxCategoria.getChildren().removeAll(vboxCategoria.getChildren());
                                                vboxCategoria.getChildren().addAll(vboxCategoriaExtra.getChildren());
                                                vboxCategoriaExtra = null;
                                                stackPaneCategoria = null;
                                            }else{
                                                snackbar("Digite um nome pra Alterar");
                                            }
                                        });
                                        btnCancel.setOnAction(event2 -> {
                                            dialog.close();
                                            vboxCategoria.getChildren().removeAll(vboxCategoria.getChildren());
                                            vboxCategoria.getChildren().addAll(vboxCategoriaExtra.getChildren());});
                                        dialog.setOverlayClose(false);

                                        stackPaneCategoria.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());
                                        vboxCategoriaExtra.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());

                                        if(vboxCategoriaExtra.getChildren().size() <= 0) {
                                            vboxCategoriaExtra.getChildren().addAll(vboxCategoria.getChildren());
                                            vboxCategoria.getChildren().removeAll(vboxCategoria.getChildren());
                                            vboxCategoria.getChildren().add(stackPaneCategoria);
                                            dialog.show();
                                        }else{
                                            dialog.show();
                                        }
                                    });

                                    vbox.getChildren().addAll(btn);

                                    setGraphic(vbox);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        depthAction.setCellFactory(callback);
        listCategoria.getColumns().addAll(depthAction);
    }

    private void createColumnsUnidade() {
        JFXTreeTableColumn<Unidade, String> depthId = new JFXTreeTableColumn<>("Código");
        depthId.setPrefWidth(listUnidade.getPrefWidth() / 2);
        depthId.setCellValueFactory(param -> param.getValue().getValue().idProperty());
        listUnidade.getColumns().add(depthId);

        JFXTreeTableColumn<Unidade, String> depthUnit = new JFXTreeTableColumn<>("Unidade");
        depthUnit.setPrefWidth(listUnidade.getPrefWidth() / 2);
        depthUnit.setCellValueFactory(param -> param.getValue().getValue().nomeProperty());
        listUnidade.getColumns().add(depthUnit);

        JFXTreeTableColumn<Unidade, String> depthDate = new JFXTreeTableColumn<>("Criado em");
        depthDate.setPrefWidth(listUnidade.getPrefWidth() / 2);
        depthDate.setCellValueFactory(param -> param.getValue().getValue().date_inProperty());
        listUnidade.getColumns().add(depthDate);

        JFXTreeTableColumn<Unidade, String> depthAction = new JFXTreeTableColumn<>("Ações");
        depthAction.setPrefWidth(listUnidade.getPrefWidth() / 6);
        Callback<TreeTableColumn<Unidade, String>, TreeTableCell<Unidade, String>> callback
                = //
                new Callback<TreeTableColumn<Unidade, String>, TreeTableCell<Unidade, String>>() {
                    @Override
                    public TreeTableCell call(final TreeTableColumn<Unidade, String> param) {
                        final TreeTableCell<Unidade, String> cell = new TreeTableCell<Unidade, String>() {
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    VBox vbox = new VBox(5);
                                    vbox.setPadding(new Insets(30,0,0,0));

                                    String id = getTreeTableRow().getItem().idProperty().getValue();

                                    JFXButton btnConfirma = genericButton("Alterar", "#00BF5D");

                                    JFXButton btnCancel = genericButton("Cancelar" ,"#FF4043");

                                    /** Alterar o Nome da Unidade*/

                                    JFXButton btn = new JFXButton("Alterar Nome");
                                    btn.setId("btnAlteraProduto");
                                    btn.setButtonType(JFXButton.ButtonType.RAISED);

                                    JFXTextField newName = new JFXTextField();
                                    newName.setLabelFloat(true);
                                    newName.setPromptText("Nova Unidade");

                                    /** Ação do botão Alterar nome do Cliente */

                                    btn.setOnAction(event -> {
                                        if(null == vboxUnidadeExtra || null == stackPaneUnidade ){
                                            vboxUnidadeExtra = new VBox();
                                            stackPaneUnidade = new StackPane();
                                        }

                                        JFXDialogLayout content = new JFXDialogLayout();
                                        content.setPrefSize(subJfxTabPane.getPrefWidth() / 4, subJfxTabPane.getPrefHeight() / 4);
                                        content.setHeading(new Text("Digite a nova Categoria"));
                                        content.setBody(newName);
                                        content.setActions(btnConfirma, btnCancel);
                                        JFXDialog dialog = new JFXDialog(stackPaneUnidade, content, JFXDialog.DialogTransition.CENTER);
                                        btnConfirma.setOnAction(event1 -> {
                                            if(newName.getText().length() > 0) {
                                                if(undao.updateName(newName.getText(), id)) {
                                                    snackbar("Nome Alterado com Sucesso!");
                                                }else{
                                                    snackbar("Falha ao alterar a categoria");
                                                }
                                                atualizeListUnidades();
                                                getAllUnidades();
                                                atualizeListProducts();
                                                getAllProducts();
                                                populateComboUnits();
                                                dialog.close();
                                                vboxUnidade.getChildren().removeAll(vboxUnidade.getChildren());
                                                vboxUnidade.getChildren().addAll(vboxUnidadeExtra.getChildren());
                                                vboxUnidadeExtra = null;
                                                stackPaneUnidade = null;
                                            }else{
                                                snackbar("Digite um nome pra Alterar");
                                            }
                                        });
                                        btnCancel.setOnAction(event2 -> {
                                            dialog.close();
                                            vboxUnidade.getChildren().removeAll(vboxUnidade.getChildren());
                                            vboxUnidade.getChildren().addAll(vboxUnidadeExtra.getChildren());});
                                        dialog.setOverlayClose(false);

                                        stackPaneUnidade.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());
                                        vboxUnidadeExtra.setPrefSize(subJfxTabPane.getPrefWidth(), subJfxTabPane.getPrefHeight());

                                        if(vboxUnidadeExtra.getChildren().size() <= 0) {
                                            vboxUnidadeExtra.getChildren().addAll(vboxUnidade.getChildren());
                                            vboxUnidade.getChildren().removeAll(vboxUnidade.getChildren());
                                            vboxUnidade.getChildren().add(stackPaneUnidade);
                                            dialog.show();
                                        }else{
                                            dialog.show();
                                        }
                                    });

                                    vbox.getChildren().addAll(btn);

                                    setGraphic(vbox);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        depthAction.setCellFactory(callback);
        listUnidade.getColumns().add(depthAction);
    }

    /**
     * Thread para quando for verificar os campos inseridos
     */
    class initiate implements Runnable{
        @Override
        public void run() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if(jfxSnackbarOld != null){
                        if(jfxSnackbarOld.isVisible()){
                            jfxSnackbarOld.unregisterSnackbarContainer(anchorPane);
                            jfxSnackbarOld = null;
                        }
                    }
                    jfxSnackbar = new JFXSnackbar(anchorPane);
                    jfxSnackbar.show(messageSnackBar , "Fechar", 4000, setEventHandler());
                    jfxSnackbarOld = jfxSnackbar;
                }
            });
        }
    }

    class report implements Runnable{
        private int positionReport;
        private HashMap map;
        private boolean positive;
        public report setPositionReport(int positionReport){ this.positionReport = positionReport; return this;}
        public report setHashMap(HashMap map) {this.map = map; return this;}
        public report setPositionCategory(boolean positive) {this.positive = positive; return this;}
        @Override
        public void run() {
            try {
                new PrintReport().setPositionReport(positionReport).addMap(map).setPositiveCategory(positive).showReport(sizeScreen());
            } catch ( JRException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void snackbar(String message){
        Thread thInitiate = new Thread(new initiate());
        messageSnackBar = message;
        thInitiate.start();
    }

    private EventHandler setEventHandler(){
        eventHandler = new EventHandler() {
            @Override
            public void handle(Event event) {
                jfxSnackbar.close();
            }
        };
        return eventHandler;
    }

    /** Método Genérico para criação de campos*/

    private JFXTextField genericTextField(String name){
        JFXTextField jfxTextField = new JFXTextField();
        jfxTextField.setLabelFloat(true);
        jfxTextField.setPromptText(name);
        return jfxTextField;
    }

    /** */

    /** Método Genérico para criação de botão padrão azul claro*/

    private JFXButton genericButton(String name, String color){
        JFXButton button = new JFXButton(name);
        button.setStyle("-fx-background-color:"+color+";\n" +
                "    -fx-background-insets: 0,1,2,3;\n" +
                "    -fx-background-radius: 3,2,2,2;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-font-size: 12px;");
        return button;
    }

    /** */

    private void atualizeListUsuarios(){
        final TreeItem<Usuario> usuarioTreeItem = new RecursiveTreeItem<Usuario>(usuarios, RecursiveTreeObject::getChildren);
        listUsuario.setRoot(usuarioTreeItem);
        listUsuario.setShowRoot(false);
    }

    private void atualizeListProducts(){
        final TreeItem<Produto> produtoTreeItem = new RecursiveTreeItem<Produto>(produtos, RecursiveTreeObject::getChildren);
        listProduto.setRoot(produtoTreeItem);
        listProduto.setShowRoot(false);
    }

    private void atualizeListEntradas(){
        final TreeItem<Entrada> entradaTreeItem = new RecursiveTreeItem<Entrada>(entradas, RecursiveTreeObject::getChildren);
        listEntrada.setRoot(entradaTreeItem);
        listEntrada.setShowRoot(false);
    }

    private void atualizaListSaidas(){
        final TreeItem<Saida> saidaTreeItem = new RecursiveTreeItem<Saida>(saidas, RecursiveTreeObject::getChildren);
        listSaida.setRoot(saidaTreeItem);
        listSaida.setShowRoot(false);
    }

    private void atualizeListClientes(){
        final TreeItem<Cliente> clienteTreeItem = new RecursiveTreeItem<Cliente>(clientes, RecursiveTreeObject::getChildren);
        listCliente.setRoot(clienteTreeItem);
        listCliente.setShowRoot(false);
    }

    private void atualizeListCategorias(){
        final TreeItem<Categoria> categoriaTreeItem = new RecursiveTreeItem<Categoria>(categorias, RecursiveTreeObject::getChildren);
        listCategoria.setRoot(categoriaTreeItem);
        listCategoria.setShowRoot(false);
    }

    private void atualizeListUnidades(){
        final TreeItem<Unidade> unidadeTreeItem = new RecursiveTreeItem<Unidade>(unidades, RecursiveTreeObject::getChildren);
//        listUnidade.setRoot(unidadeTreeItem);
//        listUnidade.setShowRoot(false);
    }

    private String uploadImage(String id){
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Imagens (.png, .jpg, .jpeg)", "*.png", "*.jpeg", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(app);
        if (file != null) {
            pdao.uploadImage("file:"+file.getAbsolutePath(), id);
            return file.getPath();
        }
    return null;
    }

    private boolean changeImage(JFXTreeTableColumn depthPhoto, String imagePath){
        //imagePath = "/storage/oculos.jpg";
        URL url = null;
        File file = new File(imagePath);
        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            //System.out.println(imagePath);
            ImageView graphic;
            graphic = new ImageView(new Image(url.toExternalForm()));
            graphic.setFitWidth(depthPhoto.getPrefWidth());
            graphic.setFitHeight(100);
            getAllProducts();
            atualizeListProducts();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void labelSenhaProperty(){
        //System.out.println(senhaUsuario.getText());
        labelSenha.setText(senhaUsuario.getText());
    }

    @FXML
    public void btnToggle(){
        if(toggle.isSelected()) {
            labelSenha.setVisible(true);
        }else{
            labelSenha.setVisible(false);
        }
    }

    @FXML
    public void btnToggleRelatorioCategoria(){
        if(toggleCategoriaRelatorio.isSelected()){
            relatorioCategoria = true;
        }else{
            relatorioCategoria = false;
        }
    }

    private void getAllUsuarios() { usuarios.clear(); usuarios.addAll( udao.getAllUsers()); }

    private void getAllProducts(){ produtos.clear(); produtos.addAll( pdao.getAllProducts()); }

    private void getAllClientes(){ clientes.clear(); clientes.addAll(cdao.getAllClients()); }

    private void getAllCategorias(){ categorias.clear(); categorias.addAll(ctdao.getAllCategorys()); }

    private void getAllUnidades(){ unidades.clear(); unidades.addAll(undao.getAllUnits()); }

    private void getAllEntradas(){ entradas.clear(); entradas.addAll(edao.getAllEntradas()); }

    private void getAllSaidas(){ saidas.clear(); saidas.addAll(odao.getAllSaidas()); }
}
