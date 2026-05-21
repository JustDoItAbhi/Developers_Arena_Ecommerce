package ecommerce_backend.service;

import ecommerce_backend.dtos.ProductDTO;
import ecommerce_backend.dtos.ProductResponseDTO;
import ecommerce_backend.entity.Category;
import ecommerce_backend.entity.Product;
import ecommerce_backend.repository.CategoryRepository;
import ecommerce_backend.repository.productRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private productRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public ProductResponseDTO createProduct(ProductDTO dto) {
        Optional<Product> productid=productRepository.findByName(dto.getName());
        if(productid.isPresent()) {
               throw  new RuntimeException("PRODUCT ALREADY EXISTS "+dto.getName());
        }
        Product newProduct=new Product();
        newProduct.setName(dto.getName());
        newProduct.setDescription(dto.getDescription());
        newProduct.setPrice(dto.getPrice());
        newProduct.setStockQuantity(dto.getStockQuantity());

        List<Product>productList= productRepository.findByCategoryName(dto.getCategory().getName());
        if(productList.isEmpty()){
            Category category=new Category();
            category.setName(dto.getCategory().getName());
            category.setDescription(dto.getCategory().getDescription());
            newProduct.setCategory(category);
            categoryRepository.save(category);
        }
//        Product product = modelMapper.map(productDTO, Product.class);
        Product savedProduct = productRepository.save(newProduct);
        return modelMapper.map(savedProduct,ProductResponseDTO.class);

    }

    @Override
    public Page<ProductResponseDTO> getAllProducts(int page, int size) {
        Pageable pageable= PageRequest.of(page,size);
       Page<Product>productList= productRepository.findAll(pageable);
       Page<ProductResponseDTO>responseDTOS=productList.map(product->
               modelMapper.map(product,ProductResponseDTO.class));
       return responseDTOS;
    }



    @Override
    public ProductResponseDTO getProductById(Long id) {
        Optional<Product> productid=productRepository.findById(id);
        if(productid.isEmpty()) {
            throw  new RuntimeException("PRODUCT NOT EXISTS "+id);
        }
        ProductResponseDTO responseDTO=modelMapper.map(productid.get(),ProductResponseDTO.class);
        return responseDTO;
    }

    @Override
    public boolean deleteProduct(Long id) {
        Optional<Product> productid=productRepository.findById(id);
        if(productid.isEmpty()) {
            throw  new RuntimeException("PRODUCT NOT EXISTS "+id);
        }
        productRepository.deleteById(id);
        return true;
    }

    @Override
    public ProductResponseDTO update(Long id,ProductDTO dto) {
        Optional<Product> productid=productRepository.findById(id);
        if(productid.isEmpty()) {
            throw  new RuntimeException("PRODUCT NOT EXISTS "+id);
        }
        Product product=productid.get();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        List<Product>productList= productRepository.findByCategoryName(dto.getCategory().getName());
        if(productList.isEmpty()){
            Category category=new Category();
            category.setName(dto.getCategory().getName());
            category.setDescription(dto.getCategory().getDescription());
            product.setCategory(category);
            categoryRepository.save(category);
        }
        return modelMapper.map(product,ProductResponseDTO.class);
    }
}
