/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import entity.Category;
import entity.Product;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import session.CategoryFacade;
import session.OrderManager;
import session.ProductFacade;

/**
 *
 * @author Woody
 */
@ManagedBean(name = "productController")
@SessionScoped
public class ProductController implements Serializable{
    
    @EJB
    private OrderManager orderManager;
    @EJB
    private CategoryFacade categoryFacade;
    @EJB
    private ProductFacade productFacade;
    
    private Part file=null;
    
    private Product product;

    private String categoryName;
    private String name="";
    private BigDecimal price=null;
    private String desc="";
    
    
    
    private List<Category> cats;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    
    
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    
    public void upload(String proName) {
      File destFile = new File("C:/Users/Woody/Documents/NetBeansProjects/Dakakeen/web/resources/img/products/"+proName+".png");
      
      try {                    
          InputStream inputStr=file.getInputStream();
          BufferedImage bImage=ImageIO.read(inputStr);
          //bImage=resizeImage(bImage, bImage.getType());
          ImageIO.write(bImage, "png", destFile);
          
    } catch (IOException e) {
        //log error
    }
  }
    
    /*private static BufferedImage resizeImage(BufferedImage originalImage, int type){
	BufferedImage resizedImage = new BufferedImage(142, 80, type);
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(originalImage, 0, 0, 210, 174, null);
	g.dispose();
 
	return resizedImage;
    }*/
    
    public void addProduct(){
        upload(name);
        Product pro=new Product();
        pro.setName(name);
        pro.setDescription(desc);
        pro.setPrice(price);
        short cid=-1;
        cats=categoryFacade.findAll();
        
        for (Category c: cats){
            if (c.getName().equalsIgnoreCase(categoryName)){
                pro.setCategoryId(c);
                break;
            }
        }
        
        orderManager.addProduct(pro);
        
        //product=new Product();
        name="";
        desc="";
        price=null;
        categoryName="";
        
    }

    /**
     * Creates a new instance of ProductController
     */
    public ProductController() {
        
    }
    
    
    
    public synchronized String updateName(Product pr){
        
        if (!name.equals("")){
            if(!renameFile(pr, name)){
                name="";
                return null;
            }
            try{
            pr.setName(name);
            productFacade.edit(pr);
            wait(1000);
            name="";
                return ("updPro");
            } catch (InterruptedException ex) {
                Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        name="";
        
        return ("updPro");
        
     
    }
    
    public synchronized String updateDesc(Product pr){
        
        if (!desc.equals("")){
            try{
            pr.setDescription(desc);
            productFacade.edit(pr);
            wait(1000);
            desc="";
                return ("updPro");
            } catch (InterruptedException ex) {
                Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        desc="";
        
        return ("updPro");
        
     
    }
    
    public synchronized String updatePrice(Product pr){
        
        if (price!=null){
            
            try{
            pr.setPrice(price);
            productFacade.edit(pr);
            wait(1000);
            price=null;
                return ("updPro");
            } catch (InterruptedException ex) {
                Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        price=null;
        
        return ("updPro");
        
     
    }
    
    
    public synchronized String updateFile(Product pr){

        
        if(file.getSize()!=0){
            
            try {
                upload(pr.getName());
                wait(5000);
                file=null;
                return ("updPro");
            } catch (InterruptedException ex) {
                Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        file=null;
        
        return ("updPro");
    }
    
    public boolean renameFile(Product pr,String newName){
        File oldFile = new File("C:/Users/Woody/Documents/NetBeansProjects/Dakakeen/web/resources/img/products/"+pr.getName()+".png");
        
        File newFile = new File("C:/Users/Woody/Documents/NetBeansProjects/Dakakeen/web/resources/img/products/"+newName+".png");
        
        return oldFile.renameTo(newFile);
    }
    
    public String DeletePro(Product p){
        productFacade.remove(p);
        return ("updPro");
    }
    
    public void mostPaid(){
    }
    
}
