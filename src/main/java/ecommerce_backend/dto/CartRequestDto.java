package ecommerce_backend.dto;

import java.math.BigDecimal;

public class CartRequestDto {
    private long id;
    private String name;
    private BigDecimal price;
    private Integer stockQuantity;
}
