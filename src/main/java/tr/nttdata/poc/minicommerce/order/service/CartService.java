package tr.nttdata.poc.minicommerce.order.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import tr.nttdata.poc.minicommerce.order.exception.CartItemNotFoundException;
import tr.nttdata.poc.minicommerce.order.exception.CartNotFoundException;
import tr.nttdata.poc.minicommerce.order.model.Cart;
import tr.nttdata.poc.minicommerce.order.model.CartItem;


@Service
@Transactional
public class CartService {

    private static final String CART_SESSION_KEY = "cart";

    @Autowired
    private HttpSession httpSession;

    public Cart getCartFromSession() {
        Cart cart = (Cart) httpSession.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new Cart();
            httpSession.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    public Cart addItemToCart(CartItem cartItem) {
        try {
            Cart cart = getCartFromSession();
            cart.addItem(cartItem);
            cart.updateTotalPrice();
            httpSession.setAttribute(CART_SESSION_KEY, cart);
            return cart;
        } catch (Exception e) {
            throw new CartNotFoundException("Error adding item to cart: " + e.getMessage(), e);
        }
    }

    public void updateCartItemQuantity(String productId, int quantity) {
        try {
            Cart cart = getCartFromSession();
            cart.updateItemQuantity(productId, quantity);
            httpSession.setAttribute(CART_SESSION_KEY, cart);
        } catch (Exception e) {
            throw new CartItemNotFoundException("Error updating cart item quantity: " + e.getMessage(), e);
        }
    }

    public Cart removeItemFromCart(String productId) {
        try {
            Cart cart = getCartFromSession();
            cart.removeItem(productId);
            return cart;
        } catch (Exception e) {
            throw new CartItemNotFoundException("Error removing item from cart: " + e.getMessage(), e);
        }
    }

    public void removeItemsFromCart(String customerId, List<CartItem> cartItems) {
        try {
            Cart cart = (Cart) httpSession.getAttribute("cart");
            if (cart != null) {
                List<CartItem> sessionItems = cart.getCartItems();
                for (CartItem cartItem : cartItems) {
                    Optional<CartItem> cartItemOptional = cartItems.stream()
                            .filter(item -> item.getProductId().equals(cartItem.getProductId()))
                            .findFirst();
                    if (cartItemOptional.isPresent()) {
                        CartItem citem = cartItemOptional.get();
                        sessionItems.remove(citem);
                    }
                }
                httpSession.setAttribute("cart", cart);
            }
        } catch (Exception e) {
            throw new CartItemNotFoundException("Error removing items from cart: " + e.getMessage(), e);
        }
    }

    public void clearCart() {
        try {
            Cart cart = getCartFromSession();
            cart.clearCart();
        } catch (Exception e) {
            throw new CartNotFoundException("Error clearing cart: " + e.getMessage(), e);
        }
    }

    public Cart getCart() {
        try {
            Cart cart = getCartFromSession();
            return cart;
        } catch (Exception e) {
            throw new CartNotFoundException("Error retrieving cart: " + e.getMessage(), e);
        }
    }
}