package ecommerce_backend.categoryservice.categorcontroller;

import ecommerce_backend.categoryservice.categorydto.CategoryRequestDto;
import ecommerce_backend.categoryservice.categorydto.CategoryResponseDto;
import ecommerce_backend.categoryservice.categoryservice.CategoryService;
import ecommerce_backend.productservice.dtos.ProductResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public ResponseEntity<CategoryResponseDto>createCategotry(@Valid @RequestBody CategoryRequestDto dto){
        CategoryResponseDto responseDto=categoryService.createCategory(dto);
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> findAll(@RequestParam (defaultValue = "0")  int page,
                                                            @RequestParam(defaultValue = "5")  int pageSize){
        return ResponseEntity.ok(categoryService.findAllCategories(page,pageSize));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean>deleteCategory(@PathVariable("id")long id){
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
    @GetMapping("/catProduct/{id}")
    public ResponseEntity<List<ProductResponseDTO>> findAll(@PathVariable("id")long id){
        return ResponseEntity.ok(categoryService.getAllProductByCategoryid(id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto>getCategoryById(@PathVariable("id")long id){
        return ResponseEntity.ok(categoryService.findById(id));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable("id")long id, @Valid @RequestBody CategoryRequestDto dto){
        return ResponseEntity.ok(categoryService.updateCategory(id,dto));
    }
}
/*

• POST /api/auth/register - User registration
• POST /api/auth/login - User login
• GET /api/users/profile - User profile
• POST /api/cart/add - Add to cart
• POST /api/orders - Create order
• GET /api/orders/{id} - Get order details
 */