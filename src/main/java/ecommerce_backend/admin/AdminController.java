package ecommerce_backend.admin;

import ecommerce_backend.orderservice.dto.OrderResponseDto;
import ecommerce_backend.orderservice.service.OrderService;
import ecommerce_backend.ratelimit.RateLimit;
import ecommerce_backend.userservice.userdto.response.UserResponseDto;
import ecommerce_backend.userservice.userservice.UserService;
import ecommerce_backend.utils.TrackPerformance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final OrderService orderService;

    public AdminController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/users")
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<List<UserResponseDto>> getUsers(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(userService.getallUsers(page, pageSize));
    }

    @GetMapping("/orders")
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<List<OrderResponseDto>> getOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/reports")
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<Map<String, Object>> getReport() {
        List<OrderResponseDto> orders = orderService.getAllOrders();
        BigDecimal revenue = orders.stream()
                .map(OrderResponseDto::getTotalPrice)
                .filter(total -> total != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int soldQuantity = orders.stream()
                .map(OrderResponseDto::getTotalQuantity)
                .filter(quantity -> quantity != null)
                .mapToInt(Integer::intValue)
                .sum();

        return ResponseEntity.ok(Map.of(
                "totalOrders", orders.size(),
                "totalRevenue", revenue,
                "totalSoldQuantity", soldQuantity
        ));
    }
}
