package ecommerce_backend.categoryservice.categoryservice;

import ecommerce_backend.categoryservice.categorydto.CategoryRequestDto;
import ecommerce_backend.categoryservice.categorydto.CategoryResponseDto;
import ecommerce_backend.productservice.dtos.ProductResponseDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto createCategory(CategoryRequestDto dto);
    CategoryResponseDto findById(long id);
    boolean deleteCategory(long id);
    List<CategoryResponseDto>findAllCategories(int page, int pageSize);
    CategoryResponseDto updateCategory(long id, CategoryRequestDto dto);
    List<ProductResponseDTO> getAllProductByCategoryid(long categoryId);
}
