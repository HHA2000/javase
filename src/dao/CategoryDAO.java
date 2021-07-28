/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Category;
import java.util.List;

/**
 *
 * @author wizard
 */
public interface CategoryDAO {
    public List<Category> getCategories();
    public void addCategory(String name);
    public void removeCategory(int id);
    public int getCategoryID(String name);
    public boolean ExistCategory(String name);
    public void updateCategory(Category category);
}
