/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hatde.salemanager.services;

import com.hatde.salemager.EContainer;
import com.hatde.salemanager.entities.Contact;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Khanh
 */
public class ContactFacadeRESTTest {

    private static EJBContainer container;
    private Contact entity;

    public ContactFacadeRESTTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        container = EContainer.INSTANCE.getContainer();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
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
     * Test of remove method, of class ContactFacadeREST.
     */
    @Ignore
    @Test
    public void testRemove_GenericType() throws Exception {
        System.out.println("remove");
        Contact entity = null;
        
        ContactFacadeREST instance = (ContactFacadeREST) container.getContext().lookup("java:global/classes/ContactFacadeREST");
        instance.remove(entity);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of find method, of class ContactFacadeREST.
     */
    @Ignore
    @Test
    public void testFind_Object() throws Exception {
        System.out.println("find");
        Object id = null;
        
        ContactFacadeREST instance = (ContactFacadeREST) container.getContext().lookup("java:global/classes/ContactFacadeREST");
        Contact expResult = null;
        Contact result = instance.find(id);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findRange method, of class ContactFacadeREST.
     */
    @Ignore
    @Test
    public void testFindRange_intArr() throws Exception {
        System.out.println("findRange");
        int[] range = null;
        
        ContactFacadeREST instance = (ContactFacadeREST) container.getContext().lookup("java:global/classes/ContactFacadeREST");
        List<Contact> expResult = null;
        List<Contact> result = instance.findRange(range);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of count method, of class ContactFacadeREST.
     * @throws java.lang.Exception
     */
    @Ignore
    @Test
    public void testCount() throws Exception {
        System.out.println("count");
        
        ContactFacadeREST instance = (ContactFacadeREST) container.getContext().lookup("java:global/classes/ContactFacadeREST");
        int expResult = 0;
        int result = instance.count();
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class ContactFacadeREST.
     * @throws java.lang.Exception
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        
        ContactFacadeREST instance = (ContactFacadeREST) container.getContext().lookup("java:global/classes/ContactFacadeREST");
        instance.create(entity);
        
        Contact entity2 = instance.find(entity.getContactId());
        
        assertTrue(entity.equals(entity2));
    }

    
    /**
     * Test of edit method, of class ContactFacadeREST.
     */
    @Ignore
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Contact entity = null;
        
        ContactFacadeREST instance = (ContactFacadeREST) container.getContext().lookup("java:global/classes/ContactFacadeREST");
        instance.edit(entity);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class ContactFacadeREST.
     */
    @Ignore
    @Test
    public void testRemove_Integer() throws Exception {
        System.out.println("remove");
        Integer id = null;
        
        ContactFacadeREST instance = (ContactFacadeREST) container.getContext().lookup("java:global/classes/ContactFacadeREST");
        instance.remove(id);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of find method, of class ContactFacadeREST.
     */
    @Ignore
    @Test
    public void testFind_Integer() throws Exception {
        System.out.println("find");
        Integer id = null;
        
        ContactFacadeREST instance = (ContactFacadeREST) container.getContext().lookup("java:global/classes/ContactFacadeREST");
        Contact expResult = null;
        Contact result = instance.find(id);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAll method, of class ContactFacadeREST.
     */
    @Ignore
    @Test
    public void testFindAll() throws Exception {
        System.out.println("findAll");
        
        ContactFacadeREST instance = (ContactFacadeREST) container.getContext().lookup("java:global/classes/ContactFacadeREST");
        List<Contact> expResult = null;
        List<Contact> result = instance.findAll();
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getList method, of class ContactFacadeREST.
     */
    @Ignore
    @Test
    public void testGetList() throws Exception {
        System.out.println("getList");
        
        ContactFacadeREST instance = (ContactFacadeREST) container.getContext().lookup("java:global/classes/ContactFacadeREST");
        List<Contact> expResult = null;
        List<Contact> result = instance.getList();
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findRange method, of class ContactFacadeREST.
     */
    @Ignore
    @Test
    public void testFindRange_Integer_Integer() throws Exception {
        System.out.println("findRange");
        Integer from = null;
        Integer to = null;
        
        ContactFacadeREST instance = (ContactFacadeREST) container.getContext().lookup("java:global/classes/ContactFacadeREST");
        List<Contact> expResult = null;
        List<Contact> result = instance.findRange(from, to);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of countREST method, of class ContactFacadeREST.
     * @throws java.lang.Exception
     */
    @Ignore
    @Test
    public void testCountREST() throws Exception {
        System.out.println("countREST");
        
        ContactFacadeREST instance = (ContactFacadeREST) container.getContext().lookup("java:global/classes/ContactFacadeREST");
        String expResult = "";
        String result = instance.countREST();
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
