/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modal;

import dao.DAO;
import dao.ItemDAO;
import entity.Item;
import entity.Vouncher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wizard
 */
public class ItemModal implements ItemDAO{

    @Override
    public List<Item> getItems() {
        List<Item> itemlist = new ArrayList();
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                        .prepareStatement
        ("select * from items");
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                itemlist.add(new Item(rs.getInt("id"), rs.getString("name"),
                rs.getInt("price"), rs.getInt("quantity"), rs.getString("description"), rs.getInt("category_id")));
            }
            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return itemlist;
    }

    @Override
    public void addItem(Item item) {
         try {
            PreparedStatement st = DAO.getDAO().getCon()
                    .prepareStatement("insert into items(name, price, quantity, description, category_id) values (?,?,?,?,?);");
            st.setString(1, item.getName());
            st.setInt(2, item.getPrice());
            st.setInt(3, item.getQuantity());
            st.setString(4, item.getDescription());
            st.setInt(5, item.getCategory_id());
            
            st.execute();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteItem(int id) {
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                    .prepareStatement("delete from items where id = ?");
            st.setInt(1, id);
            
            st.execute();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public int getItemID(String name) {
        int id = 0;
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                    .prepareStatement("select id from items where name = ?");
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                id = rs.getInt("id");
            }
            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return id;
    }

    @Override
    public List<Item> getItemsByCategoryID(int category_id) {
        List<Item> itemlist = new ArrayList();
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                        .prepareStatement("select * from items where category_id = ?");
            st.setInt(1, category_id);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                itemlist.add(new Item(rs.getInt("id"), rs.getString("name"),
                rs.getInt("price"), rs.getInt("quantity"), rs.getString("description"), rs.getInt("category_id")));
            }
            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return itemlist;
    }

    @Override
    public Item getItemByID(int id) {
        Item item = null;
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                        .prepareStatement("select * from items where id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                item = new Item(rs.getInt("id"), rs.getString("name"), rs.getInt("price"), rs.getInt("quantity"), rs.getString("description"),
                rs.getInt("category_id"));
            }
            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return item;
    }

    @Override
    public void refreshItemQuantity(Vouncher vouncher) {
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                        .prepareStatement("call subtract_quantity(?,?)");
            st.setString(1, vouncher.getItemname());
            st.setInt(2, vouncher.getId());
            
            st.execute();
            
            
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void updateItem(Item item) {
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                    .prepareStatement("update items set price = ?, quantity = ?, description = ? where id = ?");
            st.setInt(1, item.getPrice());
            st.setInt(2, item.getQuantity());
            st.setString(3, item.getDescription());
            st.setInt(4, item.getItemcode());
            st.executeUpdate();
            
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
