package controller;

import cart.ShoppingCart;
import cart.ShoppingCartItem;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import entity.Product;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
public class CartController implements Serializable {

    private ShoppingCart cart;
    private String quantity;
    private List<ShoppingCartItem> Items;


    public CartController() {
        cart = new ShoppingCart();
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;

    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String addToCart(Product product) {
        cart.addItem(product);
        return null;
    }

    public String viewCart() {
        return ("cart");
    }


    public List<ShoppingCartItem> getItems() {
        this.Items = cart.getItems();
        return (Items);
    }

    public void clear() {
        this.cart.clear();
    }
    
}
