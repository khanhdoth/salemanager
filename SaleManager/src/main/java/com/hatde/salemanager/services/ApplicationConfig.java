/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hatde.salemanager.services;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Do
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        // Following code can be used to customize Jersey JSON provider:
        // Note that MoxyJsonFeature is auto-discoverable in Jersey 2.0, so the feature doesn't need to be registered.
        // try {
        //     Class jsonProvider = Class.forName("org.glassfish.jersey.moxy.json.MoxyJsonFeature");
        //     Class jsonProvider = Class.forName("org.glassfish.jersey.jackson.JacksonFeature");
        //     Class jsonProvider = Class.forName("org.glassfish.jersey.jettison.JettisonFeature");
        //     resources.add(jsonProvider);
        // } catch (ClassNotFoundException ex) {
        //     java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
        // }
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.hatde.salemanager.services.ContactFacadeREST.class);
        resources.add(com.hatde.salemanager.services.PaymentReceivedFacadeREST.class);
        resources.add(com.hatde.salemanager.services.PaymentSentFacadeREST.class);
        resources.add(com.hatde.salemanager.services.ProductFacadeREST.class);
    }
    
}
