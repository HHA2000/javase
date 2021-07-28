/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modal;

import dao.DAO;
import dao.UserDAO;
import entity.User;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wizard
 */
public class UserModal implements UserDAO{
    private final String SALT = "!@#";

    @Override
    public List<User> getValidUser() {
        List<User> userlist = new ArrayList();
        try {
            PreparedStatement st = DAO.getDAO().getCon().prepareStatement("select * from users where login = 1");
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                userlist.add(new User(rs.getInt("id"),rs.getString("name"), rs.getString("password"), rs.getString("type")));
            }
            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return userlist;
    }
    
    @Override
    public List<User> getRequestedUser() {
        List<User> userlist = new ArrayList();
        try {
            PreparedStatement st = DAO.getDAO().getCon().prepareStatement("select * from users where login = 0");
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                userlist.add(new User(rs.getInt("id"),rs.getString("name"), rs.getString("password"), rs.getString("type")));
            }
            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return userlist;
    }
    
    @Override
    public boolean checkAllUser(String name) {
        boolean flag = false;
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                    .prepareStatement("select * from users where name = ?");
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                flag = true;
            }
            
            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return flag;
    }
    
    @Override
    public boolean checkValidUser(String name) {
        boolean flag = false;
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                    .prepareStatement("select * from users where name = ? and login = 1");
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                flag = true;
            }
            
            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return flag;
    }

    @Override
    public boolean login(String name, String password) {
        boolean exist = false;
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                    .prepareStatement("select * from users where name = ? and password = md5(?) and login = 1");
            st.setString(1, name);
            st.setString(2, SALT + password);
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
    public User getUserByID(int id) {
        User user = null;
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                    .prepareStatement("select * from users where id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                user = new User(rs.getString("name"), rs.getString("password"), rs.getString("type"));
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return user;
    }
    
    @Override
    public String getUserType(String name) {
        String type = "";
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                    .prepareStatement("select type from users where name = ?");
            st.setString(1, name);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                type = rs.getString("type");
            }
            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return type;
    }
    
    @Override
    public int addUser(User user) {
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                    .prepareStatement("insert into users(name, password, type, login) value (?,md5(?),?,1);"
                            , Statement.RETURN_GENERATED_KEYS);
            st.setString(1, user.getName());
            st.setString(2, SALT + user.getPassword());
            st.setString(3, user.getType());
            st.executeUpdate();
            
            ResultSet rs = st.getGeneratedKeys();
            if(rs.next()) {
                user.setId(rs.getInt(1));
            }
            
            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return user.getId();
    }
    
    @Override
    public void addRequestedUser(User user) {
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                    .prepareStatement("insert into users(name, password, type, login) value (?,md5(?),?,0);"
                    , Statement.RETURN_GENERATED_KEYS);
            st.setString(1, user.getName());
            st.setString(2, SALT + user.getPassword());
            st.setString(3, user.getType());
            st.executeUpdate();
            
            ResultSet rs = st.getGeneratedKeys();
            if(rs.next()) {
                user.setId(rs.getInt(1));
            }

            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteUser(int id) {
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                    .prepareStatement("delete from users where id = ?");
            st.setInt(1, id);
            st.executeUpdate();
            
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                    .prepareStatement("update users set name = ?, type = ? where id = ?");
            st.setString(1, user.getName());
            st.setString(2, user.getType());
            st.setInt(3, user.getId());
            st.executeUpdate();
            
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void approveRequestedUser(int id) {
        try {
            PreparedStatement st = DAO.getDAO().getCon()
                    .prepareStatement("update users set login = 1 where id = ?");
            st.setInt(1, id);
            st.executeUpdate();
            
            st.close();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
