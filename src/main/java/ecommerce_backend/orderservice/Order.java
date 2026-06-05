package ecommerce_backend.orderservice;

import ecommerce_backend.cartservice.entity.Cart;
import ecommerce_backend.productservice.entity.BaseModel;
import ecommerce_backend.userservice.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order extends BaseModel {
    @OneToOne
    private Cart cart;
    @OneToOne
    private User user;
}
