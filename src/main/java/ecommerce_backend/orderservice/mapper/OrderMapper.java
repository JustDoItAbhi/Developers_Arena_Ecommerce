package ecommerce_backend.orderservice.mapper;


import ecommerce_backend.cartservice.entity.CartItem;
import ecommerce_backend.orderservice.model.OrderItems;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class OrderMapper {
    public final ModelMapper mapper;

    public OrderMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }
    public List<OrderItems> fromCartItems(List<CartItem> cartItem){
        return mapper.map(cartItem, new TypeToken<List<OrderItems>>(){}.getType());
    }

}
