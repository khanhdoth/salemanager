package com.hatde.salemanager.services;

import com.hatde.salemager.EContainer;
import com.hatde.salemanager.entities.Contact;
import com.hatde.salemanager.entities.PaymentReceived;
import com.hatde.salemanager.entities.Product;
import com.hatde.salemanager.entities.Sale;
import com.hatde.salemanager.entities.SaleItem;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Khanh
 */
public class IntegrationProductTransactionTest {
    private static final String PERSISTENCE_UNIT_NAME = "com.hatde_SaleManager";
    private static EntityManagerFactory factory;
    
    private static EJBContainer container;
    private Contact c;
    private Product p1;
    private Product p2;
    private static ContactFacadeREST iContact;
    private static ProductFacadeREST iProduct;
    private static PaymentReceivedFacadeREST iPayment;
    
    

    public IntegrationProductTransactionTest() throws Exception {
        c = new Contact();        
        c.setAddress("Blumenfelstr. 7");
        c.setCity("Essen");
        c.setCountry("Germany");
        c.setDistrict("Nordvietel");
        c.setMoreInfo("Engineer");
        c.setName("Khanh Do");
        c.setPostcode("45171");
        iContact.create(c);
        
        p1 = new Product();        
        p1.setManufacturer("LG");        
        p1.setName("Phone");
        p1.setUnit("cái");
        iProduct.create(p1);    
        
        p2 = new Product();        
        p2.setManufacturer("Samsung");        
        p2.setName("Printer");
        p2.setUnit("cái");
        iProduct.create(p2);    
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        container = EContainer.INSTANCE.getContainer();
        iContact = (ContactFacadeREST) container.getContext().lookup("java:global/classes/ContactFacadeREST");
        iProduct = (ProductFacadeREST) container.getContext().lookup("java:global/classes/ProductFacadeREST");
        iPayment = (PaymentReceivedFacadeREST) container.getContext().lookup("java:global/classes/PaymentTransactionFacadeREST");
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        
    }

    @After
    public void tearDown() {
    }

    
    /**
     * Test of create method, of class ContactFacadeREST.
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateSale() throws Exception {
        System.out.println("Create Sale");
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        
        SaleItem si1 = new SaleItem();
        si1.setProduct(p1);
        si1.setQuantity(2);
        si1.setPrice(20000);
        si1.setDiscount(0.2f);
        
        SaleItem si2 = new SaleItem();
        si2.setProduct(p2);
        si2.setQuantity(4);
        si2.setPrice(30000);
        si2.setDiscount(0.1f);

        PaymentReceived pc1 = new PaymentReceived();
        pc1.setDate(df.parse("02.11.2013"));
        pc1.setAmount(400000);
        pc1.setPaymentReason("Tra tien lalala");
        pc1.setContact(c);
        
        Sale sale1 = new Sale();
        sale1.setDate(df.parse("02.11.2013"));
        sale1.getListOfSaleItem().add(si1);
        sale1.getListOfSaleItem().add(si2);
        sale1.setPayment(pc1);
        sale1.setContact(c);
        
        //iContact.getEntityManager().merge(sale1);

        c.getListOfBuy().add(sale1);
        
        iContact.edit(c);
        
        Contact c2 = iContact.find(c.getContactId());
        System.out.println(c2.toString());
                
        assertTrue(c.equals(c2));
        assertEquals(1, c.getListOfBuy().size());
    }
    
    /**
     * Test of create method, of class ContactFacadeREST.
     * @throws java.lang.Exception
     */
    @Test
    public void testCreatePaymentReceived() throws Exception {
        EntityManager em = factory.createEntityManager();
        
        System.out.println("Create Sale2");
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        
        em.getTransaction().begin();
        
        System.out.println(c.toString());        
        PaymentReceived pc1 = new PaymentReceived();
        pc1.setDate(df.parse("04.11.2013"));
        pc1.setAmount(500000);
        pc1.setPaymentReason("hallooeeee");
        pc1.setContact(c);
        
        c.getListOfPaymentSent().add(pc1);
        
        em.persist(pc1);
        //em.merge(c);
        em.getTransaction().commit();
        
        PaymentReceived pc2 = (PaymentReceived) em.find(PaymentReceived.class, pc1.getPaymentTransactionId());
        System.out.println(pc2.toString());
        assertTrue(pc1.equals(pc2));
        
        //c.getListOfPaymentSent().add(pc1);
        //iPayment.create(pc1);
        //iContact.edit(c);
        
        System.out.println(pc1.toString());
        //PaymentReceived pc2 = (PaymentReceived) iPayment.find(pc1.getPaymentTransactionId());
        //System.out.println(pc2.toString());
        //assertTrue(pc1.equals(pc2));
        
        Contact c2 = (Contact) em.find(Contact.class, c.getContactId());        
        System.out.println(c2.toString());
        
        assertTrue(c.equals(c2));         
        em.close();
    }
    
    @Test
    public void testCreatePaymentReceived3() throws Exception {
        System.out.println("Create Sale3");
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        
        
        System.out.println(c.toString());        
        PaymentReceived pc1 = new PaymentReceived();
        pc1.setDate(df.parse("04.11.2013"));
        pc1.setAmount(500000);
        pc1.setPaymentReason("hallooeeee");
        pc1.setContact(c);
        
        c.getListOfPaymentSent().add(pc1);
        
        iPayment.create(pc1);
        
        PaymentReceived pc2 = (PaymentReceived) iPayment.find(pc1.getPaymentTransactionId());
        System.out.println(pc2.toString());
        assertTrue(pc1.equals(pc2));
        
        //c.getListOfPaymentSent().add(pc1);
        //iPayment.create(pc1);
        //iContact.edit(c);
        
        System.out.println(pc1.toString());
        //PaymentReceived pc2 = (PaymentReceived) iPayment.find(pc1.getPaymentTransactionId());
        //System.out.println(pc2.toString());
        //assertTrue(pc1.equals(pc2));
        
        Contact c2 = (Contact) iContact.find(c.getContactId());        
        System.out.println(c2.toString());
        
        assertTrue(c.equals(c2));                 
    }
    
    @Test
    public void testCreatePaymentReceived4() throws Exception {
        System.out.println("Create Sale4");
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        
        
        System.out.println(c.toString());        
        PaymentReceived pc1 = new PaymentReceived();
        pc1.setDate(df.parse("04.11.2013"));
        pc1.setAmount(500000);
        pc1.setPaymentReason("hallooeeee");
        pc1.setContact(c);
        
        c.getListOfPaymentSent().add(pc1);
        
        iContact.edit(c);
        
        //PaymentReceived pc2 = (PaymentReceived) iPayment.find(pc1.getPaymentTransactionId());
        //System.out.println(pc2.toString());
        //assertTrue(pc1.equals(pc2));
        
        //c.getListOfPaymentSent().add(pc1);
        //iPayment.create(pc1);
        //iContact.edit(c);
        
        System.out.println(pc1.toString());
        //PaymentReceived pc2 = (PaymentReceived) iPayment.find(pc1.getPaymentTransactionId());
        //System.out.println(pc2.toString());
        //assertTrue(pc1.equals(pc2));
        
        Contact c2 = (Contact) iContact.find(c.getContactId());        
        System.out.println(c2.toString());
        
        assertTrue(c.equals(c2));                 
    }                
}