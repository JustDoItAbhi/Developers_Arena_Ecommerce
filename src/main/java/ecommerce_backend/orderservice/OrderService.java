package ecommerce_backend.orderservice;

import java.util.List;

public interface OrderService {
    OrderResponseDto confirmOrder(long cartId,String email);
    List<OrderResponseDto>getAllOrders();
}
