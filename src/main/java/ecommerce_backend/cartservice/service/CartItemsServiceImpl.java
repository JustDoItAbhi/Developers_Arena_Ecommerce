package ecommerce_backend.cartservice.service;

import ecommerce_backend.cartservice.cartrepository.CartItemRepository;
import ecommerce_backend.cartservice.cartrepository.CartRepository;
import ecommerce_backend.cartservice.dto.CartItemResponseDto;
import ecommerce_backend.cartservice.dto.CartItemResponseDtoList;
import ecommerce_backend.cartservice.dto.ProductCartRequestDto;
import ecommerce_backend.cartservice.entity.CartItem;
import ecommerce_backend.cartservice.mapper.CartItemMapper;
import ecommerce_backend.cartservice.mapper.CartMapper;
import ecommerce_backend.exceptions.CartNotFoundException;
import ecommerce_backend.productservice.entity.Product;
import ecommerce_backend.productservice.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemsServiceImpl implements CartItemsService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

        @Override
        public List<CartItemResponseDto> savesToDatabase(ProductCartRequestDto dto) {
            List<Product> products = productRepository.findAllById(dto.getProductId());
            List<CartItemResponseDto> responseDto = new ArrayList<>();
            List<CartItem> cartItemList = new ArrayList<>();
            for(Product product : products) {
                Optional<CartItem> existingCartItem = cartItemRepository.findByProductId(product.getId());
                if(existingCartItem.isPresent()) {
                    throw new CartNotFoundException("Cart already exists for product: " + product.getId());
                }

                CartItem newCartItem = new CartItem();
                newCartItem.setProductId(product.getId());
                newCartItem.setProductName(product.getName());
                newCartItem.setPrice(product.getPrice());
                cartItemList.add(newCartItem);
                cartItemRepository.save(newCartItem);
                CartItemResponseDto cartItemResponseDto=CartItemMapper.fromEntity(newCartItem);
                responseDto.add(cartItemResponseDto);
            }return responseDto;

    }

    @Override
    public List<CartItemResponseDtoList> findAllCartItems() {

        List<CartItem>items=cartItemRepository.findAll();
        List<CartItemResponseDtoList>list=new ArrayList<>();
        for(CartItem item:items){
            CartItemResponseDtoList responseDtoList=new CartItemResponseDtoList();
            responseDtoList.setId(item.getId());
            responseDtoList.setProductName(item.getProductName());
            responseDtoList.setQuantity(item.getQuantity());
            responseDtoList.setPrice(item.getPrice());
            responseDtoList.setProductId(item.getProductId());
            list.add(responseDtoList);
        }
        return list;
    }

    @Override
    public String deleteCartItems(long id) {
        CartItem item=cartItemRepository.findById(id).orElseThrow(
                ()->new CartNotFoundException("cart not found "+ id));
        cartItemRepository.deleteById(id);

        return "deleted"+id;
    }

}
