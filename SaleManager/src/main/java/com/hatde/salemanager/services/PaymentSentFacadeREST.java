/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hatde.salemanager.services;

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
@Path("paymentsent")
public class PaymentSentFacadeREST extends AbstractFacade<PaymentSent> {

    @PersistenceContext(unitName = "com.hatde_SaleManager")
    private EntityManager em;

    public PaymentSentFacadeREST() {
        super(PaymentSent.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(PaymentSent entity) {
        Contact contact = entity.getContact();
        entity.setContact(contact);
        contact.getListOfPaymentReceived().add(entity);
        em.merge(contact);
        em.persist(entity);
    }
    /*
     public void create(PaymentSent entity, Contact contact) {
     System.out.println("---create Payment---- " + contact.getName());
     entity.setContact(contact);
     contact.getListOfPaymentReceived().add(entity);
     em.merge(contact);
     em.persist(entity);
     }
     */

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, PaymentSent entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @Override
    public void remove(PaymentSent entity) {
        Contact contact = entity.getContact();
        contact.getListOfPaymentReceived().remove(entity);
        em.merge(contact);
        em.remove(em.merge(entity));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public PaymentSent find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<PaymentSent> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<PaymentSent> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
