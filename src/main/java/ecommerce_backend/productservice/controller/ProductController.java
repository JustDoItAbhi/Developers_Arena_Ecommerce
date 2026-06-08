package ecommerce_backend.productservice.controller;

import ecommerce_backend.productservice.dtos.*;
import ecommerce_backend.productservice.service.ProductService;
import ecommerce_backend.utils.DbTimeHolder;
import ecommerce_backend.utils.TrackPerformance;
import ecommerce_backend.ratelimit.RateLimit;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;


    @PostMapping
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductResponseDTO createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ProductResponseDTO> products = productService.getAllProducts(page, size);
        return ResponseEntity.ok(products.getContent());
    }

    @GetMapping("/{id}")
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
    @PutMapping("/{id}")
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<ProductResponseDTO> update(@PathVariable("id")Long id,
                                                     @RequestBody ProductDTO dto){
        ProductResponseDTO dto1=productService.update(id,dto);
        return ResponseEntity.ok(dto1);
    }
    @DeleteMapping("/{id}")
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<Boolean> deleteById(@PathVariable("id")Long id){
        boolean result=productService.deleteProduct(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/search")
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<List<ProductResponseDTO>> AllProductsWithFilters(@RequestBody ProductSearchParams params,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size) {
        List<ProductResponseDTO> products = productService.filtersForProduct(params,page,size);
        return ResponseEntity.ok(products);
    }

}
