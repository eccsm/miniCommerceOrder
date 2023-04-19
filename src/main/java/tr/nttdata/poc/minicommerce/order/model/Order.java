package tr.nttdata.poc.minicommerce.order.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order implements Serializable {
    @Id
    private String id;
    private String customerId;
    private Cart cart;
    private Double totalPrice;
    private LocalDateTime orderDate;
    private String status; 

    public enum Status {
        NEW, UPDATED, DELIVERED
    }
}
