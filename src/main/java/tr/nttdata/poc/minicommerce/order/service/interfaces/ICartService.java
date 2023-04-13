
package tr.nttdata.poc.minicommerce.order.service.interfaces;

import java.util.List;

import tr.nttdata.poc.minicommerce.order.model.Cart;
import tr.nttdata.poc.minicommerce.order.model.CartItem;

public interface ICartService {
    Cart getCartFromSession();

    Cart addItemToCart(CartItem cartItem);

    void updateCartItemQuantity(String productId, int quantity);

    Cart removeItemFromCart(String productId);

    void removeItemsFromCart(String customerId, List<CartItem> cartItems);

    void clearCart();

    Cart getCart();

}
