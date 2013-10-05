/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hatde.salemanager.web;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Do
 */
@Stateless
public class MyBean {
    @PersistenceContext(unitName = "com.hatde_SaleManager")
    private EntityManager em;
    
    public int add(int a, int b){
        return a+b;
    }
}
