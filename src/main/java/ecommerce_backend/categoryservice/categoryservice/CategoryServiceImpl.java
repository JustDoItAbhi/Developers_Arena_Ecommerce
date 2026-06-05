package ecommerce_backend.categoryservice.categoryservice;

import ecommerce_backend.categoryservice.categorydto.CategoryRequestDto;
import ecommerce_backend.categoryservice.categorydto.CategoryResponseDto;
import ecommerce_backend.categoryservice.categoryrepository.CategoryRepository;
import ecommerce_backend.productservice.dtos.ProductResponseDTO;
import ecommerce_backend.productservice.entity.Category;
import ecommerce_backend.productservice.entity.Product;
import ecommerce_backend.productservice.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;
    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto dto) {
        Optional<Category>exsistingCategory=categoryRepository.findByName(dto.getName());
        if(exsistingCategory.isPresent()){
            CategoryResponseDto responseDto=mapper.map(exsistingCategory.get(),CategoryResponseDto.class);
            return responseDto;
        }
        Category category=new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        categoryRepository.save(category);
        CategoryResponseDto categoryResponseDto=mapper.map(category,CategoryResponseDto.class);
        return categoryResponseDto;
    }

    @Override
    public CategoryResponseDto findById(long id) {
        Category category=categoryRepository.findById(id).orElseThrow(
                ()->new RuntimeException("no such category exists "+ id));
        CategoryResponseDto responseDto=mapper.map(category,CategoryResponseDto.class);
        return responseDto;
    }

    @Override
    public boolean deleteCategory(long id) {
        Category category=categoryRepository.findById(id).orElseThrow(
                ()->new RuntimeException("no such category exists "+ id));
        List<Product>product=productRepository.findByCategoryId(category.getId());
      productRepository.deleteAll(product);
        categoryRepository.deleteById(id);
        return true;
    }

    @Override
    public List<CategoryResponseDto> findAllCategories(int page, int pageSize) {
        Pageable pageable= PageRequest.of(page,pageSize);
        Page<Category> categories=categoryRepository.findAll(pageable);
        List<CategoryResponseDto>responseDtos=new ArrayList<>();
        for(Category category:categories){
            responseDtos.add(mapper.map(category,CategoryResponseDto.class));
        }
        return responseDtos;
    }

    @Override
    public CategoryResponseDto updateCategory(long id, CategoryRequestDto dto) {
        Category category=categoryRepository.findById(id).orElseThrow(
                ()->new RuntimeException("no such category exists "+ id));
       Category cat=category;
       cat.setName(dto.getName());
       cat.setDescription(dto.getDescription());
       CategoryResponseDto responseDto=mapper.map(cat,CategoryResponseDto.class);
        return responseDto;
    }

    @Override
    public List<ProductResponseDTO> getAllProductByCategoryid(long categoryId) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(
                ()->new RuntimeException("no such category exists "+ categoryId));
        List<ProductResponseDTO>responseDTOS=new ArrayList<>();
        for(Product c:category.getProductList()){
            responseDTOS.add(mapper.map(c,ProductResponseDTO.class));
        }
        return responseDTOS;
    }


}
