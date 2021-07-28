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
public class Vouncher {
    private String itemname;
    private int quantity;
    private int price;
    private int id;
    private int category_id;

    public Vouncher(String itemname, int quantity, int price, int category_id) {
        this.itemname = itemname;
        this.quantity = quantity;
        this.price = price * quantity;
        this.category_id = category_id;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemname() {
        return itemname;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
    
    public int getCategory_id() {
        return category_id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
