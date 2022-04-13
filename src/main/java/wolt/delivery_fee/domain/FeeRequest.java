package wolt.delivery_fee.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Instant;

public class FeeRequest {

    @Min(value = 1, message = "Cart value cannot be 0 or negative")
    @NotNull(message = "Cart value required")
    @JsonProperty("cart_value")
    private int cartValue;

    @Min(value = 1, message = "Delivery distance cannot be 0 or negative")
    @NotNull(message = "Delivery distance required")
    @JsonProperty("delivery_distance")
    private int deliveryDistance;

    @Min(value = 1, message = "Number of items cannot be 0 or negative")
    @NotNull(message = "number_of_items required")
    @JsonProperty("number_of_items")
    private int numberOfItems;

    @NotNull
    private final Instant time;

    public FeeRequest(int cartValue, int deliveryDistance, int numberOfItems, Instant time) {
        this.cartValue = cartValue;
        this.deliveryDistance = deliveryDistance;
        this.numberOfItems = numberOfItems;
        this.time = time;
    }

    public int getCartValue() {
        return cartValue;
    }

    public int getDeliveryDistance() {
        return deliveryDistance;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public Instant getTime() {
        return time;
    }
}
