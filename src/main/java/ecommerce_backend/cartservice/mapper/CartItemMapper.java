package ecommerce_backend.cartservice.mapper;

import ecommerce_backend.cartservice.dto.CartItemResponseDto;
import ecommerce_backend.cartservice.dto.CartItemResponseDtoList;
import ecommerce_backend.cartservice.entity.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartItemMapper {
    public static CartItemResponseDto fromEntity(CartItem cartItem){
        List<CartItemResponseDtoList> responseDto=new ArrayList<>();
        CartItemResponseDtoList list=new CartItemResponseDtoList();
        list.setId(cartItem.getId());
        list.setQuantity(cartItem.getQuantity());
        list.setPrice(cartItem.getPrice());
        list.setProductId(cartItem.getProductId());
        list.setProductName(cartItem.getProductName());
        responseDto.add(list);
        CartItemResponseDto dto=new CartItemResponseDto();
        dto.setResponseDtoLists(responseDto);
        return dto;
    }
}
