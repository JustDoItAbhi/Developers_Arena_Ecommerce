package ecommerce_backend.paymentservice.dto;

import ecommerce_backend.cartservice.dto.CartResponseDto;
import ecommerce_backend.orderservice.dto.OrderResponseDto;
import ecommerce_backend.paymentservice.PaymentStatus;
import ecommerce_backend.productservice.dtos.ProductResponseDTO;
import ecommerce_backend.userservice.userdto.response.UserResponseDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponseDto {
    private LocalDateTime orderCreatedAt;
    private long orderID;
    private BigDecimal TotalPrice;
    private Integer totalQuantity;
   private PaymentStatus message;
   private String userAddress;
}
