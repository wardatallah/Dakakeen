/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import session.OrderManager;

/**
 *
 * @author Woody
 */
@ManagedBean(name = "registerController")
@ApplicationScoped
public class RegisterController {
    
    @EJB
    private OrderManager orderManager;
    
    private String name="",email="",address="",phone="",region="",password="",confirm="",CcNumber="";
    
    private String errorMsg;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCcNumber() {
        return CcNumber;
    }

    public void setCcNumber(String CcNumber) {
        this.CcNumber = CcNumber;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
    
    
    
    public String register(){
        errorMsg="";
        boolean error=false;
       if (name.equals("") || email.equals("") || address.equals("") || phone.equals("") || region.equals("") || CcNumber.equals("")|| password.equals("") || confirm.equals("")){
           errorMsg="All Fields are REQUIRED !";
           return null;
        }
       else {
               if (!email.contains("@")){
                   errorMsg="-Email must contains @ character-";
                   error=true;
               }
               if (region.length()>2){
                   errorMsg+="-region length must be less or equals 2 characters-";
                   error=true;
               }
               if (!password.equals(confirm)){
                   errorMsg+="-Password doesn't match confirm-";
                   error=true;
               }
               if(password.equals(confirm)&&password.length()<6){
                   errorMsg+="-Password length must be more than 6-";
                   error=true;
               }
           }
       
       if (error==false){
               orderManager.addCustomer(name, email, phone, address, region, CcNumber,password);
               clearAll();
               return ("login");
       }
       
       return null;
    }
    
    public void clearAll(){
        name="";
        email="";
        address="";
        phone="";
        region="";
        CcNumber="";
        password="";
        confirm="";
        errorMsg="";
    }
}
