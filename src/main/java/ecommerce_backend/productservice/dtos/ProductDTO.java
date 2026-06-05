package ecommerce_backend.productservice.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductDTO {
    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 100)
    private String name;
    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @Min(0)
    private Integer stockQuantity;
    private String category;
}
