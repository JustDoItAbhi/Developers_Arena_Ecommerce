package ecommerce_backend.orderservice.model;

import ecommerce_backend.cartservice.dto.CartItemResponseDtoList;
import ecommerce_backend.cartservice.entity.Cart;
import ecommerce_backend.cartservice.entity.CartItem;
import ecommerce_backend.productservice.entity.BaseModel;
import ecommerce_backend.userservice.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order extends BaseModel {
    @OneToOne
    private Cart cart;
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItems> orderItems;
    private BigDecimal totalPrice;

    private Integer totalQuantity;
    @Enumerated(EnumType.STRING)
    private OrderEnum orderEnum;
}
