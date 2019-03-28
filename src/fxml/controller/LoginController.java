package fxml.controller;

import app.App;
import com.jfoenix.controls.*;
import connection.Db;
import dao.UserDAO;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private Db db = new Db();

    @FXML
    private JFXSpinner jfxSpinner;

    @FXML
    private Label labelSenha;

    @FXML
    private JFXToggleButton toggle;

    @FXML
    private JFXButton btnEntrar;

    @FXML
    private JFXTextField login;

    @FXML
    private JFXPasswordField senha;

    @FXML
    private AnchorPane anchorPane;

    private JFXSnackbar jfxSnackbar = null;
    private JFXSnackbar jfxSnackbarOld = null;
    private Thread th;
    private Thread thInitiate;
    private Thread thDisable;
    private Thread thAble;

    private Usuario usuario;

    private UserDAO userDAO = new UserDAO();

    public LoginController() throws IOException {
    }

    /**
     * Thread para desabilitar os campos e rodar o spinner
     */
    class disable implements Runnable{
        @Override
        public void run() {
            login.setDisable(true);
            senha.setDisable(true);
            btnEntrar.setDisable(true);
            jfxSpinner.setVisible(true);
        }
    }

    /**
     * Thread para habilitar os campos e rodar o spinner
     */
    class able implements Runnable{
        @Override
        public void run() {
            login.setDisable(false);
            senha.setDisable(false);
            btnEntrar.setDisable(false);
            jfxSpinner.setVisible(false);
        }
    }

    /**
     * Thread para quando verificado o usuário, desabilitar o botão e acionar o spinner
     */
    class spinner implements Runnable{
        @Override
        public void run() {
            btnEntrar.setDisable(true);
            jfxSpinner.setVisible(true);
        }
    }
    /**
     * Thread para quando for verificar no banco se o usuário existe
     */
    class initiate implements Runnable{
        @Override
        public void run() {
            usuario = userDAO.checkId(login.getText());
            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    thAble.start();
                    if(null != usuario){
                        thDisable.interrupt();
                        if(senha.getText().equals(usuario.passwordProperty().getValue())){
                            th.start();
                            jfxSnackbar.show("Você está logado", 3000);
                            jfxSnackbarOld = jfxSnackbar;
                            App.getPrimaryStage().close();
                            Parent root = null;
                            try {
                                root = new FXMLLoader().load(getClass().getResource("/fxml/MenuPrincipal.fxml"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            App.getPrimaryStage().setScene(new Scene(root, App.getDimension().getWidth(), App.getDimension().height));
                            App.getPrimaryStage().setMaximized(true);
                            App.getPrimaryStage().show();
                            App.getPrimaryStage().setResizable(false);
                        }else{
                            senha.requestFocus();
                            jfxSnackbar.show("Senha Incorreta", 3000);
                            jfxSnackbarOld = jfxSnackbar;
                        }
                    }else{
                        login.requestFocus();
                        jfxSnackbar.show("Usuário incorreto", 3000);
                        jfxSnackbarOld = jfxSnackbar;
                    }
                }
            });
        }
    }

    /**
     * Ação do botão
     */

    @FXML
    public void handleBtnEntrar(ActionEvent event){
        verificarCampos();
    }

    /**
     * Verifica se o campo está corretamente inserido e chama um JFXSnackBar para notificar
     */
    private void verificarCampos(){
        th = new Thread(new spinner());
        thInitiate = new Thread(new initiate());
        thDisable = new Thread(new disable());
        thAble = new Thread(new able());
        jfxSnackbar = new JFXSnackbar(anchorPane);
        if(jfxSnackbarOld != null){
            if(jfxSnackbarOld.isVisible()){
                jfxSnackbarOld.unregisterSnackbarContainer(anchorPane);
                jfxSnackbarOld = null;
            }
        }
        if (login.getText().isEmpty() || senha.getText().isEmpty()) {

            EventHandler eventHandler = new EventHandler() {
                @Override
                public void handle(Event event) {
                    jfxSnackbar.close();
                }
            };
            login.requestFocus();
            jfxSnackbar.show("Insira um usuário e uma senha", "Fechar", 3000, eventHandler);
            jfxSnackbarOld = jfxSnackbar;
        } else {
            thInitiate.start();
            thDisable.start();
        }
    }

    @FXML
    private void labelSenhaProperty(){
        labelSenha.setText(senha.getText());
    }

    @FXML
    public void btnToggle(){
        if(toggle.isSelected()) {
            labelSenha.setVisible(true);
        }else{
            labelSenha.setVisible(false);
        }
    }

    /**
     * Método para inciar o Controlador
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        db.isDbConnected();
        jfxSpinner.setVisible(false);
        /**
         * Método para inserir apenas números no campo
         */
    }


}
