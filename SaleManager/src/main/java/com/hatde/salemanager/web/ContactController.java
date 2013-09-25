/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.Contact;
import com.hatde.salemanager.services.ContactFacadeREST;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Do
 */
//@Named(value = "contactController")
//@SessionScoped
@Named(value = "contactController")
@SessionScoped
public class ContactController implements Serializable {

    @EJB
    private ContactFacadeREST bean;

    private List<Contact> list;
    private List<Contact> filterList;

    @PostConstruct
    public void init() {
        list = new ArrayList();
        list = bean.findAll();
    }

    public ContactController() {
    }

    public List<Contact> getList() {
        return list;
    }

    public void setList(List<Contact> list) {
        this.list = list;
    }

    public List<Contact> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<Contact> filterList) {
        this.filterList = filterList;
    }
}
