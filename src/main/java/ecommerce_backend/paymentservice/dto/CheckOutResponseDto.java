package ecommerce_backend.paymentservice.dto;

import ecommerce_backend.paymentservice.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CheckOutResponseDto {
    private final PaymentStatus status;
    private final String message;
    private final String url;



}
