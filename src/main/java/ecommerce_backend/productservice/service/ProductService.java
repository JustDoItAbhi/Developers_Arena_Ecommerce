package ecommerce_backend.productservice.service;

import ecommerce_backend.productservice.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductDTO dto);
    Page<ProductResponseDTO> getAllProducts(int page, int size);
    ProductResponseDTO getProductById(Long id);
    boolean deleteProduct(Long id);
    ProductResponseDTO update(Long id,ProductDTO dto);
    List<ProductResponseDTO> filtersForProduct(ProductSearchParams params, int pageable, int pageSize);


}
