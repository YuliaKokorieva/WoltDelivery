package wolt.delivery_fee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wolt.delivery_fee.domain.FeeRequest;
import wolt.delivery_fee.service.FeeService;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class FeeAppTests {

    @Test
    public void calculateTest1() {
        FeeService feeService = new FeeService();
        FeeRequest testRequest = new FeeRequest(790, 2235, 4, Instant.parse("2021-01-16T13:00:00Z"));
        Integer testResult = feeService.calculateDeliveryFee(testRequest);
        assertThat(testResult).isEqualTo(710);
    }

    @Test
    public void calculateTestZeroFee() {
        FeeService feeService = new FeeService();
        FeeRequest testRequest = new FeeRequest(10001, 2235, 4, Instant.parse("2021-01-16T13:00:00Z"));
        Integer testResult = feeService.calculateDeliveryFee(testRequest);
        assertThat(testResult).isEqualTo(0);
    }

    @Test
    public void calculateTestRushHours() {
        FeeService feeService = new FeeService();
        FeeRequest testRequest = new FeeRequest(790, 2235, 4, Instant.parse("2022-01-14T16:00:00Z"));
        Integer testResult = feeService.calculateDeliveryFee(testRequest);
        assertThat(testResult).isEqualTo(781);
    }

    @Test
    public void calculateTestMax15() {
        FeeService feeService = new FeeService();
        FeeRequest testRequest = new FeeRequest(790, 100000, 40, Instant.parse("2022-01-14T16:00:00Z"));
        Integer testResult = feeService.calculateDeliveryFee(testRequest);
        assertThat(testResult).isEqualTo(1500);
    }

}
