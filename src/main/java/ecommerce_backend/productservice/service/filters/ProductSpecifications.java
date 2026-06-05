package ecommerce_backend.productservice.service.filters;

import ecommerce_backend.productservice.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.*;
public class ProductSpecifications {
    public static Specification<Product> filterCategory(String category) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.join("category").get("name")),
                        "%" + category.toLowerCase() + "%"
                );
    }

    public static Specification<Product> filterProductByName(String productName) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("name")),
                        "%" + productName.toLowerCase() + "%"
                );
    }
    public static Specification<Product> getByPriceLessThan(BigDecimal price){
        return (product,query,builder)->
            builder.lessThanOrEqualTo(product.get("price"),price);
    }
    public static Specification<Product> getByPriceGreaterThan(BigDecimal price){
        return (product,query,builder)->
                builder.greaterThanOrEqualTo(product.get("price"),price);
    }
    public static Specification<Product> hasMinStock(Integer minstockQuantity){
        return (product,query,builder)->
                builder.greaterThanOrEqualTo(
                        product.get("stockQuantity"),
                        minstockQuantity);
    }
    public static Specification<Product> hasMaxStock(Integer maxstockQuantity){
        return (product,query,builder)->
                builder.lessThanOrEqualTo(
                        product.get("stockQuantity"),
                        maxstockQuantity);
    }
    public static Specification<Product>getByDiscription(String description){
        return (product,query, builder)->
                builder.like(builder.lower(product.get("description")),"%"+ description.toLowerCase()+"%");
    }

}
