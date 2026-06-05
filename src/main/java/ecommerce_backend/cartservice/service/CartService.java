package ecommerce_backend.cartservice.service;

import ecommerce_backend.cartservice.dto.*;

import java.util.List;

public interface CartService {
   List<CartItemResponseDto> savesToDatabase(ProductCartRequestDto dto);
    CartResponseDto addToCart(AddToCartRequest dto);
    List<CartItemResponseDtoList>findAllCartItems();
    String deleteCartItems(long id);
    List<CartResponseDto> getAllCarts();

}
