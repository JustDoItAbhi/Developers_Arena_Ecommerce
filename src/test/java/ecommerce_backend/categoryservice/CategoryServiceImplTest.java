package ecommerce_backend.categoryservice;

import ecommerce_backend.categoryservice.categorydto.CategoryRequestDto;
import ecommerce_backend.categoryservice.categorydto.CategoryResponseDto;
import ecommerce_backend.categoryservice.categoryrepository.CategoryRepository;
import ecommerce_backend.categoryservice.categoryservice.CategoryServiceImpl;
import ecommerce_backend.productservice.entity.Category;
import ecommerce_backend.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void createCategorySavesNewCategory() {
        CategoryRequestDto request = categoryRequest();
        CategoryResponseDto response = new CategoryResponseDto();
        response.setName(request.getName());
        response.setDescription(request.getDescription());

        when(categoryRepository.findByName(request.getName())).thenReturn(Optional.empty());
        when(mapper.map(any(Category.class), any())).thenReturn(response);

        CategoryResponseDto created = categoryService.createCategory(request);

        assertThat(created.getName()).isEqualTo("Electronics");
        assertThat(created.getDescription()).isEqualTo("Devices and accessories");
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void createCategoryReturnsExistingCategoryWithoutSavingDuplicate() {
        CategoryRequestDto request = categoryRequest();
        Category existing = new Category();
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());

        CategoryResponseDto response = new CategoryResponseDto();
        response.setName(existing.getName());
        response.setDescription(existing.getDescription());

        when(categoryRepository.findByName(request.getName())).thenReturn(Optional.of(existing));
        when(mapper.map(existing, CategoryResponseDto.class)).thenReturn(response);

        CategoryResponseDto created = categoryService.createCategory(request);

        assertThat(created.getName()).isEqualTo("Electronics");
        verify(categoryRepository, never()).save(any(Category.class));
    }

    private CategoryRequestDto categoryRequest() {
        CategoryRequestDto dto = new CategoryRequestDto();
        dto.setName("Electronics");
        dto.setDescription("Devices and accessories");
        return dto;
    }
}
