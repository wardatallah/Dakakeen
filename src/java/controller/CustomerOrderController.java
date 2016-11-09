/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cart.ShoppingCart;
import entity.Customer;
import entity.CustomerOrder;
import entity.OrderedProduct;
import entity.Product;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import session.CustomerOrderFacade;
import session.OrderManager;
import session.OrderedProductFacade;

@ManagedBean
@RequestScoped
public class CustomerOrderController implements Serializable{

    @EJB
    private OrderManager orderManager;
    @EJB
    private CustomerOrderFacade customerOrder;
    @EJB
    private OrderedProductFacade orderedProduct;
    
    String name;
    String email;
    String phone;
    String address;
    String cityRegion;
    String ccNumber;
    ShoppingCart cart;
    int orderId;
    
    int customerID;

    public int getCustomerID() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params
                = fc.getExternalContext().getRequestParameterMap();
        String cID = params.get("customer");
        return Integer.parseInt(cID);
    }
    
    

    public OrderManager getOrderManager() {
        return orderManager;
    }

    public void setOrderManager(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public CustomerOrderFacade getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrderFacade customerOrder) {
        this.customerOrder = customerOrder;
    }
    
    
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public CustomerOrderController() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityRegion() {
        return cityRegion;
    }

    public void setCityRegion(String cityRegion) {
        this.cityRegion = cityRegion;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }
    public List<CustomerOrder> getOrders(){
        return (customerOrder.findAll());
    }
    
    public List<CustomerOrder> getCustOrder(int id){
        List<CustomerOrder> spes=new ArrayList<CustomerOrder>();

        for (CustomerOrder co:customerOrder.findAll()){
            if (co.getCustomerId().getId()==id)
                spes.add(co);
        }
        return spes;
    }
    
    public String placeOrder(Customer c, ShoppingCart cart) {
        
        orderId = orderManager.placeOrder(c, cart);
        cart.clear();
        return ("/confirmation");

    }
    
    

    public Map getOrderDetails(int orderId) {
        Map orderMap = orderManager.getOrderDetails(orderId);
        return orderMap;

    }

    
    /*public List<Product> getMostPaid(){
        int max=0,mid=0,min=0;
        List<OrderedProduct> op=orderedProduct.findAll();
        Map<Product,Integer> myMap=new HashMap<Product, Integer>();
        for (OrderedProduct ordered:op){
            if (myMap.containsKey(ordered.getProduct())){
                Integer old=myMap.get(ordered.getProduct());
                Integer newValue=old+ordered.getQuantity();
                
                myMap.replace(ordered.getProduct(), old, newValue);
                
            }
            else{
                myMap.put(ordered.getProduct(), Integer.valueOf(ordered.getQuantity()));
                
            }
        }
        
    }*/
}
