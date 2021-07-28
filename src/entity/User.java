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
public class User {
    private String name;
    private String type;
    private String password;
    private int id;
    
    public User(int id, String name, String password, String type) {
        this.name = name;
        this.password = password;
        this.type = type;
        this.id = id;
    }
    
    public User(String name, String password, String type) {
        this.name = name;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return toCapletter(name);
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getPassword() {
        return password;
    }

    public String getType() {
        return type.toLowerCase();
    }
    
    private String toCapletter(String name) {
        return name.substring(0, 1).toUpperCase()+name.substring(1).toLowerCase();
    }
}
