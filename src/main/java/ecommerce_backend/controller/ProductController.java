package ecommerce_backend.controller;

import ecommerce_backend.dtos.ProductDTO;
import ecommerce_backend.dtos.ProductResponseDTO;
import ecommerce_backend.service.ProductService;
import ecommerce_backend.utils.DbTimeHolder;
import ecommerce_backend.utils.TrackPerformance;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;


    @PostMapping
    @TrackPerformance
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        DbTimeHolder.setDbStartTime(System.nanoTime());
        ProductResponseDTO createdProduct = productService.createProduct(productDTO);
        DbTimeHolder.recordDbTime();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping
 @TrackPerformance
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        DbTimeHolder.setDbStartTime(System.nanoTime());
        Page<ProductResponseDTO> products = productService.getAllProducts(page, size);
        DbTimeHolder.recordDbTime();

        return ResponseEntity.ok(products.getContent());
    }

    @GetMapping("/{id}")
    @TrackPerformance
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        DbTimeHolder.setDbStartTime(System.nanoTime());
        ProductResponseDTO product = productService.getProductById(id);
        DbTimeHolder.recordDbTime();
        return ResponseEntity.ok(product);
    }
    @PutMapping("/{id}")
    @TrackPerformance
    public ResponseEntity<ProductResponseDTO> update(@PathVariable("id")Long id,
                                                     @RequestBody ProductDTO dto){
        DbTimeHolder.setDbStartTime(System.nanoTime());
        ProductResponseDTO dto1=productService.update(id,dto);
        DbTimeHolder.recordDbTime();
        return ResponseEntity.ok(dto1);
    }
    @DeleteMapping("/{id}")
    @TrackPerformance
    public ResponseEntity<Boolean> deleteById(@PathVariable("id")Long id){
        DbTimeHolder.setDbStartTime(System.nanoTime());
        boolean result=productService.deleteProduct(id);
        DbTimeHolder.recordDbTime();
        return ResponseEntity.ok(result);
    }
}
