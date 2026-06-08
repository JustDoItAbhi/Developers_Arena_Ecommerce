package ecommerce_backend.cartservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponseDtoList {
    private long id;
    private long productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal total;
}
