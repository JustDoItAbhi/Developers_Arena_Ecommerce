package ecommerce_backend.orderservice.model;

import ecommerce_backend.cartservice.entity.Cart;
import ecommerce_backend.productservice.entity.BaseModel;
import ecommerce_backend.productservice.entity.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
public class OrderItems extends BaseModel {
    @OneToOne(fetch = FetchType.LAZY)
    private Product product;
    private Integer quantity;
    private BigDecimal total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
