package wolt.delivery_fee.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeeResponse {
    @JsonProperty("delivery_fee")
    private int deliveryFee;

    public FeeResponse(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
}
