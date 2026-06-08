package ecommerce_backend.cartservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponseDto {
    private String userEmail;
    private long cartID;
    private List<CartItemResponseDtoList>cartItems;
    private BigDecimal TotalPrice;
    private Integer totalQuantity;
}
