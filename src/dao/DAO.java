/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author wizard
 */
public class DAO {
    private Connection con = null;
    public static DAO instance;
    
    private DAO() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/javase", "root", "admin123");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public static DAO getDAO(){
        if(instance == null) {
            instance = new DAO();
        }
        return instance;
    }

    public Connection getCon() {
        return con;
    }

}
