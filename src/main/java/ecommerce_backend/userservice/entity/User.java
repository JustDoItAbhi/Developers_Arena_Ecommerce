package ecommerce_backend.userservice.entity;

import ecommerce_backend.orderservice.model.Order;
import ecommerce_backend.productservice.entity.BaseModel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "users")
public class User extends BaseModel {
    private String name;
    private String email;
    private String password;
    private String contactNumber;
    @Embedded
    private Address address;
    @Enumerated(EnumType.STRING)
    private List<Role> roles;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Order>orderList;
}
