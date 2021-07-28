/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Category;
import entity.Item;
import entity.Vouncher;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.util.converter.IntegerStringConverter;
import modal.CategoryModal;
import modal.ItemModal;
import modal.SaleModal;

/**
 * FXML Controller class
 *
 * @author wizard
 */
public class UserController implements Initializable {

    @FXML
    private TableView<Category> tableCategory;
    @FXML
    private TableColumn<Category, String> columnCategoryName;
    @FXML
    private TableView<Item> tableItem;
    @FXML
    private TableColumn<Item, String> columnItemName;
    @FXML
    private TableColumn<Item, Integer> columnItemPrice;
    @FXML
    private TableColumn<Item, String> columnItemQuantity;
    @FXML
    private TableColumn<Item, String> columnItemDescription;
    @FXML
    private TableView<Item> tableCart;
    @FXML
    private TableColumn<Item, String> columnCartItemName;
    @FXML
    private TableColumn<Item, Integer> columnCartItemQuantity;
    @FXML
    private TableColumn<Item, Integer> columnCartItemPrice;
    @FXML
    private Label lblCartItem;
    @FXML
    private Pane paneUser;
    @FXML
    private Pane paneVouncher;
    @FXML
    private TableView<Vouncher> tableVouncher;
    @FXML
    private TableColumn<Vouncher, String> columnVouncherItem;
    @FXML
    private TableColumn<Vouncher, Integer> columnVouncherQuantity;
    @FXML
    private TableColumn<Vouncher, Integer> columnVouncherPrice;
    @FXML
    private TableColumn<Vouncher, Integer> columnCartItemCategoryID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadCategoryTable();
        loadItemTable();
    }    
    
    CategoryModal category = new CategoryModal();
    
    private void loadCategoryTable(){
        List<Category> categorylist = category.getCategories();
        ObservableList<Category> obser = FXCollections.observableArrayList(categorylist);
        columnCategoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableCategory.setItems(obser);
    }
    
    ItemModal item = new ItemModal();
    
    private void loadItemTable() {
        List<Item> itemlist = item.getItems();
        ObservableList<Item> obser = FXCollections.observableArrayList(itemlist);
        columnItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnItemQuantity.setCellValueFactory(new PropertyValueFactory<>("quantityString"));
        columnItemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableItem.setItems(obser);
    }
    
    private void loadItemTableByCategoryID(int category_id){
        List<Item> itemlist = item.getItemsByCategoryID(category_id);
        ObservableList<Item> obser = FXCollections.observableArrayList(itemlist);
        columnItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnItemQuantity.setCellValueFactory(new PropertyValueFactory<>("quantityString"));
        columnItemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableItem.setItems(obser);
    }

    @FXML
    private void btnItemSearchByCategoryID(ActionEvent event) {
        loadItemTableByCategoryID(tableCategory.getSelectionModel().getSelectedItem().getId());
    }

    @FXML
    private void btnItemTableRefresh(ActionEvent event) {
        loadItemTable();
    }

    List<Item> cartItemlist = new ArrayList();
    
    @FXML
    private void btnAddtoCart(ActionEvent event) {
        cartItems();
        loadCartTable();
    }
    
    List<Integer> ids = new ArrayList();
    private void cartItems(){
        int id = tableItem.getSelectionModel().getSelectedItem().getItemcode();
        if(ids.contains(id)){
            lblCartItem.setText("Item already exists");
            lblCartItem.setVisible(true);
        }
        else{
            lblCartItem.setVisible(false);
            ids.add(id);
            Item ite = item.getItemByID(id);
            ite.setQuantity(1);
            cartItemlist.add(ite);
        }
    }
    
    private void loadCartTable(){
        ObservableList<Item> obser = FXCollections.observableArrayList(cartItemlist);
        columnCartItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnCartItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnCartItemQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnCartItemCategoryID.setCellValueFactory(new PropertyValueFactory<>("category_id"));
        tableCart.setItems(obser);
        
        columnCartItemQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    }

    @FXML
    private void btnCartItemRemove(ActionEvent event) {
        if(!tableCart.getSelectionModel().isEmpty()){
            lblCartItem.setVisible(false);
            ids.remove(tableCart.getSelectionModel().getFocusedIndex());
            cartItemlist.remove(tableCart.getSelectionModel().getFocusedIndex());
            loadCartTable();
        }
        else {
            lblCartItem.setText("Please select row");
            lblCartItem.setVisible(true);
        }
    }
    
    @FXML
    private void btnCheckOut(ActionEvent event) {
        paneUser.setVisible(false);
        paneVouncher.setVisible(true);
        
        loadVouncherTable();
    }

    private void loadVouncherTable(){
        List<Item> ii = tableCart.getItems();
        List<Vouncher> vouncherlist = ii.stream()
                .map(obj -> new Vouncher(obj.getName(), obj.getQuantity(), obj.getPrice(), obj.getCategory_id()))
                .collect(Collectors.toList());
        
        ObservableList<Vouncher> obser = FXCollections.observableArrayList(vouncherlist);
        columnVouncherItem.setCellValueFactory(new PropertyValueFactory<>("itemname"));
        columnVouncherPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnVouncherQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tableVouncher.setItems(obser);
    }

    SaleModal sale = new SaleModal();
    
    @FXML
    private void btnBuy(ActionEvent event) {
        if(tableVouncher.getItems().isEmpty()){
            cartItemlist.removeAll(cartItemlist);
            ids.removeAll(ids);
            loadCartTable();
            paneUser.setVisible(true);
            paneVouncher.setVisible(false);
        }
        else{
            tableVouncher.getItems().forEach(vouncher-> {
                sale.addSale(vouncher);
                item.refreshItemQuantity(vouncher);
            });
           
            cartItemlist.removeAll(cartItemlist);
            ids.removeAll(ids);
            loadCartTable();
            paneUser.setVisible(true);
            paneVouncher.setVisible(false);
        }
    }

    @FXML
    private void btnVouncherCancel(ActionEvent event) {
        paneUser.setVisible(true);
        paneVouncher.setVisible(false);
    }

    @FXML
    private void onEditChangeQuantity(TableColumn.CellEditEvent<Item, Integer> event) {
        tableCart.getSelectionModel().getSelectedItem().setQuantity(event.getNewValue());
    }
}
