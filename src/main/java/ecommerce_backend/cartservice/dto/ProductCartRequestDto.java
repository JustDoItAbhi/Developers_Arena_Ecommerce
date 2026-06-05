package ecommerce_backend.cartservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductCartRequestDto {
    private List<Long>productId;
}
