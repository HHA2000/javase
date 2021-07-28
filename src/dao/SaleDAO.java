/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Vouncher;
import java.util.List;

/**
 *
 * @author wizard
 */
public interface SaleDAO {
    public void addSale(Vouncher vouncher);
    public List<Vouncher> getSales();
    public List<Vouncher> getSalesByCategory(int category_id);
}
