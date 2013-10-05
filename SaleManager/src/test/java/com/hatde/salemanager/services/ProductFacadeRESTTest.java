/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hatde.salemanager.services;

import com.hatde.salemager.EContainer;
import com.hatde.salemanager.entities.Product;
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
public class ProductFacadeRESTTest {

    private static EJBContainer container;
    private Product entity;

    public ProductFacadeRESTTest() {
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
        entity = new Product();
        entity.setManufacturer("LG");
        entity.setName("Bún mắm");
        entity.setUnit("cái");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of edit method, of class ProductFacadeREST.
     */
    @Ignore
    @Test
    public void testEdit_GenericType() throws Exception {
        System.out.println("edit");
        Product entity = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ProductFacadeREST instance = (ProductFacadeREST) container.getContext().lookup("java:global/classes/ProductFacadeREST");
        instance.edit(entity);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class ProductFacadeREST.
     */
    @Ignore
    @Test
    public void testRemove_GenericType() throws Exception {
        System.out.println("remove");
        Product entity = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ProductFacadeREST instance = (ProductFacadeREST) container.getContext().lookup("java:global/classes/ProductFacadeREST");
        instance.remove(entity);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of find method, of class ProductFacadeREST.
     */
    @Ignore
    @Test
    public void testFind_Object() throws Exception {
        System.out.println("find");
        Object id = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ProductFacadeREST instance = (ProductFacadeREST) container.getContext().lookup("java:global/classes/ProductFacadeREST");
        Product expResult = null;
        Product result = instance.find(id);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findRange method, of class ProductFacadeREST.
     */
    @Ignore
    @Test
    public void testFindRange_intArr() throws Exception {
        System.out.println("findRange");
        int[] range = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ProductFacadeREST instance = (ProductFacadeREST) container.getContext().lookup("java:global/classes/ProductFacadeREST");
        List<Product> expResult = null;
        List<Product> result = instance.findRange(range);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of count method, of class ProductFacadeREST.
     */
    @Ignore
    @Test
    public void testCount() throws Exception {
        System.out.println("count");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ProductFacadeREST instance = (ProductFacadeREST) container.getContext().lookup("java:global/classes/ProductFacadeREST");
        int expResult = 0;
        int result = instance.count();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class ProductFacadeREST.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        ProductFacadeREST instance = (ProductFacadeREST) container.getContext().lookup("java:global/classes/ProductFacadeREST");
        instance.create(entity);

        Product entity2 = instance.find(entity.getProductId());
        assertTrue(entity.equals(entity2));
    }

    /**
     * Test of edit method, of class ProductFacadeREST.
     */
    @Ignore
    @Test
    public void testEdit_Integer_Product() throws Exception {
        System.out.println("edit");
        Integer id = null;
        Product entity = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ProductFacadeREST instance = (ProductFacadeREST) container.getContext().lookup("java:global/classes/ProductFacadeREST");
        instance.edit(id, entity);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class ProductFacadeREST.
     */
    @Ignore
    @Test
    public void testRemove_Integer() throws Exception {
        System.out.println("remove");
        Integer id = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ProductFacadeREST instance = (ProductFacadeREST) container.getContext().lookup("java:global/classes/ProductFacadeREST");
        instance.remove(id);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of find method, of class ProductFacadeREST.
     */
    @Ignore
    @Test
    public void testFind_Integer() throws Exception {
        System.out.println("find");
        Integer id = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ProductFacadeREST instance = (ProductFacadeREST) container.getContext().lookup("java:global/classes/ProductFacadeREST");
        Product expResult = null;
        Product result = instance.find(id);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAll method, of class ProductFacadeREST.
     */
    @Ignore
    @Test
    public void testFindAll() throws Exception {
        System.out.println("findAll");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ProductFacadeREST instance = (ProductFacadeREST) container.getContext().lookup("java:global/classes/ProductFacadeREST");
        List<Product> expResult = null;
        List<Product> result = instance.findAll();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findRange method, of class ProductFacadeREST.
     */
    @Ignore
    @Test
    public void testFindRange_Integer_Integer() throws Exception {
        System.out.println("findRange");
        Integer from = null;
        Integer to = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ProductFacadeREST instance = (ProductFacadeREST) container.getContext().lookup("java:global/classes/ProductFacadeREST");
        List<Product> expResult = null;
        List<Product> result = instance.findRange(from, to);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of countREST method, of class ProductFacadeREST.
     */
    @Ignore
    @Test
    public void testCountREST() throws Exception {
        System.out.println("countREST");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ProductFacadeREST instance = (ProductFacadeREST) container.getContext().lookup("java:global/classes/ProductFacadeREST");
        String expResult = "";
        String result = instance.countREST();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
