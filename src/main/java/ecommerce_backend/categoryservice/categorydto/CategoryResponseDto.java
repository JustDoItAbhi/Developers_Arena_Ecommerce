package ecommerce_backend.categoryservice.categorydto;

import ecommerce_backend.productservice.dtos.ProductResponseDTO;
import lombok.Data;

import java.util.List;
@Data
public class CategoryResponseDto {
    private long id;
    private String name;
    private String description;
//    private List<ProductResponseDTO> productList;
}
