/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CategoryDAO;
import dao.UserDAO;
import entity.Category;
import entity.Item;
import entity.User;
import entity.Vouncher;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.util.converter.IntegerStringConverter;
import modal.CategoryModal;
import modal.ItemModal;
import modal.SaleModal;
import modal.UserModal;

/**
 * FXML Controller class
 *
 * @author wizard
 */
public class AdminController implements Initializable {

    @FXML
    private Pane paneUser;
    @FXML
    private TableView<User> tableUser;
    @FXML
    private TableColumn<User, String> columnUsername;
    @FXML
    private TableColumn<User, String> columnUserType;
    @FXML
    private TextField txtUsername;
    @FXML
    private ComboBox<String> comboType;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lbluserRegister;
    @FXML
    private Label lblpasswordRegister;
    @FXML
    private Label lblType;
    @FXML
    private Label lblSelectRow;
    @FXML
    private Pane paneItem;
    @FXML
    private Label lblCategory;
    @FXML
    private Label lblCategorySelectRow;
    @FXML
    private TableView<Category> tableCategories;
    @FXML
    private TableColumn<Category, String> columnCategoryName;
    @FXML
    private TextField txtCategory;
    @FXML
    private TextArea txtItemDescription;
    @FXML
    private TextField txtItemPrice;
    @FXML
    private Label lblItemPrice;
    @FXML
    private TextField txtItemQuantity;
    @FXML
    private Label lblItemQuantity;
    @FXML
    private TextField txtItemName;
    @FXML
    private Label lblItemname;
    @FXML
    private ComboBox<String> comboItemCategory;
    @FXML
    private Label lblItemCategory;
    @FXML
    private TableView<Item> tableItem;
    @FXML
    private TableColumn<Item, Integer> columnItemcode;
    @FXML
    private TableColumn<Item, String> columnItemname;
    @FXML
    private TableColumn<Item, Integer> columnItemquantity;
    @FXML
    private TableColumn<Item, Integer> columnItemprice;
    @FXML
    private TableColumn<Item, String> columnItemDescription;
    @FXML
    private Label lblSelectItem;
    @FXML
    private TableView<User> tableRequestedUser;
    @FXML
    private TableColumn<User, String> columnRequestedUsername;
    @FXML
    private TableColumn<User, String> columnRequestUsertype;
    @FXML
    private Label lblSelectRequestUser;
    @FXML
    private Pane paneSaleReport;
    @FXML
    private TableView<Category> tableSaleCategory;
    @FXML
    private TableColumn<Category, String> columnSaleCategoryname;
    @FXML
    private TableView<Vouncher> tableSales;
    @FXML
    private TableColumn<Vouncher, String> columnSalename;
    @FXML
    private TableColumn<Vouncher, Integer> columnSaleQuantity;
    @FXML
    private TableColumn<Vouncher, Integer> columnSalePrice;
    @FXML
    private Label lblSaleCategory;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadUserTable();
        loadRequestedUserTable();
        loadComboType();
        loadCategoryTable();
        loadComboItemCategory();
        loadItemTable();
        loadSaleCategoryTable();
        loadSaleTable();
    }    
    
    //------UserView----------
    
    UserDAO user = new UserModal();
    
     private void loadRequestedUserTable(){
        List<User> userlist = user.getRequestedUser();
        ObservableList<User> obser = FXCollections.observableArrayList(userlist);
        columnRequestedUsername.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnRequestUsertype.setCellValueFactory(new PropertyValueFactory<>("type"));
        
        tableRequestedUser.setItems(obser);
    }
    
    @FXML
    private void btnUserAccept(ActionEvent event) {
        if(!tableRequestedUser.getSelectionModel().isEmpty()){
            user.approveRequestedUser(tableRequestedUser.getSelectionModel().getSelectedItem().getId());
            loadRequestedUserTable();
            loadUserTable();
        }
        else{
            lblSelectRequestUser.setVisible(true);
        }
    }

    @FXML
    private void btnUserRemove(ActionEvent event) {
         if(!tableRequestedUser.getSelectionModel().isEmpty()){
            user.deleteUser(tableRequestedUser.getSelectionModel().getSelectedItem().getId());
            loadRequestedUserTable();
        }
        else{
            lblSelectRequestUser.setVisible(true);
        }
    }
    
    private void loadUserTable(){
        List<User> userlist = user.getValidUser();
        ObservableList<User> obser = FXCollections.observableArrayList(userlist);
        columnUsername.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnUserType.setCellValueFactory(new PropertyValueFactory<>("type"));
        
        tableUser.setItems(obser);
        
        columnUsername.setCellFactory(TextFieldTableCell.forTableColumn());
        columnUserType.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @FXML
    private void onEditChangeUserName(CellEditEvent<User, String> event) {
        tableUser.getSelectionModel().getSelectedItem().setName(event.getNewValue());
    }

    @FXML
    private void onEditChangeUserType(CellEditEvent<User, String> event) {
        tableUser.getSelectionModel().getSelectedItem().setType(event.getNewValue());
    }
    
    @FXML
    private void btnAddAction(ActionEvent event) {
        String name = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String type = comboType.getSelectionModel().getSelectedItem();
        
        if(registerInputValidate()){
            if(!user.checkAllUser(name)){
                user.addUser(new User(name, password, type));
                clearValue();
                loadUserTable();
            }
            else{
                lbluserRegister.setText("User is already exist");
                lbluserRegister.setVisible(true);
            }
        }
    }
    
    private void clearValue(){
        txtUsername.setText("");
        txtPassword.setText("");
        comboType.getSelectionModel().clearSelection();
        comboType.setButtonCell(new ListCell<String>(){
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
        if("".equals(txtUsername.getText())){
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
        if("".equals(txtPassword.getText())){
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
        if(comboType.getSelectionModel().isEmpty()){
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
        comboType.setItems(obser);
    }
    
    @FXML
    private void btnUpdateAction(ActionEvent event) {
        updateUser();
    }
    
    private void updateUser(){
        if(!tableUser.getSelectionModel().isEmpty()){
            lblSelectRow.setVisible(false);
            user.updateUser(tableUser.getSelectionModel().getSelectedItem());
            loadUserTable();
        }
        else{
            lblSelectRow.setVisible(true);
        }
    }
    
    @FXML
    private void btnRemoveUser(ActionEvent event) {
        removeUser();
    }
    
    private void removeUser(){
        if(!tableUser.getSelectionModel().isEmpty()){
            lblSelectRow.setVisible(false);
            user.deleteUser(tableUser.getSelectionModel().getSelectedItem().getId());
            loadUserTable();
        }
        else{
            lblSelectRow.setVisible(true);
        }
    }

    @FXML
    private void menuUser(ActionEvent event) {
        paneUser.setVisible(true);
        paneItem.setVisible(false);
        paneSaleReport.setVisible(false);
    }

    @FXML
    private void menuItem(ActionEvent event) {
        paneUser.setVisible(false);
        paneItem.setVisible(true);
        paneSaleReport.setVisible(false);
    }
    
    @FXML
    private void menuSaleReport(ActionEvent event) {
        paneUser.setVisible(false);
        paneItem.setVisible(false);
        paneSaleReport.setVisible(true);
    }

    //--------Category----------
    
    CategoryDAO category = new CategoryModal();
    
    private void loadCategoryTable(){
        List<Category> categorylist = category.getCategories();
        ObservableList<Category> obser = FXCollections.observableArrayList(categorylist);
        columnCategoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableCategories.setItems(obser);
        
        columnCategoryName.setCellFactory(TextFieldTableCell.forTableColumn());
    }
    
    @FXML
    private void onEditChangeCategoryName(CellEditEvent<Category, String> event) {
        tableCategories.getSelectionModel().getSelectedItem().setName(event.getNewValue());
    }

    @FXML
    private void btnCategoryAdd(ActionEvent event) {
        addCategory();
    }
    
    private void addCategory(){
        String name = txtCategory.getText();
        if(!name.isEmpty()){
            if(category.ExistCategory(name)){
                lblCategory.setText("Category is already exist");
                lblCategory.setVisible(true);
            }
            else{
                lblCategory.setVisible(false);
                category.addCategory(name.trim());
                loadCategoryTable();
                loadComboItemCategory();
                txtCategory.setText("");
            }
        }
        else {
            lblCategory.setVisible(true);
        }
    }

    @FXML
    private void btnCategoryRemove(ActionEvent event) {
        removeCategory();
    }
    
    private void removeCategory(){
        if(!tableCategories.getSelectionModel().isEmpty()){
            lblCategorySelectRow.setVisible(false);
            category.removeCategory(tableCategories.getSelectionModel().getSelectedItem().getId());
            loadCategoryTable();
        }
        else {
            lblCategorySelectRow.setVisible(true);
        }
    }

    @FXML
    private void btnCategoryUpdate(ActionEvent event) {
        updateCategory();
        loadCategoryTable();
        loadSaleCategoryTable();
    }
    
    private void updateCategory(){
        if(!tableCategories.getSelectionModel().isEmpty()){
            lblCategorySelectRow.setVisible(false);
            category.updateCategory(tableCategories.getSelectionModel().getSelectedItem());
            loadCategoryTable();
        }
        else{
            lblCategorySelectRow.setVisible(true);
        }
    }
    
    //--------------Item------------------
    
    ItemModal item = new ItemModal();

    @FXML
    private void btnItemRemove(ActionEvent event) {
        removeItem();
    }
    
    private void removeItem(){
        if(!tableItem.getSelectionModel().isEmpty()){
            lblSelectItem.setVisible(false);
            item.deleteItem(tableItem.getSelectionModel().getSelectedItem().getItemcode());
            loadItemTable();
        }
        else {
            lblSelectItem.setVisible(true);
        }
    }
    
    @FXML
    private void btnItemUpdate(ActionEvent event) {
        updateItem();
    }
    
    private void updateItem(){
        if(!tableItem.getSelectionModel().isEmpty()){
            lblSelectItem.setVisible(false);
            item.updateItem(tableItem.getSelectionModel().getSelectedItem());
            loadItemTable();
        }
        else{
            lblSelectItem.setVisible(true);
        }
    }

    @FXML
    private void btnItemAdd(ActionEvent event) {
              
        if(itemInputValidate()){
            String name = txtItemName.getText();
            int price = Integer.parseInt(txtItemPrice.getText());
            int quantity = Integer.parseInt(txtItemQuantity.getText());
            String description = txtItemDescription.getText();
            int category_id = category.getCategoryID(comboItemCategory.getSelectionModel().getSelectedItem());  
            
            item.addItem(new Item(name, price, quantity, description, category_id));
            loadItemTable();
            clearItemValue();
        }
    }
    
    private void clearItemValue(){
        txtItemName.setText("");
        txtItemPrice.setText("");
        txtItemQuantity.setText("");
        txtItemDescription.setText("");
        comboItemCategory.getSelectionModel().clearSelection();
        comboItemCategory.setButtonCell(new ListCell<String>(){
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
    
    private boolean itemInputValidate(){
        if(itemName() && itemQuantity() && itemPrice() && itemCategory()){
            return true;
        }
        else{
            itemName();
            itemQuantity();
            itemPrice();
            itemCategory();
            return false;
        }
    }
    
    private boolean itemName(){
        if("".equals(txtItemName.getText())){
            lblItemname.setText("Type item name");
            lblItemname.setVisible(true);
            return false;
        }
        else {
            lblItemname.setVisible(false);
            return true;
        }
    }
    
    private boolean itemQuantity() {
        String quantity = txtItemQuantity.getText();
        if("".equals(quantity)){
            lblItemQuantity.setText("Type Quantity");
            lblItemQuantity.setVisible(true);
            return false;
        }
        else {
            try {
                int amount = Integer.parseInt(quantity);

                if(amount == 0) {
                    lblItemQuantity.setVisible(true);
                    lblItemQuantity.setText("Write amount");
                    return false;
                }
                else {
                    lblItemQuantity.setVisible(false);
                    return true;
                }

            } catch(NumberFormatException ex) {
                lblItemQuantity.setText("Only Number accept");
                lblItemQuantity.setVisible(true);
                return false;
            }
        }
    }
    
    private boolean itemPrice() {
        String price = txtItemPrice.getText();
        if("".equals(price)){
            lblItemPrice.setVisible(true);
            lblItemPrice.setText("Type Price");
            return false;
        }
        else {
            try {
                int amount = Integer.parseInt(price);

                if(amount == 0) {
                    lblItemPrice.setVisible(true);
                    lblItemPrice.setText("Write amount");
                    return false;
                }
                else {
                    lblItemPrice.setVisible(false);
                    return true;
                }

            } catch(NumberFormatException ex) {
                lblItemPrice.setText("Only Number accept");
                lblItemPrice.setVisible(true);
                return false;
            }
        }
    }
    
    private boolean itemCategory() {
        if(comboItemCategory.getSelectionModel().isEmpty()){
            lblItemCategory.setVisible(true);
            return false;
        }
        else {
            lblItemCategory.setVisible(false);
            return true;
        }
    }
    
    private void loadComboItemCategory() {
        List<Category> categorylist = category.getCategories();
        ObservableList<String> obser = FXCollections.observableArrayList();
        categorylist.forEach(c->{
            obser.add(c.getName());
        });
        comboItemCategory.setItems(obser);
    }
    
    private void loadItemTable(){
        List<Item> itemlist = item.getItems();
        ObservableList<Item> obser = FXCollections.observableArrayList(itemlist);
        columnItemcode.setCellValueFactory(new PropertyValueFactory<>("itemcode"));
        columnItemname.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnItemprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnItemquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnItemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        tableItem.setItems(obser);
        
        columnItemprice.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        columnItemquantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        columnItemDescription.setCellFactory(TextFieldTableCell.forTableColumn());
    }
    
    @FXML
    private void onEditChangeItemQuantiity(CellEditEvent<Item, Integer> event) {
        tableItem.getSelectionModel().getSelectedItem().setQuantity(event.getNewValue());
    }

    @FXML
    private void onEditChangeItemPrice(CellEditEvent<Item, Integer> event) {
        tableItem.getSelectionModel().getSelectedItem().setPrice(event.getNewValue());
    }

    @FXML
    private void onEditChangeItemDescription(CellEditEvent<Item, String> event) {
        tableItem.getSelectionModel().getSelectedItem().setDescription(event.getNewValue());
    }

    @FXML
    private void btnSearchItem(ActionEvent event) {
        if(!tableCategories.getSelectionModel().isEmpty()){
            lblCategorySelectRow.setVisible(false);
            loadItemTableByCategory(tableCategories.getSelectionModel().getSelectedItem().getId());
        }
        else {
            lblCategorySelectRow.setVisible(true);
        }
    }
    
    private void loadItemTableByCategory(int categoryID){
        List<Item> itemlist = item.getItemsByCategoryID(categoryID);
        ObservableList<Item> obser = FXCollections.observableArrayList(itemlist);
        columnItemcode.setCellValueFactory(new PropertyValueFactory<>("itemcode"));
        columnItemname.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnItemprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnItemquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnItemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        tableItem.setItems(obser);
    }

    @FXML
    private void btnRefreshItemTable(ActionEvent event) {
        loadItemTable();
    }
    
    //-------------Sale-------------------
    
    SaleModal sale = new SaleModal();  
    
    private void loadSaleCategoryTable(){
        List<Category> categorylist = category.getCategories();
        ObservableList<Category> obser = FXCollections.observableArrayList(categorylist);
        columnSaleCategoryname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableSaleCategory.setItems(obser);
    }

    @FXML
    private void btnSaleCategorySearch(ActionEvent event) {
        if(!tableSaleCategory.getSelectionModel().isEmpty()){
            lblSaleCategory.setVisible(false);
            loadSaleTableByCategoryID(tableSaleCategory.getSelectionModel().getSelectedItem().getId());
        }
        else{
            lblSaleCategory.setVisible(true);
        }
    }

    @FXML
    private void btnSaleRefresh(ActionEvent event) {
        loadSaleTable();
    }
    
    private void loadSaleTableByCategoryID(int category_id){
        List<Vouncher> salelist = sale.getSalesByCategory(category_id);
        ObservableList<Vouncher> obser = FXCollections.observableArrayList(salelist);
        columnSalename.setCellValueFactory(new PropertyValueFactory<>("itemname"));
        columnSaleQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnSalePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableSales.setItems(obser);
    }
    
    private void loadSaleTable(){
        List<Vouncher> salelist = sale.getSales();
        ObservableList<Vouncher> obser = FXCollections.observableArrayList(salelist);
        columnSalename.setCellValueFactory(new PropertyValueFactory<>("itemname"));
        columnSaleQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnSalePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableSales.setItems(obser);
    }
}
