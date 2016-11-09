/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import entity.Customer;
import entity.CustomerOrder;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import session.CustomerFacade;

/**
 *
 * @author Woody
 */
@ManagedBean(name = "customerController")
@ApplicationScoped
public class CustomerController implements Serializable{

    @EJB
    private CustomerFacade customerFacade;

    private Customer selectedCustomer;

    private Integer selectedCustomerId;
    
    private String selected;

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }



    /*  public Category getSelectedCategorybyID(String selected) {
        Category   selectedCategory = categoryFacade.find(Short.parseShort(selected));
        this.selectedCategoryName = selectedCategory.getName();
        return (selectedCategory);
    }*/
    
    public CustomerController() {
    }

    public Customer getSelectedCustomer() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params
                = fc.getExternalContext().getRequestParameterMap();
        String gID = params.get("customer");
        selectedCustomer = customerFacade.find(Short.parseShort(gID));
        selectedCustomerId=selectedCustomer.getId();
        return (selectedCustomer);
    }

    public Integer getSelectedCustomerId() {

        return (selectedCustomerId);
    }

    public List<Customer> getCustomers() {
        return (customerFacade.findAll());
    }

    public Collection<CustomerOrder> getcustomerorderCollection() {
        return (selectedCustomer.getCustomerOrderCollection());
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public void setSelectedCustomerId(Integer selectedCustomerId) {
        this.selectedCustomerId = selectedCustomerId;
    }
    
}
