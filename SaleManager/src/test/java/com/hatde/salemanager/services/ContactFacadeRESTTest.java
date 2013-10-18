/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hatde.salemanager.services;

import com.hatde.salemanager.entities.Contact;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Khanh
 */
public class ContactFacadeRESTTest {

    private Contact entity;
    private static EJBContainer container;
    private ContactFacadeREST instance;

    public ContactFacadeRESTTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Map<Object, Object> properties = new HashMap<Object, Object>();
        properties.put(EJBContainer.APP_NAME, "SaleManager");
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer(properties);
    }

    @AfterClass
    public static void tearDownClass() {
        container.close();
    }

    @Before
    public void setUp() throws NamingException {
        instance = (ContactFacadeREST) container.getContext().lookup("java:global/SaleManager/classes/ContactFacadeREST");
        System.out.print("instace == null? " + instance.toString());

        entity = new Contact();
        entity.setAddress("Blumenfelstr. 7");
        entity.setCity("Essen");
        entity.setCountry("Germany");
        entity.setDistrict("Nordvietel");
        entity.setMoreInfo("Engineer");
        entity.setName("Khanh Do");
        entity.setPostcode("45171"); 
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class ContactFacadeREST.
     *
     * @throws java.lang.Exception
     */
    //@Ignore
    @Test
    public void testCreate() throws Exception {
        System.out.println("testCreate");
        //Contact entity2 = instance.find(entity.getContactId());
        instance.create(entity);
        Contact entity2 = instance.find(entity.getContactId());        
        assertTrue(entity.equals(entity2));
        
        List listContact = instance.findAll();                
        assertTrue(listContact.size()==0);
        System.out.println("list size: " + listContact.size());
        
    }
}
