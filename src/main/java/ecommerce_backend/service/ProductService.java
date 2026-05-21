package ecommerce_backend.service;

import ecommerce_backend.dtos.ProductDTO;
import ecommerce_backend.dtos.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductDTO dto);
    Page<ProductResponseDTO> getAllProducts(int page, int size);
    ProductResponseDTO getProductById(Long id);
    boolean deleteProduct(Long id);
    ProductResponseDTO update(Long id,ProductDTO dto);

}
