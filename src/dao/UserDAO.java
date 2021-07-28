/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.User;
import java.util.List;

/**
 *
 * @author wizard
 */
public interface UserDAO {
    public List<User> getValidUser();
    public List<User> getRequestedUser();
    public User getUserByID(int id);
    public boolean checkValidUser(String name);
    public boolean checkAllUser(String name);
    public boolean login(String name, String password);
    public String getUserType(String name);
    public int addUser(User user);
    public void addRequestedUser(User user);
    public void deleteUser(int id);
    public void updateUser(User user);
    public void approveRequestedUser(int id);
}
