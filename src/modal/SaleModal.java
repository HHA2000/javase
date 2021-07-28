/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modal;

import dao.DAO;
import dao.SaleDAO;
import entity.Vouncher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wizard
 */
public class SaleModal implements SaleDAO{

    @Override
    public void addSale(Vouncher vouncher) {
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                    .prepareStatement("insert into sales(name, price, quantity, category_id) value (?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, vouncher.getItemname());
            st.setInt(2, vouncher.getPrice());
            st.setInt(3, vouncher.getQuantity());
            st.setInt(4, vouncher.getCategory_id());
            st.executeUpdate();
            
            ResultSet rs = st.getGeneratedKeys();
            if(rs.next()) {
                vouncher.setId(rs.getInt(1));
            }
            
            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Vouncher> getSales() {
        List<Vouncher> salelist = new ArrayList();
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                    .prepareStatement("select * from sales");
            
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                salelist.add(new Vouncher(rs.getString("name"), rs.getInt("quantity"), rs.getInt("price"), rs.getInt("category_id")));
            }
            
            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return salelist;
    }

    @Override
    public List<Vouncher> getSalesByCategory(int category_id) {
        List<Vouncher> salelist = new ArrayList();
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                    .prepareStatement("select * from sales where category_id = ?");
            st.setInt(1, category_id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                salelist.add(new Vouncher(rs.getString("name"), rs.getInt("quantity"), rs.getInt("price"), rs.getInt("category_id")));
            }
            
            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return salelist;
    }
}
