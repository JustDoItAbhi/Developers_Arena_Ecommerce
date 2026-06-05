package ecommerce_backend.cartservice.service;

import ecommerce_backend.cartservice.cartrepository.CartRepository;
import ecommerce_backend.cartservice.cartrepository.CartItemRepository;
import ecommerce_backend.cartservice.dto.*;
import ecommerce_backend.cartservice.entity.Cart;
import ecommerce_backend.cartservice.entity.CartItem;

import ecommerce_backend.cartservice.mapper.CartMapper;
import ecommerce_backend.productservice.entity.Product;
import ecommerce_backend.productservice.repository.ProductRepository;
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

    HashMap<Long,Product>productHashMap=new HashMap<>();

    @Override
    public List<CartItemResponseDto> savesToDatabase(ProductCartRequestDto dto) {
        List<Product> products = productRepository.findAllById(dto.getProductId());
        List<CartItem> cartItemList = new ArrayList<>();
        for(Product product : products) {
            Optional<CartItem> existingCartItem = cartItemRepository.findByProductId(product.getId());
            if(existingCartItem.isPresent()) {
                throw new RuntimeException("Cart already exists for product: " + product.getId());
            }
            CartItem newCartItem = new CartItem();
            newCartItem.setProductId(product.getId());
            newCartItem.setProductName(product.getName());
            newCartItem.setPrice(product.getPrice());
            newCartItem.setQuantity(product.getStockQuantity());
            cartItemList.add(newCartItem);
        }
        List<CartItem> savedItems = cartItemRepository.saveAll(cartItemList);
        List<CartItemResponseDto> responseDto = new ArrayList<>();
        for(CartItem item : savedItems) {
            responseDto.add(CartMapper.fromEntity(item));
        }

        return responseDto;
    }
@Override
public CartResponseDto addToCart(AddToCartRequest dto) {
    Cart cart = new Cart();
    BigDecimal totalPrice = BigDecimal.ZERO;
    int totalQuantity = 0;
    List<CartItem> cartItemList = new ArrayList<>();

    Map<Long, Integer> quantityMap = new HashMap<>();
    for (CartRequestDtoList item : dto.getCartRequestDtoLists()) {
        Optional<CartItem>cartItem=cartItemRepository.findById(item.getCartItemId());
        if(cartItem.isEmpty()){
            throw new RuntimeException("CART ITEM ID IS NULL "+item.getCartItemId());
        }
        Optional<Product>product=productRepository.findById(item.getProductId());
        if(product.isEmpty()){
            throw new RuntimeException("PRODUCT ID IS NULL "+item.getCartItemId());
        }

        cartItemList.add(cartItem.get());

        quantityMap.put(item.getCartItemId(), item.getQuantity());

        totalPrice=(product.get().getPrice().multiply(BigDecimal.valueOf(quantityMap.get(item.getCartItemId()))));
        totalQuantity+=quantityMap.get(item.getCartItemId());

        System.out.println("QUANTITY ::::::"+quantityMap.get(item.getCartItemId())+" :::::   "+ totalPrice);
    }

    cart.setCartItems(cartItemList);
    cart.setTotalPrice(totalPrice);
    cart.setTotalQuantity(totalQuantity);

    Cart savedCart = cartRepository.save(cart);

    System.out.println("CART ID :::: "+savedCart.getId());

    CartResponseDto responseDto = new CartResponseDto();
    responseDto.setCartID(savedCart.getId());
    responseDto.setTotalPrice(savedCart.getTotalPrice());
    responseDto.setTotalNumberOfItemsSelected(savedCart.getTotalQuantity());


    List<CartItemResponseDtoList> list = new ArrayList<>();
    for (CartItem item : savedCart.getCartItems()) {
        CartItemResponseDtoList responseDtoList = new CartItemResponseDtoList();
        responseDtoList.setNumberOfItemsSelected(quantityMap.get(item.getId()));
        responseDtoList.setId(item.getId());
        responseDtoList.setPrice(item.getPrice());
        responseDtoList.setProductName(item.getProductName());
        responseDtoList.setProductId(item.getProductId());
        list.add(responseDtoList);
    }
    responseDto.setResponseDtoLists(list);

    return responseDto;
}


    @Override
    public List<CartItemResponseDtoList> findAllCartItems() {

        List<CartItem>items=cartItemRepository.findAll();
        List<CartItemResponseDtoList>list=new ArrayList<>();
        for(CartItem item:items){
            CartItemResponseDtoList responseDtoList=new CartItemResponseDtoList();
            responseDtoList.setId(item.getId());
            responseDtoList.setProductName(item.getProductName());
            responseDtoList.setNumberOfItemsSelected(item.getQuantity());
            responseDtoList.setPrice(item.getPrice());
            responseDtoList.setProductId(item.getProductId());
            list.add(responseDtoList);
        }
        return list;
    }

    @Override
    public String deleteCartItems(long id) {
        CartItem item=cartItemRepository.findById(id).orElseThrow(
                ()->new RuntimeException("cart not found "+ id));
      cartItemRepository.deleteById(id);

        return "deleted"+id;
    }

    @Override
    public List<CartResponseDto> getAllCarts() {
        List<Cart>cartList=cartRepository.findAll();
        List<CartResponseDto>responseDtos=new ArrayList<>();
        for(Cart cart:cartList){
            responseDtos.add(mapper.map(cart,CartResponseDto.class));
        }
        return responseDtos;
    }
}
