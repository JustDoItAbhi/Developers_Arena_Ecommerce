package ecommerce_backend.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping
    public ResponseEntity<OrderResponseDto>confirmOrder(@RequestBody OrderRequestDto dto){
        return ResponseEntity.ok(orderService.confirmOrder(dto.getCartId(),dto.getEmail()));
    }
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>>getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

}
