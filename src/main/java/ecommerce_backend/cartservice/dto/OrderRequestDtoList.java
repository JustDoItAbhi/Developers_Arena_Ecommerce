package ecommerce_backend.cartservice.dto;

import lombok.Data;

@Data
public class OrderRequestDtoList {
    private long  orderItemId;
    private long productId;
    private Integer quantity;
}
