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


//    public CartResponseDto fromCartEntity(Cart cart){
//        if (cart.getCartItems() != null && !cart.getCartItems().isEmpty()) {
//            System.out.println("Cart has " + cart.getCartItems().size() + " items");
//            System.out.println("First item: " + cart.getCartItems().get(0));
//        } else {
//            System.out.println("WARNING: Cart has totalQuantity " + cart.getTotalQuantity() +
//                    " but cartItems is " + (cart.getCartItems() == null ? "null" : "empty"));
//        }
//        CartResponseDto dto=mapper.map(cart,CartResponseDto.class);
//        System.out.println("Cart ID: " + cart.getId());
//        System.out.println("Cart items size: " + cart.getCartItems().size());
//        System.out.println("Cart items: " + cart.getCartItems());
//
//        return dto;
//    }

    public static CartResponseDto fromCartEntity(Cart cart){
        CartResponseDto responseDto = new CartResponseDto();
        responseDto.setUserEmail(cart.getUserEmail());
        responseDto.setCartID(cart.getId());
        responseDto.setTotalPrice(cart.getTotalPrice());
        responseDto.setTotalQuantity(cart.getTotalQuantity());


        List<CartItemResponseDtoList> list = new ArrayList<>();
        for (CartItem item : cart.getCartItems()) {
            CartItemResponseDtoList responseDtoList = new CartItemResponseDtoList();
            responseDtoList.setQuantity(item.getQuantity());
            responseDtoList.setId(item.getId());
            responseDtoList.setPrice(item.getPrice());
            responseDtoList.setProductName(item.getProductName());
            responseDtoList.setProductId(item.getProductId());
            responseDtoList.setTotal(item.getTotal());
            list.add(responseDtoList);
        }
        responseDto.setCartItems(list);
        return responseDto;
    }

}
