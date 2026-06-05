package ecommerce_backend.productservice.service;

import ecommerce_backend.categoryservice.exceptions.CategoryNotFoundException;
import ecommerce_backend.productservice.dtos.*;
import ecommerce_backend.productservice.entity.Category;
import ecommerce_backend.productservice.entity.Product;
import ecommerce_backend.productservice.exceptions.ProductNotExsists;
import ecommerce_backend.categoryservice.categoryrepository.CategoryRepository;
import ecommerce_backend.productservice.repository.ProductRepository;
import ecommerce_backend.productservice.service.filters.ProductSpecifications;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public ProductResponseDTO createProduct(ProductDTO dto) {
        Optional<Product> productid=productRepository.findByName(dto.getName());
        if(productid.isPresent()) {
               ProductResponseDTO responseDTO=modelMapper.map(productid.get(),ProductResponseDTO.class);
               return responseDTO;
        }
        Product newProduct=new Product();
        newProduct.setName(dto.getName());
        newProduct.setDescription(dto.getDescription());
        newProduct.setPrice(dto.getPrice());
        newProduct.setStockQuantity(dto.getStockQuantity());

        Optional<Category>category= categoryRepository.findByName(dto.getCategory());
        if(category.isEmpty()){
            throw new CategoryNotFoundException("CATEGORY NOT FOUND "+dto.getCategory());
        }
        newProduct.setCategory(category.get());
        Product savedProduct = productRepository.save(newProduct);
        Category category1=category.get();
        List<Product>productList= Arrays.asList(savedProduct);
        category1.setProductList(productList);

        categoryRepository.saveAndFlush(category.get());
        ProductResponseDTO product = modelMapper.map(savedProduct, ProductResponseDTO.class);
        return product;
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
            throw  new ProductNotExsists("Product with id "+id+" not found");
        }
        ProductResponseDTO responseDTO=modelMapper.map(productid.get(),ProductResponseDTO.class);
        return responseDTO;
    }

    @Override
    public boolean deleteProduct(Long id) {
        Optional<Product> productid=productRepository.findById(id);
        if(productid.isEmpty()) {
            throw  new ProductNotExsists("Product with id "+id+" not found");
        }
        productRepository.deleteById(id);
        return true;
    }

    @Override
    public ProductResponseDTO update(Long id,ProductDTO dto) {
        Optional<Product> productid=productRepository.findById(id);
        if(productid.isEmpty()) {
            throw  new ProductNotExsists("Product with id "+id+" not found");
        }
        Product product=productid.get();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        List<Product>productList= productRepository.findByCategoryName(dto.getCategory());
        if(productList.isEmpty()){
            Category category=new Category();
            category.setName(dto.getCategory());
            product.setCategory(category);
            categoryRepository.save(category);
        }
        return modelMapper.map(product,ProductResponseDTO.class);
    }

    @Override
    public List<ProductResponseDTO> filtersForProduct(ProductSearchParams params, int page, int pageSize) {
        if (params == null) {
           throw new ProductNotExsists("no such product ");
        }

        Pageable pageable = PageRequest.of(page, pageSize);

        Specification<Product> spec = buildSpecifications(params);

        Page<Product> productPage = productRepository.findAll(spec, pageable);

        return productPage.getContent()
                .stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .toList();
    }


    private Specification<Product> buildSpecifications(ProductSearchParams params) {

        Specification<Product> specification = Specification.unrestricted();

        if (params.getName() != null && !params.getName().isBlank()) {
            specification = specification.and(
                    ProductSpecifications.filterProductByName(params.getName())
            );
        }

        if (params.getCategory() != null && !params.getCategory().isBlank()) {
            specification = specification.and(
                    ProductSpecifications.filterCategory(params.getCategory())
            );
        }
        if(params.getMinprice()!=null){
            specification=specification.and(ProductSpecifications.getByPriceLessThan(params.getMinprice()));
        }
        if(params.getMaxPrice()!=null){
            specification=specification.and(ProductSpecifications.getByPriceGreaterThan(params.getMaxPrice()));
        }
        if(params.getMinStock()!=null){
            specification=specification.and(ProductSpecifications.hasMinStock(params.getMinStock()));
        }
        if(params.getMaxStock()!=null){
            specification=specification.and(ProductSpecifications.hasMaxStock(params.getMaxStock()));
        }
        if(!params.getDescription().isBlank() && !params.getDescription().isEmpty()){
            specification=specification.and(ProductSpecifications.getByDiscription(params.getDescription()));
        }

        return specification;
    }




}
