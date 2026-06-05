package ecommerce_backend.cartservice.entity;

import ecommerce_backend.productservice.entity.BaseModel;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_item")
@Data
public class CartItem extends BaseModel {

    private Long productId;

    private String productName;

    private BigDecimal price;

    private Integer stock;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
