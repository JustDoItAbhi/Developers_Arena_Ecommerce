package ecommerce_backend.productservice;

import ecommerce_backend.categoryservice.categoryrepository.CategoryRepository;
import ecommerce_backend.categoryservice.exceptions.CategoryNotFoundException;
import ecommerce_backend.productservice.dtos.ProductDTO;
import ecommerce_backend.productservice.dtos.ProductResponseDTO;
import ecommerce_backend.productservice.entity.Category;
import ecommerce_backend.productservice.entity.Product;
import ecommerce_backend.productservice.repository.ProductRepository;
import ecommerce_backend.productservice.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void createProductSavesProductWhenCategoryExists() {
        ProductDTO request = productRequest();
        Category category = new Category();
        category.setName("Electronics");

        ProductResponseDTO response = new ProductResponseDTO();
        response.setName(request.getName());
        response.setPrice(request.getPrice());

        when(productRepository.findByName(request.getName())).thenReturn(Optional.empty());
        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(modelMapper.map(any(Product.class), any())).thenReturn(response);

        ProductResponseDTO created = productService.createProduct(request);

        assertThat(created.getName()).isEqualTo("Laptop Stand");
        assertThat(created.getPrice()).isEqualByComparingTo("49.99");
        verify(productRepository).save(any(Product.class));
        verify(categoryRepository).saveAndFlush(category);
    }

    @Test
    void createProductThrowsWhenCategoryDoesNotExist() {
        ProductDTO request = productRequest();

        when(productRepository.findByName(request.getName())).thenReturn(Optional.empty());
        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.createProduct(request))
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessageContaining("CATEGORY NOT FOUND Electronics");

        verify(productRepository, never()).save(any(Product.class));
    }

    private ProductDTO productRequest() {
        ProductDTO dto = new ProductDTO();
        dto.setName("Laptop Stand");
        dto.setDescription("Adjustable aluminum stand");
        dto.setPrice(new BigDecimal("49.99"));
        dto.setStockQuantity(25);
        dto.setCategory("Electronics");
        return dto;
    }
}
