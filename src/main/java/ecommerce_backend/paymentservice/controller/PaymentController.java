package ecommerce_backend.paymentservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stripe.exception.StripeException;
import ecommerce_backend.categoryservice.exceptions.UserNotFoundException;
import ecommerce_backend.paymentservice.dto.CheckOutResponseDto;
import ecommerce_backend.paymentservice.dto.PaymentResponseDto;
import ecommerce_backend.paymentservice.service.PaymentGateway;
import ecommerce_backend.ratelimit.RateLimit;
import ecommerce_backend.utils.TrackPerformance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")

public class PaymentController {
    private final PaymentGateway paymentGateway;
    public PaymentController(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    @GetMapping("/{id}/{email}")
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<CheckOutResponseDto> getOrderForPayment(@PathVariable("id") long id, @PathVariable ("email")String email) throws StripeException{
        return ResponseEntity.ok(paymentGateway.topay(id,email));
    }
    @GetMapping("/getDelivery/{email}/{orderId}")
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<PaymentResponseDto> deleverydetails( @PathVariable ("email")String email,
                                                               @PathVariable ("orderId")long orerId  ){
        return ResponseEntity.ok(paymentGateway.FromOrder(email,orerId));
    }

}