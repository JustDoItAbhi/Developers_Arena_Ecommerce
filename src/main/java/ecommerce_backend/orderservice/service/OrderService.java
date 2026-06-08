package ecommerce_backend.orderservice.service;

import ecommerce_backend.cartservice.dto.AddToOrderRequest;
import ecommerce_backend.orderservice.dto.OrderRequestDto;
import ecommerce_backend.orderservice.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {
    OrderResponseDto placeOrder(OrderRequestDto dto);
    List<OrderResponseDto>getAllOrders();
    boolean deleteOrder(long id);
    String ConfirmOrder(String email);
}
