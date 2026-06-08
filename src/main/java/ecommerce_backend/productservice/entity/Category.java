package ecommerce_backend.productservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name = "categories")
public class Category extends BaseModel{
    private String name;
    private String description;
    @OneToMany(   mappedBy = "category",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Product>productList=new ArrayList<>();
}
