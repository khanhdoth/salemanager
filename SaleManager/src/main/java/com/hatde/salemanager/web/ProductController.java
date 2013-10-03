package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.Product;
import com.hatde.salemanager.services.ProductFacadeREST;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Do
 */
@Named(value = "productController")
@SessionScoped
public class ProductController implements Serializable {

    @EJB
    private ProductFacadeREST bean;

    @Inject
    private BundleBean bundleBean;

    private List<Product> list;
    private List<Product> filterList;
    private Product selectedProduct;
    private Product newProduct;
    private ResourceBundle bundle;

    @PostConstruct
    public void init() {
        initListandNewProduct();
        bundle = bundleBean.getBundle();
    }

    public void initListandNewProduct() {
        initDataList();
        initNewProduct();
    }

    public void initDataList() {
        list = new ArrayList();
        list = bean.findAll();
    }

    public void initNewProduct() {
        newProduct = new Product();
    }

    public ProductController() {
    }

    public List<Product> getList() {
        System.out.println("----ProductController.getList----");
        return list;
    }

    public void setList(List<Product> list) {
        this.list = list;
    }

    public List<Product> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<Product> filterList) {
        this.filterList = filterList;
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            Product c = (Product) ((DataTable) event.getSource()).getRowData();
            try {
                bean.edit(c);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("Edit_Success"), "name = " + c.getName()));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("Edit_Failed_Message"), "name = " + c.getName()));
            }
        }
    }

    public void delete() {
        System.out.println("============== delete =============");

        try {
            bean.remove(selectedProduct);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("Delete_Success"), ""));
            initDataList();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("Delete_Failed_Message"), "name = " + selectedProduct.getName()));
        }
    }

    public void createProduct() {
        System.out.println("============== createProduct ============= " + newProduct.getName());
        try {
            bean.create(newProduct);
            initListandNewProduct();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("Create_Success"), ""));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("Create_Failed_Message"), ""));
        }

    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public Product getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(Product newProduct) {
        this.newProduct = newProduct;
    }

    public void refreshList() {
        initDataList();
    }
}
