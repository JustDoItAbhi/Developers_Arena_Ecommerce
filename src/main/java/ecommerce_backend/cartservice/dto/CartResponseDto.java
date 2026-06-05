package ecommerce_backend.cartservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponseDto {
    private long cartID;
    private List<CartItemResponseDtoList>responseDtoLists;
    private BigDecimal TotalPrice;
    private Integer TotalNumberOfItemsSelected;
}
