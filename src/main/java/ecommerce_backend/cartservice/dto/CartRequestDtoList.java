package ecommerce_backend.cartservice.dto;

import lombok.Data;

@Data
public class CartRequestDtoList {
    private long cartItemId;
    private long productId;
    private Integer quantity;
}
