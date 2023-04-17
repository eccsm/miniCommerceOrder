package tr.nttdata.poc.minicommerce.order.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import tr.nttdata.poc.minicommerce.order.annotation.LogObjectAfter;
import tr.nttdata.poc.minicommerce.order.annotation.LogObjectBefore;
import tr.nttdata.poc.minicommerce.order.model.Cart;
import tr.nttdata.poc.minicommerce.order.model.CartItem;
import tr.nttdata.poc.minicommerce.order.model.Order;
import tr.nttdata.poc.minicommerce.order.service.CartService;
import tr.nttdata.poc.minicommerce.order.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    //TODO extract username from request header
    static String username = "cengiz";

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @LogObjectBefore
    @LogObjectAfter
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
//            Order createdOrder = orderService.checkout(order.getCustomerId());
            Order createdOrder = orderService.checkout(username);
            if (createdOrder != null) {
                return ResponseEntity.ok(createdOrder);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error message: " + e.getMessage());
        }
    }

    @LogObjectAfter
    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error message: " + e.getMessage());
        }
    }

    @LogObjectAfter
    @GetMapping("/cart")
    public ResponseEntity<?> getCart() {
        try {
            Cart cart = cartService.getCart(username);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error message: " + e.getMessage());
        }
    }

    @LogObjectAfter
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable String orderId) {
        try {
            Order order = orderService.getOrderById(String.valueOf(orderId));
            if (order != null) {
                return ResponseEntity.ok(order);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error message: " + e.getMessage());
        }
    }

    @LogObjectAfter
    @PutMapping("/{orderId}")
    public ResponseEntity<?> updateOrder(@PathVariable String orderId, @Valid @RequestBody Order updatedOrder) {
        try {
            Order order = orderService.updateOrder(orderId, updatedOrder);
            if (order != null) {
                return ResponseEntity.ok(order);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error message: " + e.getMessage());
        }
    }

    @LogObjectAfter
    @PutMapping("updateOrder")
    public ResponseEntity<?> updateOrder(@Valid @RequestBody CartItem cartItem){
        try {
            cartService.updateCartItemQuantity(username, cartItem.getProductId(), cartItem.getQuantity());
            return ResponseEntity.ok(cartItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error message: " + e.getMessage());
        }
    }

    @LogObjectAfter
    @PostMapping("clearCart")
    public ResponseEntity<?> clearCart(){
        try {
            cartService.clearCart(username);
            return ResponseEntity.ok("Cart cleared");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error message: " + e.getMessage());
        }
    }

    @LogObjectAfter
    @PostMapping("addToCart")
    public ResponseEntity<?> addToCart(@Valid @RequestBody CartItem cartItem) {
        try {
            cartService.addItemToCart(username, cartItem);
            Cart cart = cartService.getCart(username);
            System.out.println(cart.getCartItems().size());
            return ResponseEntity.ok(cartItem);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error message: " + e.getMessage());
        }
    }

    @LogObjectAfter
    @PostMapping("removeFromCart")
    public ResponseEntity<?> removeFromCart(@RequestBody CartItem cartItem) {
        try {
            Cart removedItem = cartService.removeItemFromCart(username, cartItem.getProductId());
            if (removedItem != null)
                return ResponseEntity.ok(cartItem.getProductId());
            else
                return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error message: " + e.getMessage());
        }
    }

    @LogObjectAfter
    @PostMapping("/checkout")
    public ResponseEntity<?> checkout() {
        try {
            Order order = orderService.checkout(username);
            if (order != null) {
                return ResponseEntity.ok(order);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error message: " + e.getMessage());
        }
    }

}
