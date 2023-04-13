package tr.nttdata.poc.minicommerce.order.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Cart implements Serializable {
    private List<CartItem> cartItems;
    private double totalPrice;

    public Cart() {
        cartItems = new ArrayList<>();
        this.totalPrice = 0.0;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void addItem(CartItem cartItem) {
        cartItems.add(cartItem);
        updateTotalPrice();
    }

    public void updateItemQuantity(String productId, int quantity) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProductId().equals(productId)) {
                cartItem.setQuantity(quantity);
                updateTotalPrice();
                break;
            }
        }
    }

    public void removeItem(String productId) {
        cartItems.removeIf(cartItem -> cartItem.getProductId().equals(productId));
        updateTotalPrice();
    }

    public void clearCart() {
        cartItems.clear();
        setTotalPrice(0.0);
    }

    public void updateTotalPrice() {
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getPrice() * cartItem.getQuantity();
        }
        setTotalPrice(totalPrice);
    }
}
