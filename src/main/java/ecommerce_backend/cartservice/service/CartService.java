package ecommerce_backend.cartservice.service;

import ecommerce_backend.cartservice.dto.*;

import java.util.List;

public interface CartService {

    CartResponseDto addToCart(AddToOrderRequest dto);
    List<CartResponseDto> getAllCarts();
    CartResponseDto getCartByID(long id);
    boolean deleteCart(long id);

}
