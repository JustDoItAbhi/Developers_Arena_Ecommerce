package ecommerce_backend.orderservice.dto;

import ecommerce_backend.orderservice.model.OrderEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class OrderConfimrationDto {
    private OrderEnum status;
    private String useEmail;
    private LocalDateTime orderCreatedAt;
    private long orderID;
    private BigDecimal TotalPrice;
    private Integer totalQuantity;
}
