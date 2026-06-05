package ecommerce_backend.cartservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartItemResponseDto {
 private List<CartItemResponseDtoList>responseDtoLists;
}