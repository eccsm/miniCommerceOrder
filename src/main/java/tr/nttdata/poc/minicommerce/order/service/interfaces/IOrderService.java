
package tr.nttdata.poc.minicommerce.order.service.interfaces;

import java.util.List;

import org.hibernate.service.spi.ServiceException;

import tr.nttdata.poc.minicommerce.order.exception.ResourceNotFoundException;
import tr.nttdata.poc.minicommerce.order.model.Order;

public interface IOrderService {
    Order getOrderById(String orderId) throws ResourceNotFoundException, ServiceException;

    List<Order> getOrdersByCustomerId(String customerId);

    List<Order> getAllOrders();

    Order updateOrder(String orderId, Order updatedOrder) throws ResourceNotFoundException, ServiceException;

    boolean deleteOrder(String orderId) throws ResourceNotFoundException, ServiceException;

    Order checkout(String customerId) throws ResourceNotFoundException, ServiceException;

}
