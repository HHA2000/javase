/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Item;
import entity.Vouncher;
import java.util.List;

/**
 *
 * @author wizard
 */
public interface ItemDAO {
    public List<Item> getItems();
    public void addItem(Item item);
    public void deleteItem(int id);
    public int getItemID(String name);
    public Item getItemByID(int id);
    public List<Item> getItemsByCategoryID(int category_id);
    public void refreshItemQuantity(Vouncher vouncher);
    public void updateItem(Item item);
}
