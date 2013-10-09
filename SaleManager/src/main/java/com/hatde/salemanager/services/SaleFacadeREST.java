/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hatde.salemanager.services;

import com.hatde.salemanager.entities.Contact;
import com.hatde.salemanager.entities.PaymentReceived;
import com.hatde.salemanager.entities.PaymentSent;
import com.hatde.salemanager.entities.Sale;
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
@Path("sale")
public class SaleFacadeREST extends AbstractFacade<Sale> {

    @PersistenceContext(unitName = "com.hatde_SaleManager")
    private EntityManager em;

    public SaleFacadeREST() {
        super(Sale.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Sale entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Sale entity) {
        super.edit(entity);
    }

    @Override
    public void edit(Sale entity) {
        em.merge(entity.getPayment());
        em.merge(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @Override
    public void remove(Sale entity) {
        Contact contact = entity.getContact();
        PaymentReceived pc = (PaymentReceived) entity.getPayment();

        if (pc != null) {
            contact.getListOfPaymentSent().remove(pc);
        }

        contact.getListOfBuy().remove(entity);
        em.merge(contact);
        em.remove(em.merge(entity));

        if (pc != null) {
            em.remove(em.merge(pc));
        }
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Sale find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Sale> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Sale> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
