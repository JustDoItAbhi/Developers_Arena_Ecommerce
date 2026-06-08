package ecommerce_backend.cartservice.dto;

import lombok.Data;

import java.util.List;
@Data
public class AddToOrderRequest {
    private String userEmail;
    private List<CartRequestDtoList> cartRequestDtoLists;
}
