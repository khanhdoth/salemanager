/**
 *
 * @author Khanh
 */
package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.SaleItem;
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

    public enum DialogMode {

        CREATE, EDIT
    }
    protected DialogMode dialogMode = DialogMode.CREATE;

    protected Class<T> entityClass;

    protected abstract AbstractFacade<T> getBean();

    protected abstract BundleBean getBundleBean();

    protected List<T> list;
    protected List<T> filterList;
    protected T selectedT;
    protected T newT;
    protected ResourceBundle bundle;

    //@PostConstruct
    public void init(Class<T> entityClass) {
        this.entityClass = entityClass;
        initFirstListandNewT();
        bundle = getBundleBean().getBundle();
    }

    public void initFirstListandNewT() {
        //list = new ArrayList();
        initNewT();
    }

    public void initListandNewT() {
        initDataList();
        initNewT();
    }

    public void initDataList() {
        list = new ArrayList();
        list = getBean().findAll();
    }

    public void initNewT() {
        try {
            newT = entityClass.newInstance();
            dialogMode = dialogMode.CREATE;
        } catch (Exception e) {
        }
    }

    public FacadeController() {
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            T c = (T) ((DataTable) event.getSource()).getRowData();
            doEdit(c);
        }
    }

    public void edit() {
        System.out.println("----create----" + this.toString());
        try {
            doEdit(newT);
            initListandNewT();
        } catch (Exception e) {
        }
    }

    public void doEdit(T c) {
        System.out.println("----edit----" + this.toString());
        try {
            getBean().edit(c);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("Edit_Success"), ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("Edit_Failed_Message"), ""));
        }

    }

    public void delete() {
        System.out.println("----delete----" + this.toString());
        try {
            doDelete();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("Delete_Success"), ""));
            initDataList();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("Delete_Failed_Message"), ""));
        }
    }

    public void doDelete() {
        getBean().remove(selectedT);
    }
    
    public void createOrEdit() {
        if(dialogMode == DialogMode.CREATE){
            create();
        } else {
            edit();
        }
    }

    public void create() {
        System.out.println("----create----" + this.toString());
        try {
            doCreate();
            initListandNewT();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("Create_Success"), ""));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("Create_Failed_Message"), ""));
        }

    }

    public void doCreate() {
        getBean().create(newT);
    }

    public void refreshList() {
        initDataList();
    }

    /*--------------------------------------------------------------------------
     * getter and setter
     *--------------------------------------------------------------------------
     */
    public List<T> getList() {
        System.out.println("----getList----" + this.toString());
        return list;
    }

    public List<T> getListOne() {
        System.out.println("----getListOne----" + this.toString());
        if (list == null) {
            refreshList();
        }
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
    
    public void closeDialog(){
        System.out.println("---- closeDialog ----" + this.toString());
        initListandNewT();
    }
}
