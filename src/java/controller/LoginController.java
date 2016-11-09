/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import cart.ShoppingCart;
import entity.Customer;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.CustomerFacade;

@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController implements Serializable{

    @EJB
    CustomerFacade customerFacade;
    
    private Customer customer;
    private boolean isLogin=false;
    private boolean adminLogin=false;
    private boolean customerLogin=false;
    private String email;
    private String password;
    private String errorMSG;
    private String selected;
    private String type;
    private boolean checked;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    
    public boolean isAdminLogin() {
        return adminLogin;
    }

    public void setAdminLogin(boolean adminLogin) {
        this.adminLogin = adminLogin;
    }

    public boolean isCustomerLogin() {
        return customerLogin;
    }

    public void setCustomerLogin(boolean customerLogin) {
        this.customerLogin = customerLogin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getSelected() {
        return selected;
    }
    
    public String getErrorMSG() {
        return errorMSG;
    }

    public void setErrorMSG(String errorMSG) {
        this.errorMSG = errorMSG;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Creates a new instance of AdminController
     */
    public LoginController() {
    }
    
    public String login(){
        errorMSG="";
        if (isLogin){
            if (type.equalsIgnoreCase("admin"))
                return ("admin");
            else
                return ("index");
        }
        else
            return ("login");
    }

    public boolean isIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
    
    public String loginMethod(){
        errorMSG="";
        
        List<Customer> customers=customerFacade.findAll();
            
            for (Customer c:customers){
                if (c.getEmail().equalsIgnoreCase(email)&&c.getPassword().equalsIgnoreCase(password)){
                    customer=c;
                    isLogin=true;
                    customerLogin=true;
                    type="customer";
                    return ("checkout");
                }
            }
            errorMSG="Email/Password is invalid !";
            return null;
    }
    
    public String loginAdmin(){
        
        errorMSG="";
        
            if (email.equalsIgnoreCase("admin")&&password.equalsIgnoreCase("admin")){
                isLogin=true;
                adminLogin=true;
                type="admin";
                return ("admin");
            }
            else{
                errorMSG="Username/Password is invalid !";
                return null;
            }
        
        
    }
    
    public String register(){
        errorMSG="";
        return ("register");
    }
    
    public String logout(ShoppingCart cart){
        if (cart!=null)
            cart.clear();
        isLogin=false;
        adminLogin=false;
        customerLogin=false;
        email="";
        password="";
        errorMSG="";
        type="";
        selected="";
        return ("index");
    }
    
    
    public String checkout() {
        errorMSG="";
        if (customerLogin){
            return ("checkout");
        }
        else{
            errorMSG="Please Login first!";
            return ("login");
        }
            
    }
}
