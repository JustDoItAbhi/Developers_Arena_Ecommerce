package ecommerce_backend.cartservice.entity;

import ecommerce_backend.productservice.entity.BaseModel;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "cart")
public class Cart extends BaseModel {
    @OneToMany(mappedBy = "cart",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CartItem>cartItems;
    private BigDecimal totalPrice;
    private Integer totalQuantity;
}
