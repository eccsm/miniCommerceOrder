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
import tr.nttdata.poc.minicommerce.order.repository.CartRepository;


@Service
@Transactional
public class CartService {

    private static final String CART_SESSION_KEY = "cart";

    @Autowired
    private CartRepository cartRepository;

    public Cart getCartFromSession(String username) {

        Cart cart = cartRepository.findCartByUsername(username);

        if (cart == null) {
            cart = new Cart();
            cartRepository.save(username, cart);
        }

        return cart;
    }

    public Cart addItemToCart(String username, CartItem cartItem) {
        try {
            Cart cart = getCartFromSession(username);
            boolean isUpdated = false;
            for (CartItem item : cart.getCartItems()) {
                if (cartItem.getProductId().equals(item.getProductId())) {
                    item.setQuantity(item.getQuantity() + 1);
                    isUpdated = true;
                    break;
                }
            }
            if (!isUpdated) {
                cart.addItem(cartItem);
            }
            cart.updateTotalPrice();
            cartRepository.save(username, cart);
            return cart;
        } catch (Exception e) {
            throw new CartNotFoundException("Error adding item to cart: " + e.getMessage(), e);
        }
    }

    public void updateCartItemQuantity(String username, String productId, int quantity) {
        try {
            Cart cart = getCartFromSession(username);
            cart.updateItemQuantity(productId, quantity);
            cartRepository.save(username, cart);
        } catch (Exception e) {
            throw new CartItemNotFoundException("Error updating cart item quantity: " + e.getMessage(), e);
        }
    }

    public Cart removeItemFromCart(String username, String productId) {
        try {
            Cart cart = getCartFromSession(username);
            cart.removeItem(productId);
            cartRepository.save(username, cart);
            return cart;
        } catch (Exception e) {
            throw new CartItemNotFoundException("Error removing item from cart: " + e.getMessage(), e);
        }
    }

//    public void removeItemsFromCart(String username, String customerId, List<CartItem> cartItems) {
//        try {
//            Cart cart = getCartFromSession(username);
//            if (cart != null) {
//                List<CartItem> sessionItems = cart.getCartItems();
//                for (CartItem cartItem : cartItems) {
//                    Optional<CartItem> cartItemOptional = cartItems.stream()
//                            .filter(item -> item.getProductId().equals(cartItem.getProductId()))
//                            .findFirst();
//                    if (cartItemOptional.isPresent()) {
//                        CartItem citem = cartItemOptional.get();
//                        sessionItems.remove(citem);
//                    }
//                }
//                httpSession.setAttribute("cart", cart);
//            }
//        } catch (Exception e) {
//            throw new CartItemNotFoundException("Error removing items from cart: " + e.getMessage(), e);
//        }
//    }

    public void clearCart(String username) {
        try {
            Cart cart = getCartFromSession(username);
            cart.clearCart();
            cartRepository.save(username, cart);
        } catch (Exception e) {
            throw new CartNotFoundException("Error clearing cart: " + e.getMessage(), e);
        }
    }

    public Cart getCart(String username) {
        try {
            Cart cart = getCartFromSession(username);
            return cart;
        } catch (Exception e) {
            throw new CartNotFoundException("Error retrieving cart: " + e.getMessage(), e);
        }
    }
}