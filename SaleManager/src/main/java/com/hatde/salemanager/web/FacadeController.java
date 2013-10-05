/**
 *
 * @author Khanh
 */
package com.hatde.salemanager.web;

import com.hatde.salemanager.services.AbstractFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Do
 */
public abstract class FacadeController<T> {
    private Class<T> entityClass;

    //@EJB
    //private ContactFacadeREST bean;
    //@Inject
    //private BundleBean bundleBean;
    protected abstract AbstractFacade<T> getBean();

    protected abstract BundleBean getBundleBean();

    private List<T> list;
    private List<T> filterList;
    private T selectedT;
    private T newT;
    private ResourceBundle bundle;

    
     //@PostConstruct
     public void init(Class<T> entityClass) {
        this.entityClass = entityClass;
        initFirstListandNewT();        
        bundle = getBundleBean().getBundle();
     }    
    
    //public void initFirstListandNewContact() {
    public void initFirstListandNewT() {
        list = new ArrayList();
        initNewT();
    }

    //public void initListandNewContact() {
    public void initListandNewT() {
        initDataList();
        initNewT();
    }

    public void initDataList() {
        list = new ArrayList();
        list = getBean().findAll();
    }

    //public void initNewContact() {
    public void initNewT() {        
        try{
            newT = entityClass.newInstance();
        } catch(Exception e){
        }
    }

    public FacadeController() {        
    }
    
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            T c = (T) ((DataTable) event.getSource()).getRowData();
            try {
                getBean().edit(c);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("Edit_Success"), "" ));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("Edit_Failed_Message"), ""));
            }
        }
    }

    public void delete() {
        System.out.println("============== delete =============");

        try {
            getBean().remove(selectedT);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("Delete_Success"), ""));
            initDataList();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("Delete_Failed_Message"), ""));
        }
    }

    //public void createContact() {
    public void create() {
        System.out.println("============== create ============= ");
        try {
            getBean().create(newT);
            initListandNewT();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("Create_Success"), ""));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("Create_Failed_Message"), ""));
        }

    }

    public void refreshList() {
        initDataList();
    }

    /*--------------------------------------------------------------------------
     * getter and setter
     *--------------------------------------------------------------------------
     */
   
    /*
    public Contact getSelectedContact() {
    return selectedT;
    }
    public void setSelectedContact(Contact selectedContact) {
    this.selectedT = selectedContact;
    }
    public Contact getNewContact() {
    return newT;
    }
    public void setNewContact(Contact newContact) {
    this.newT = newContact;
    }
    public List<Contact> getList() {
    System.out.println("----ContactController.getList----");
    return list;
    }
    public void setList(List<Contact> list) {
    this.list = list;
    }
    public List<Contact> getFilterList() {
    return filterList;
    }
    public void setFilterList(List<Contact> filterList) {
    this.filterList = filterList;
    }
     */
    
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public List<T> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<T> filterList) {
        this.filterList = filterList;
    }

    public T getSelectedT() {
        return selectedT;
    }

    public void setSelectedT(T selectedT) {
        this.selectedT = selectedT;
    }

    public T getNewT() {
        return newT;
    }

    public void setNewT(T newT) {
        this.newT = newT;
    }    
}
