package ecommerce_backend.orderservice;

import ecommerce_backend.cartservice.dto.CartResponseDto;
import ecommerce_backend.userservice.userdto.response.UserResponseDto;
import lombok.Data;

@Data
public class OrderResponseDto {
    private long orderId;
    private CartResponseDto cartResponseDto;
    private UserResponseDto userResponseDto;
}
