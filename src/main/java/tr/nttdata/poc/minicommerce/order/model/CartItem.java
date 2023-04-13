package tr.nttdata.poc.minicommerce.order.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    @JsonCreator
    public CartItem(@JsonProperty("productId") String productId,
            @JsonProperty("price") double price,
            @JsonProperty("quantity") int quantity) {
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

    public double getTotalPrice() {
        return price * quantity;
    }
}