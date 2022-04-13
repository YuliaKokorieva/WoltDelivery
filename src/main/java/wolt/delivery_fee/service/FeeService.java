package wolt.delivery_fee.service;
import wolt.delivery_fee.domain.FeeRequest;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class FeeService {

    private static final int CENTS_PER_EUR = 100;
    private static final int FIXED_PRICE_DISTANCE = 1000;
    private static final int BASIC_NUMBER_OF_ITEMS = 4;
    private static final int SMALL_ORDER_SURCHARGE_BASE_EUR = 10;
    private static final int ABOVE_4_ITEMS_SURCHARGE=50;
    private static final String RUSH_HOUR_START = "14:59:59";
    private static final String RUSH_HOUR_END = "19:00:01";
    private static final int MAX_FEE_EUR=15;
    private static final int FREE_DELIVERY_CART_VALUE_EUR=100;
    private static final double PAYABLE_DISTANCE_STEP= 500.0;
    private static final int BASIC_DEL_FEE_FOR_1000_M_EUR = 2;

    public int calculateDeliveryFee(FeeRequest request) {

        if (isDeliveryFree(request)) {
            return 0;
        } else {
            int fee = (int) Math.round((calculateSmallOrderSurcharge(request) + calculateDistanceFee(request) + calculateNumerousSurcharge(request)) * calculateRushHoursSurchargeIfApplicable(request));
            if (fee > MAX_FEE_EUR * CENTS_PER_EUR) {
                fee = MAX_FEE_EUR * CENTS_PER_EUR;
            }
            return fee;
        }
    }

    private int calculateSmallOrderSurcharge(FeeRequest request) {
        if (request.getCartValue() < SMALL_ORDER_SURCHARGE_BASE_EUR * CENTS_PER_EUR) {
            return SMALL_ORDER_SURCHARGE_BASE_EUR * CENTS_PER_EUR - request.getCartValue();
        } else {
            return 0;
        }
    }

    private boolean isDeliveryFree(FeeRequest request) {
        return request.getCartValue() >= FREE_DELIVERY_CART_VALUE_EUR * CENTS_PER_EUR;
    }

    private int calculateDistanceFee(FeeRequest request) {
        int basicFee = BASIC_DEL_FEE_FOR_1000_M_EUR * CENTS_PER_EUR;
        if (request.getDeliveryDistance() <= FIXED_PRICE_DISTANCE) {
            return basicFee;
        } else {
            return (basicFee + ((int) Math.ceil((request.getDeliveryDistance() - FIXED_PRICE_DISTANCE) / PAYABLE_DISTANCE_STEP) * CENTS_PER_EUR));
        }
    }

    private int calculateNumerousSurcharge(FeeRequest request) {
        if (request.getNumberOfItems() > BASIC_NUMBER_OF_ITEMS) {
            return (request.getNumberOfItems() - BASIC_NUMBER_OF_ITEMS) * ABOVE_4_ITEMS_SURCHARGE;
        } else {
            return 0;
        }
    }

    private double calculateRushHoursSurchargeIfApplicable(FeeRequest request) {
        if (isRushHoursSurchargeApplicable(request)) {
            return 1.1;
        } else {
            return 1;
        }
    }

    private boolean isRushHoursSurchargeApplicable(FeeRequest request) {
        Instant instant = request.getTime();
        LocalTime time = instant.atZone(ZoneOffset.UTC).toLocalTime();
        LocalDate date = instant.atZone(ZoneOffset.UTC).toLocalDate();
        return (date.getDayOfWeek().getValue() == 5) && (time.isAfter(LocalTime.parse(RUSH_HOUR_START)))
                && (time.isBefore(LocalTime.parse(RUSH_HOUR_END)));
    }

}
