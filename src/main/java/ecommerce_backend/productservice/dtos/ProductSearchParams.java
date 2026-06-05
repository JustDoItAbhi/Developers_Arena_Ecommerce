package ecommerce_backend.productservice.dtos;

import ecommerce_backend.productservice.entity.Category;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductSearchParams {
    private String name;
    private String description;
    private BigDecimal minprice;
    private BigDecimal maxPrice;
    private Integer minStock;
    private Integer maxStock;
    private Integer stock;
    private String category;
}
