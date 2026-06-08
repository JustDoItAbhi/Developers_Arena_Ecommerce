package ecommerce_backend.orderservice.dto;

import lombok.Data;

@Data
public class OrderRequestDto {
   private long cartId;
   private String email;
}
