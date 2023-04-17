package tr.nttdata.poc.minicommerce.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class CartItem implements Serializable {
    @NotBlank(message = "Product ID cannot be blank")
    private String productId;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price should be greater than or equal to 0")
    private double price;

    @Min(value = 1, message = "Quantity should be greater than or equal to 1")
    private int quantity;

    public CartItem() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @JsonIgnore
    public double getTotalPrice() {
        return price * quantity;
    }
}