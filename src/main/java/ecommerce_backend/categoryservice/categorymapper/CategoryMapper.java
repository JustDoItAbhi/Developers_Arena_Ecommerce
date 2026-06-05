package ecommerce_backend.categoryservice.categorymapper;

import ecommerce_backend.categoryservice.categorydto.CategoryResponseDto;
import ecommerce_backend.productservice.entity.Category;
import org.modelmapper.ModelMapper;

public class CategoryMapper {
    private final ModelMapper mapper;

    public CategoryMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }
    public  CategoryResponseDto fromCategory(Category category){
        CategoryResponseDto dto=mapper.map(category,CategoryResponseDto.class);
        return dto;
    }
}
