package tr.nttdata.poc.minicommerce.order.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tr.nttdata.poc.minicommerce.order.exception.ResourceNotFoundException;
import tr.nttdata.poc.minicommerce.order.model.Cart;
import tr.nttdata.poc.minicommerce.order.model.Order;
import tr.nttdata.poc.minicommerce.order.repository.OrderRepository;
import tr.nttdata.poc.minicommerce.order.service.interfaces.IOrderService;

@Service
@Transactional
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Override
    public Order getOrderById(String orderId) throws ResourceNotFoundException, ServiceException {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            return order;
        } else {
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
    }

    @Override
    public List<Order> getOrdersByCustomerId(String customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        return orders;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders;
    }

    @Override
    public Order updateOrder(String orderId, Order updatedOrder)
            throws ResourceNotFoundException, ServiceException {
        Optional<Order> existingOrder = orderRepository.findById(orderId);
        if (existingOrder.isPresent()) {
            Order orderToUpdate = existingOrder.get();
            if (updatedOrder.getCart() != null)
                orderToUpdate.setCart(updatedOrder.getCart());

            if (updatedOrder.getTotalPrice() != null)
                orderToUpdate.setTotalPrice(updatedOrder.getTotalPrice());

            orderToUpdate.setOrderDate(new Date());
            orderToUpdate.setStatus(Order.Status.UPDATED.toString());

            Order updatedOrderObj = orderRepository.save(orderToUpdate);

            return updatedOrderObj;
        } else {
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
    }

    @Override
    public boolean deleteOrder(String orderId) throws ResourceNotFoundException, ServiceException {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true;
        } else {
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
    }

    @Override
    public Order checkout(String username) throws ResourceNotFoundException, ServiceException {
        Cart cart = cartService.getCart(username);
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new ServiceException("Cart is empty, cannot perform checkout.");
        }

        Order order = new Order();
        order.setCart(cart);
        order.setStatus(Order.Status.NEW.toString());
        cart.clearCart();
        return order;
    }

}
