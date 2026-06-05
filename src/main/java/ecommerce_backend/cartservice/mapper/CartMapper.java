package ecommerce_backend.cartservice.mapper;

import ecommerce_backend.cartservice.dto.CartItemResponseDtoList;
import ecommerce_backend.cartservice.dto.CartResponseDto;
import ecommerce_backend.cartservice.entity.Cart;
import ecommerce_backend.cartservice.dto.CartItemResponseDto;
import ecommerce_backend.cartservice.entity.CartItem;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartMapper {
    public final ModelMapper mapper;

    public CartMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public static   CartItemResponseDto fromEntity(CartItem cartItem){
        List<CartItemResponseDtoList>responseDto=new ArrayList<>();
        CartItemResponseDtoList list=new CartItemResponseDtoList();
        list.setId(cartItem.getId());
        list.setNumberOfItemsSelected(cartItem.getQuantity());
        list.setPrice(cartItem.getPrice());
        list.setProductId(cartItem.getProductId());
        list.setProductName(cartItem.getProductName());
        responseDto.add(list);
        CartItemResponseDto dto=new CartItemResponseDto();
        dto.setResponseDtoLists(responseDto);
        return dto;
    }
    public CartResponseDto fromCartEntity(Cart cart){
        CartResponseDto dto=mapper.map(cart,CartResponseDto.class);
        return dto;
    }

}
