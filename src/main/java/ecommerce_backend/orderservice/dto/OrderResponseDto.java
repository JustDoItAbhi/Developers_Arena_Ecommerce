package ecommerce_backend.orderservice.dto;

import ecommerce_backend.orderservice.model.OrderEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto {
    private OrderEnum status;
    private String useEmail;
    private LocalDateTime orderCreatedAt;
    private long orderID;
    private List<Long> orderItemsIds;
    private List<Long>productIds;
    private BigDecimal TotalPrice;
    private Integer totalQuantity;
    private String userEmail;


}
