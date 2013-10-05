/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hatde.salemager;
import javax.ejb.embeddable.EJBContainer;

/**
 *
 * @author Khanh
 */
public enum EContainer {
    INSTANCE;
    private EJBContainer container;
    
    EContainer(){
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
    }

    public EJBContainer getContainer() {
        return container;
    }
}
