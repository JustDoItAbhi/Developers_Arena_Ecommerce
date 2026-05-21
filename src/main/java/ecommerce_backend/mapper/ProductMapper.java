package ecommerce_backend.mapper;

import ecommerce_backend.dtos.ProductDTO;
import ecommerce_backend.dtos.ProductResponseDTO;
import ecommerce_backend.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class ProductMapper {
    ModelMapper modelMapper = new ModelMapper();
    public Product fromDtoToentity(ProductDTO productDTO){
        Product product = modelMapper.map(productDTO, Product.class);
        return product;
    }

    public ProductResponseDTO fromEntityToDto(Product product){
        ProductResponseDTO productResponse = modelMapper.map(product, ProductResponseDTO.class);
        return productResponse;
    }

}
