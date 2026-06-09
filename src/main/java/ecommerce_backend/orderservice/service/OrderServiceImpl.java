package ecommerce_backend.orderservice.service;

import ecommerce_backend.OrderNotFoundEx;
import ecommerce_backend.cartservice.cartrepository.CartItemRepository;
import ecommerce_backend.cartservice.cartrepository.CartRepository;
import ecommerce_backend.cartservice.entity.Cart;
import ecommerce_backend.cartservice.entity.CartItem;
import ecommerce_backend.categoryservice.exceptions.UserNotFoundException;
import ecommerce_backend.exceptions.CartNotFoundException;
import ecommerce_backend.orderservice.OrderRepository;
import ecommerce_backend.orderservice.dto.OrderConfimrationDto;
import ecommerce_backend.orderservice.dto.OrderRequestDto;
import ecommerce_backend.orderservice.dto.OrderResponseDto;
import ecommerce_backend.orderservice.model.Order;
import ecommerce_backend.orderservice.model.OrderEnum;
import ecommerce_backend.orderservice.model.OrderItems;
import ecommerce_backend.productservice.entity.Product;
import ecommerce_backend.exceptions.ProductNotExsists;
import ecommerce_backend.productservice.repository.ProductRepository;
import ecommerce_backend.userservice.entity.User;
import ecommerce_backend.userservice.userrepository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OrderServiceImpl implements OrderService{
    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CartItemRepository cartItemRepository;

    ConcurrentHashMap <String,OrderResponseDto>orderResponseDtoHashMap=new ConcurrentHashMap<>();
    ConcurrentHashMap <String,OrderConfimrationDto>orderConfirmation=new ConcurrentHashMap<>();

    public OrderServiceImpl(UserRepository userRepository, OrderRepository orderRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }


    public OrderResponseDto placeOrder(OrderRequestDto dto) {
        if(orderResponseDtoHashMap.containsKey(dto.getEmail())){
            return orderResponseDtoHashMap.get(dto.getEmail());
        }
        Integer totalQuantity=0;
        Optional<Cart>exsistingCart=  cartRepository.findById(dto.getCartId());
        if(exsistingCart.isEmpty()){
            throw new CartNotFoundException("CART ID IS INVALID "+dto.getCartId());
        }
        BigDecimal total=BigDecimal.ZERO;
        BigDecimal totalPrice=BigDecimal.ZERO;
        Optional<User> userOptional=userRepository.findByEmail(dto.getEmail());
//        if(userOptional.isEmpty()){
//            throw new UserNotFoundException("USER NOT FOUND PLEASE LOGIN "+dto.getEmail());
//        }
        Order order=new Order();
        List<OrderItems>orderItemsList=new ArrayList<>();
        for(CartItem items:exsistingCart.get().getCartItems()){
            Optional<CartItem>cartItemOptional=cartItemRepository.findById(items.getId());
            if(cartItemOptional.isEmpty()){
                throw new CartNotFoundException("CART ITMS ID IS INVALID "+items.getId());
            }
            OrderItems item=new OrderItems();
            Optional<Product>productOptional=productRepository.findById(items.getProductId());
            if(productOptional.isEmpty()){
                throw new ProductNotExsists("PRODUCT ID NOT FOUND "+ items.getProductId());
            }
            if(productOptional.get().getPrice().equals(cartItemOptional.get().getPrice())){
                total=exsistingCart.get().getTotalPrice();
            }else {
                totalPrice=productOptional.get().getPrice().multiply(BigDecimal.valueOf(items.getQuantity()));
                total=total.add(totalPrice);
            }
            item.setProduct(productOptional.get());
            item.setQuantity(items.getQuantity());
            orderItemsList.add(item);
        }
        totalQuantity=exsistingCart.get().getTotalQuantity();
        order.setUser(userOptional.get());
        order.setCart(exsistingCart.get());
        order.setOrderItems(orderItemsList);
        order.setOrderEnum(OrderEnum.ORDER_PENDING);
        order.setTotalPrice(total);
        order.setTotalQuantity(totalQuantity);
        for(OrderItems items:orderItemsList){
            items.setOrder(order);
        }
        orderRepository.save(order);
        orderResponseDtoHashMap.put(order.getUser().getEmail(),fromOrderTiems(order));
        return fromOrderTiems(order);
    }
    private OrderResponseDto fromOrderTiems(Order  order){
    OrderResponseDto dto=new OrderResponseDto();
    dto.setOrderID(order.getId());
    List<Long>productIds=new ArrayList<>();
    dto.setUseEmail(order.getCart().getUserEmail());
    dto.setOrderCreatedAt(order.getCreatedAt());
    List<Long>itemsListId=new ArrayList<>();
    for(OrderItems orderItems:order.getOrderItems()){
        itemsListId.add(orderItems.getId());
        productIds.add(orderItems.getProduct().getId());
    }
    dto.setOrderItemsIds(itemsListId);
    dto.setProductIds(productIds);
    dto.setTotalQuantity(order.getTotalQuantity());
    dto.setTotalPrice(order.getTotalPrice());
    dto.setStatus(order.getOrderEnum());
    return dto;
    }


    @Override
    public List<OrderResponseDto> getAllOrders() {
        if (!orderResponseDtoHashMap.isEmpty()) {
            return new ArrayList<>(orderResponseDtoHashMap.values());
        }
        List<Order>orderList=orderRepository.findAll();
        List<OrderResponseDto>responseDtos=new ArrayList<>();
        for(Order order:orderList){
           responseDtos.add(fromOrderTiems(order));
        }

        return responseDtos;
    }

    @Override
    public boolean deleteOrder(long id) {
       Optional<Order>order=orderRepository.findById(id);
       if(order.isEmpty()){
           throw new OrderNotFoundEx("ORDER ID NOT EXISTS "+id);
       }
       orderRepository.deleteById(id);
        return true;
    }

    @Override
    public OrderConfimrationDto ConfirmOrder(String email) {
        if(orderConfirmation.containsKey(email)){
            return orderConfirmation.get(email);
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("USER NOT FOUND PLEASE SIGN UP"));

        List<Order> orderList = orderRepository.findByUserId(user.getId());

        if (orderList.isEmpty()) {
            throw new OrderNotFoundEx("PLEASE PLACE AN ORDER FIRST " + email);
        }
        OrderConfimrationDto dto=null;
        for(Order order:orderList){
          dto=fromConfirmOrder(order);
        }
        orderConfirmation.put(email,dto);
        return dto;
    }
    private OrderConfimrationDto fromConfirmOrder(Order order){
        OrderConfimrationDto confimrationDto=new OrderConfimrationDto();
        confimrationDto.setStatus(OrderEnum.CONFIRM_ORDER);
        confimrationDto.setUseEmail(order.getUser().getEmail());
        confimrationDto.setOrderCreatedAt(order.getCreatedAt());
        confimrationDto.setOrderID(order.getId());
        confimrationDto.setTotalQuantity(order.getTotalQuantity());
        confimrationDto.setTotalPrice(order.getTotalPrice());
    return confimrationDto;
    }
}
