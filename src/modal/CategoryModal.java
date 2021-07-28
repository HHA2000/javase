/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modal;

import dao.CategoryDAO;
import dao.DAO;
import entity.Category;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wizard
 */
public class CategoryModal implements CategoryDAO {

    @Override
    public List<Category> getCategories() {
        List<Category> categorylist = new ArrayList();
        try {
            PreparedStatement st = DAO.getDAO().getCon().prepareStatement("select * from categories");
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                categorylist.add(new Category(rs.getInt("id"), rs.getString("name")));
            }
            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return categorylist;
    }

    @Override
    public void addCategory(String name) {
         try {
            PreparedStatement st = DAO.getDAO().getCon().prepareStatement("insert into categories(name) values (?);");
            st.setString(1, name);
            st.execute();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void removeCategory(int id) {
        try {
            PreparedStatement st = DAO.getDAO().getCon().prepareStatement("delete from categories where id = ?");
            st.setInt(1, id);
            st.execute();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public int getCategoryID(String name) {
        int id = 0;
        try {
            PreparedStatement st = DAO.getDAO().getCon().prepareStatement("select id from categories where name = ?");
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
    public boolean ExistCategory(String name) {
        boolean exist = false;
        try {
            PreparedStatement st = DAO.getDAO().getCon().prepareStatement("select id from categories where name = ?");
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                exist = true;
            }
            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return exist;
    }

    @Override
    public void updateCategory(Category category) {
        try {
            PreparedStatement st = DAO.getDAO().getCon().prepareStatement("update categories set name = ? where id = ?");
            st.setString(1, category.getName());
            st.setInt(2, category.getId());
            st.execute();
           
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
