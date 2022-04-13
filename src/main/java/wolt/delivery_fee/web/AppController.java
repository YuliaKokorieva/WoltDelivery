package wolt.delivery_fee.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wolt.delivery_fee.domain.FeeRequest;
import wolt.delivery_fee.domain.FeeResponse;
import wolt.delivery_fee.service.FeeService;

import javax.validation.Valid;

@RestController
public class AppController {

    FeeService feeService = new FeeService();

    @PostMapping(value = "/fee", consumes = "application/json", produces = "application/json")
    public FeeResponse calculateFee(@Valid @RequestBody FeeRequest request) {
        return new FeeResponse(feeService.calculateDeliveryFee(request));
    }
}