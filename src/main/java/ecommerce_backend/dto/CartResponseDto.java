package ecommerce_backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartResponseDto {
    private long id;
    private String name;
    private BigDecimal totalPrice;
    private Integer numberOfitem;
}
