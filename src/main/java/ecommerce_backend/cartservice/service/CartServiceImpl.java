package ecommerce_backend.cartservice.service;

import ecommerce_backend.cartservice.cartrepository.CartRepository;
import ecommerce_backend.cartservice.cartrepository.CartItemRepository;
import ecommerce_backend.cartservice.dto.*;
import ecommerce_backend.cartservice.entity.Cart;
import ecommerce_backend.cartservice.entity.CartItem;

import ecommerce_backend.cartservice.mapper.CartMapper;
import ecommerce_backend.categoryservice.exceptions.UserNotFoundException;
import ecommerce_backend.productservice.entity.Product;
import ecommerce_backend.productservice.repository.ProductRepository;
import ecommerce_backend.userservice.entity.User;
import ecommerce_backend.userservice.userrepository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class CartServiceImpl implements CartService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;


@Autowired
private UserRepository userRepository;


@Override
public CartResponseDto addToCart(AddToOrderRequest dto) {
        Optional<User>userOptional=userRepository.findByEmail(dto.getUserEmail());
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("PLEASE SIGN UP :: "+ dto.getUserEmail());
        }

    Cart cart = new Cart();
    BigDecimal totalPrice = BigDecimal.ZERO;
    BigDecimal total=BigDecimal.ZERO;
    int totalQuantity = 0;
    List<CartItem> cartItemList = new ArrayList<>();

    for (CartRequestDtoList item : dto.getCartRequestDtoLists()) {
        Optional<CartItem>cartItem=cartItemRepository.findById(item.getCartItemId());
        if(cartItem.isEmpty()){
            throw new RuntimeException("CART ITEM ID IS NULL "+item.getCartItemId());
        }
        Optional<Product>product=productRepository.findById(item.getProductId());
        if(product.isEmpty()){
            throw new RuntimeException("PRODUCT ID IS NULL "+item.getCartItemId());
        }
        CartItem cartItem1=cartItem.get();
        System.out.println("ITEMS Q "+item.getQuantity());
        System.out.println("ITEMS PID "+item.getProductId());
        System.out.println("ITEMS CARTiD "+item.getCartItemId());
        totalPrice=cartItem1.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
         totalQuantity+=item.getQuantity();
        total=total.add(totalPrice);
         cartItem1.setQuantity(item.getQuantity());
        cartItem1.setTotal(totalPrice);
        System.out.println("PRODUCT PRICE ::: "+ product.get().getPrice());
        System.out.println("CART ITME QUANTITY ::: "+ item.getQuantity());
        cartItemRepository.save(cartItem1);
        cartItemList.add(cartItem1);
    }

    cart.setUserEmail(userOptional.get().getEmail());
    cart.setCartItems(cartItemList);
    cart.setTotalPrice(total);
    cart.setTotalQuantity(totalQuantity);
    System.out.println("TOTAL ::: "+ total);
    for (CartItem cartItem1 : cartItemList) {
        cartItem1.setCart(cart);
    }
    cart.setUserEmail(userOptional.get().getEmail());
    Cart savedCart = cartRepository.save(cart);

    System.out.println("CART ID :::: "+savedCart.getId());
    return CartMapper.fromCartEntity(savedCart);
}



    @Override
    public List<CartResponseDto> getAllCarts() {
        List<Cart>cartList=cartRepository.findAll();
        List<CartResponseDto>responseDtos=new ArrayList<>();
        for(Cart cart:cartList){
            for(CartItem item:cart.getCartItems()) {
                System.out.println("CART ITEM DETAILS :::: " + item.getProductName());
            }
            responseDtos.add(CartMapper.fromCartEntity(cart));
        }

        return responseDtos;
    }

    @Override
    public CartResponseDto getCartByID(long id) {
        Cart cart=cartRepository.findById(id).orElseThrow(
                ()->new RuntimeException("CART NOT FOUND "+ id));
        CartResponseDto dto=CartMapper.fromCartEntity(cart);

        return dto;
    }

    @Override
    public boolean deleteCart(long id) {
        if (!cartRepository.existsById(id)) {
            throw new IllegalArgumentException("Cart not found with id: " + id);
        }

        cartRepository.deleteById(id);
        return true;

    }
}
