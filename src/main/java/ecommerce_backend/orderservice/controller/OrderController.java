package ecommerce_backend.orderservice.controller;

import ecommerce_backend.cartservice.dto.AddToOrderRequest;
import ecommerce_backend.orderservice.dto.OrderRequestDto;
import ecommerce_backend.orderservice.dto.OrderResponseDto;
import ecommerce_backend.orderservice.dto.orderitemdto.ConfirmOrderDto;
import ecommerce_backend.orderservice.service.OrderService;
import ecommerce_backend.ratelimit.RateLimit;
import ecommerce_backend.utils.TrackPerformance;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<OrderResponseDto>placeOrder(@RequestBody OrderRequestDto dto){
        return ResponseEntity.ok(orderService.placeOrder(dto));
    }
    @GetMapping("/confirm")
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<String>placeOrder(@RequestBody ConfirmOrderDto dto){
        return ResponseEntity.ok(orderService.ConfirmOrder(dto.getEmail()));
    }
    @GetMapping
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<List<OrderResponseDto>>getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }
    @DeleteMapping("/{id}")
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<Boolean>deleteOrders(@PathVariable("id")long id){
        return ResponseEntity.ok(orderService.deleteOrder(id));
    }

}
