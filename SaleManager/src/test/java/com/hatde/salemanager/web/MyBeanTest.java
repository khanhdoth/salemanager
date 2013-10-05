/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hatde.salemanager.web;

import com.hatde.salemager.EContainer;
import javax.ejb.embeddable.EJBContainer;
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
public class MyBeanTest {
    private static EJBContainer container;
    
    public MyBeanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {   
        //container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        container = EContainer.INSTANCE.getContainer();
    }
    
    @AfterClass
    public static void tearDownClass() {
        //container.close();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of add method, of class MyBean.
     */
    @Test
    public void testAdd() throws Exception {
        System.out.println("add");
        int a = 1;
        int b = 2;
        
        MyBean instance = (MyBean)container.getContext().lookup("java:global/classes/MyBean");
        
        int expResult = 3;
        int result = instance.add(a, b);
        assertEquals(expResult, result);
    }
    
}
