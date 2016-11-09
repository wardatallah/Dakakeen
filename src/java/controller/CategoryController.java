package controller;

import entity.Category;
import entity.Product;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import session.CategoryFacade;
import session.OrderManager;

@ManagedBean(name = "categoryController")
@ApplicationScoped
public class CategoryController implements Serializable {

    @EJB
    private OrderManager orderManager;
    @EJB
    private CategoryFacade categoryFacade;

    private Category selectedCategory;

    private String selectedCategoryName;
    
    private List<Category> cats;
    
    private String CategoryName;
    
    private String newCat="";
    
    List<SelectItem> categoryChoices =  new ArrayList<SelectItem>();

    private Map<String, Category> categryMap;
    
    private String selected;
    
    private Part file=null;
    
    

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }

    public List<SelectItem> getCategoryChoices() {
        return categoryChoices;
    }

    public void setCategoryChoices(List<SelectItem> categoryChoices) {
        this.categoryChoices = categoryChoices;
    }
    
    
 
  public void upload(String fileName) {
      File destFile = new File("C:/Users/Woody/Documents/NetBeansProjects/Dakakeen/web/resources/img/categories/"+fileName+".jpg");
      
      try {                    
          InputStream inputStr=file.getInputStream();
          BufferedImage bImage=ImageIO.read(inputStr);
          // bImage=resizeImage(bImage, bImage.getType());
          ImageIO.write(bImage, "jpg", destFile);
          
    } catch (IOException e) {
        //log error
    }
  }
 
  /*private static BufferedImage resizeImage(BufferedImage originalImage, int type){
	BufferedImage resizedImage = new BufferedImage(210, 174, type);
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(originalImage, 0, 0, 210, 174, null);
	g.dispose();
 
	return resizedImage;
    }*/
  public Part getFile() {
    return file;
  }
 
  public void setFile(Part file) {
    this.file = file;
  }

    public OrderManager getOrderManager() {
        return orderManager;
    }

    public void setOrderManager(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public CategoryFacade getCategoryFacade() {
        return categoryFacade;
    }

    public void setCategoryFacade(CategoryFacade categoryFacade) {
        this.categoryFacade = categoryFacade;
    }

    public String getNewCat() {
        return newCat;
    }

    public void setNewCat(String newCat) {
        this.newCat = newCat;
    }
    
    

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
    
    public CategoryController() {
        
    }

    public Category getSelectedCategory() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params
                = fc.getExternalContext().getRequestParameterMap();
        String gID = params.get("category");
        selectedCategory = categoryFacade.find(Short.parseShort(gID));
        this.selectedCategoryName = selectedCategory.getName();
        return (selectedCategory);
    }

    public String getSelectedCategoryName() {

        return (selectedCategoryName);
    }

    public List<Category> getCategories() {
        cats=categoryFacade.findAll();
        categryMap=new HashMap<String, Category>();
        categoryChoices.clear();
        CategoryName=cats.get(0).getName();
        selectedCategory=cats.get(0);
        selected=CategoryName;
        for(Category c: cats) {
            SelectItem menuChoice = new SelectItem(c.getName());
            categoryChoices.add(menuChoice);
            categryMap.put(c.getName(), c);
        }
        return (cats);
    }

    public Collection<Product> getcategoryProducts() {
        return (selectedCategory.getProductCollection());
    }

    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public void setSelectedCategoryName(String selectedCategoryName) {
        this.selectedCategoryName = selectedCategoryName;
    }
    
    public void addCategory(){
        if (file.getSize() >1024*200){
            System.out.println("Size is more than 200 KB");
        }
        else {
        upload(newCat);
        orderManager.addCategory(getNewCat()); 
        newCat="";
        }
    }

    public List<Category> getCats() {
        return cats;
    }

    public void setCats(List<Category> cats) {
        this.cats = cats;
    }

    public Map<String, Category> getCategryMap() {
        return categryMap;
    }

    public void setCategryMap(Map<String, Category> categryMap) {
        this.categryMap = categryMap;
    }
    
    public Category getCategory(){
        return categryMap.get(selected);
    }
    
    public synchronized String updateName(Category c){

        if (!newCat.equals("")){
            if(!renameFile(c, newCat))
                return null;
            try{
            c.setName(newCat);
            categoryFacade.edit(c);
            wait(1000);
            newCat="";
                return ("updCat");
            } catch (InterruptedException ex) {
                Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        newCat="";
        
        return ("updCat");
     
    }
    
    public synchronized String updateFile(Category c){

        
        if(file.getSize()!=0 ){
            if (file.getSize() >1024*200){
            System.out.println("Size is more than 200 KB");
            }
            else {
            try {
                upload(c.getName());
                wait(3500);
                return ("updCat");
            } catch (InterruptedException ex) {
                Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            
        }
        file=null;
        
        return ("updCat");
    }
    
    public boolean renameFile(Category c,String newName){
        File oldFile = new File("C:/Users/Woody/Documents/NetBeansProjects/Dakakeen/web/resources/img/categories/"+c.getName()+".jpg");
        
        File newFile = new File("C:/Users/Woody/Documents/NetBeansProjects/Dakakeen/web/resources/img/categories/"+newName+".jpg");
        
        return oldFile.renameTo(newFile);
    }
}
