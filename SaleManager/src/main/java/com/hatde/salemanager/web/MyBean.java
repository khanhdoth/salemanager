/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hatde.salemanager.web;

import javax.ejb.Stateless;

/**
 *
 * @author Do
 */
@Stateless
public class MyBean {
    public int add(int a, int b){
        return a+b;
    }
}
