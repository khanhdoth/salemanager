/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hatde.salemanager.services;

import com.hatde.salemanager.entities.Buy;
import com.hatde.salemanager.entities.Contact;
import com.hatde.salemanager.entities.PaymentSent;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Khanh
 */
@Stateless
@Path("buy")
public class BuyFacadeREST extends AbstractFacade<Buy> {

    @PersistenceContext(unitName = "com.hatde_SaleManager")
    private EntityManager em;

    public BuyFacadeREST() {
        super(Buy.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Buy entity) {
        Contact contact = entity.getContact();
        PaymentSent ps = (PaymentSent) entity.getPayment();
        if (ps.getAmount() > 0) {
            ps.setDate(entity.getDate());
            ps.setContact(contact);
            contact.getListOfPaymentReceived().add(ps);
        } else {
            entity.setPayment(null);
        }

        entity.setContact(contact);
        contact.getListOfSale().add(entity);
        em.merge(contact);
        em.persist(entity);
    }
    
    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Buy entity) {
        super.edit(entity);
    }

    @Override
    public void edit(Buy entity) {
        em.merge(entity.getPayment());
        em.merge(entity);
    }
    
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @Override
    public void remove(Buy entity) {
        Contact contact = entity.getContact();
        PaymentSent ps = (PaymentSent) entity.getPayment();

        if (ps != null) {
            contact.getListOfPaymentReceived().remove(ps);
        }

        contact.getListOfSale().remove(entity);
        em.merge(contact);
        em.remove(em.merge(entity));

        if (ps != null) {            
            em.remove(em.merge(ps));
        }
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Buy find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Buy> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Buy> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
