package ecommerce_backend.productservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
@Table(name = "Category")
public class Category extends BaseModel{
    private String name;
    private String description;
    @OneToMany(   mappedBy = "category",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Product>productList;
}
