/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author wizard
 */
public class Item {
    private int itemcode;
    private String name;
    private int price;
    private int quantity;
    private String description;
    private int category_id;
    private String quantityString;
    
    public Item(String name, int price, int quantity, String description, int category_id){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.category_id = category_id;
    }

    public Item(int itemcode, String name, int price, int quantity, String description,int category_id) {
        this.itemcode = itemcode;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.category_id = category_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public int getItemcode() {
        return itemcode;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
    
    public String getQuantityString(){
        if(quantity != 0){
            return quantityString = "In Stock";
        }
        else {
            return quantityString = "Out of Stock";
        }
    }

    public String getDescription() {
        return description;
    }
    
    
}
