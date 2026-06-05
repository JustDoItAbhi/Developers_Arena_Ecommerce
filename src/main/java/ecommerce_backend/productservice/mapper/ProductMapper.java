package ecommerce_backend.productservice.mapper;

import ecommerce_backend.productservice.dtos.ProductDTO;
import ecommerce_backend.productservice.dtos.ProductResponseDTO;
import ecommerce_backend.productservice.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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
