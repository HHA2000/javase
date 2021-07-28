/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.User;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modal.UserModal;

/**
 * FXML Controller class
 *
 * @author wizard
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtusername;
    @FXML
    private Label lblusername;
    @FXML
    private Label lblpassword;
    @FXML
    private PasswordField txtpassword;
    @FXML
    private Pane paneLogin;
    @FXML
    private Pane paneRegister;
    @FXML
    private Label lbluserRegister;
    @FXML
    private Label lblpasswordRegister;
    @FXML
    private Label lblType;
    @FXML
    private TextField txtRegisterUsername;
    @FXML
    private ComboBox<String> comboRegisterType;
    @FXML
    private PasswordField txtRegisterPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadComboType();
        
    }    
    
    UserModal user = new UserModal();

    @FXML
    private void btnloginAction(ActionEvent event) {
        auth(event);
    }
    
    private void auth(ActionEvent event){
        String name = txtusername.getText().trim();
        String password = txtpassword.getText().trim();
        
        if(inputValidate()){
            
            if(user.checkValidUser(name)){
                lblusername.setVisible(false);
                
                if(user.login(name, password)){
                    ((Node)event.getSource()).getScene().getWindow().hide();
                    
                    switch(user.getUserType(name)){
                        case "admin" -> Window("Admin.fxml");
                        case "user" -> Window("User.fxml");
                    }
                }
                
                else{
                    lblpassword.setText("Password is not correct");
                    lblpassword.setVisible(true);
                }
                
            }
            else {
                lblusername.setText("User is not found");
                lblusername.setVisible(true);
            }
        }
    }
    
    private boolean inputValidate(){
        if(loginUserValidate() && loginPasswordValidate()){
            return true;
        }
        else {
            loginUserValidate();
            loginPasswordValidate();
            return false;
        }
    }
    
    private boolean loginUserValidate(){
        if("".equals(txtusername.getText().trim())){
            lblusername.setText("Type username");
            lblusername.setVisible(true);
            return false;
        }
        else {
            lblusername.setVisible(false);
            return true;
        }
    }
    
    private boolean loginPasswordValidate(){
        if("".equals(txtpassword.getText().trim())){
            lblpassword.setText("Type password");
            lblpassword.setVisible(true);
            return false;
        }
        else {
            lblpassword.setVisible(false);
            return true;
        }
    }
    
    private void Window(String path) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/" + path));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void btnRegisterAction(ActionEvent event) {
        if(registerInputValidate()){
            String name = txtRegisterUsername.getText().trim();
            if(!user.checkAllUser(name)){
                lbluserRegister.setVisible(false);
                String password = txtRegisterPassword.getText().trim();
                String type = comboRegisterType.getSelectionModel().getSelectedItem();

                user.addRequestedUser(new User(name, password, type));

                clearValue();
            }
            else{
                lbluserRegister.setText("User is already exist");
                lbluserRegister.setVisible(true);
            }
        }
    }
    
     private void clearValue(){
        txtRegisterUsername.setText("");
        txtRegisterPassword.setText("");
        comboRegisterType.getSelectionModel().clearSelection();
        comboRegisterType.setButtonCell(new ListCell<String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty) ;
                if (empty || item == null) {
                    setText("Type");
                } else {
                    setText(item);
                }
            }
        });
    }
    
    private boolean registerInputValidate() {
        if(registerUsername() && registerPassword() && registerType()){
            return true;
        }
        else {
            registerPassword();
            registerType();
            registerUsername();
            return false;
        }
        
    }
    
    private boolean registerUsername(){
        if("".equals(txtRegisterUsername.getText())){
            lbluserRegister.setText("Type username");
            lbluserRegister.setVisible(true);
            return false;
        }
        else {
            lbluserRegister.setVisible(false);
            return true;
        }
    }
    
    private boolean registerPassword() {
        if("".equals(txtRegisterPassword.getText())){
            lblpasswordRegister.setText("Type password");
            lblpasswordRegister.setVisible(true);
            return false;
        }
        else {
            lblpasswordRegister.setVisible(false);
            return true;
        }
    }
    
    private boolean registerType() {
        if(comboRegisterType.getSelectionModel().isEmpty()){
            lblType.setText("Select one type");
            lblType.setVisible(true);
            return false;
        }
        else {
            lblType.setVisible(false);
            return true;
        }
    }
    
    private void loadComboType() {
        List<String> typelist = Arrays.asList("admin", "user");
        ObservableList<String> obser = FXCollections.observableArrayList(typelist);
        comboRegisterType.setItems(obser);
    }
    
    @FXML
    private void btnCancelAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void btnRegister(ActionEvent event) {
        paneLogin.setVisible(false);
        paneRegister.setVisible(true);
    }

    @FXML
    private void btncancelRegister(ActionEvent event) {
        paneLogin.setVisible(true);
        paneRegister.setVisible(false);
    }
}
