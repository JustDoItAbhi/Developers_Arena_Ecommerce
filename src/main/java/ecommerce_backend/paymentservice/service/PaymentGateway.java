package ecommerce_backend.paymentservice.service;

import com.stripe.exception.StripeException;
import ecommerce_backend.paymentservice.dto.CheckOutResponseDto;
import ecommerce_backend.paymentservice.dto.PaymentResponseDto;

public interface PaymentGateway {
    CheckOutResponseDto topay(long id, String email) throws StripeException;
    PaymentResponseDto FromOrder(String email,long orderId);
}
