package ecommerce_backend.cartservice.service;

import ecommerce_backend.cartservice.dto.*;

import java.util.List;

public interface CartItemsService {
    List<CartItemResponseDto> savesToDatabase(ProductCartRequestDto dto);
    List<CartItemResponseDtoList>findAllCartItems();
    String deleteCartItems(long id);

}
