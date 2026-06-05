package ecommerce_backend.orderservice;

import ecommerce_backend.cartservice.cartrepository.CartRepository;
import ecommerce_backend.cartservice.dto.CartResponseDto;
import ecommerce_backend.cartservice.entity.Cart;
import ecommerce_backend.categoryservice.exceptions.UserNotFoundException;
import ecommerce_backend.userservice.entity.User;
import ecommerce_backend.userservice.userdto.response.UserResponseDto;
import ecommerce_backend.userservice.userrepository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper mapper;

    public OrderServiceImpl(UserRepository userRepository, OrderRepository orderRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public OrderResponseDto confirmOrder(long cartId, String email) {
        Optional<User> userOptional=userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("USER NOT FOUND PLEASE LOGIN "+email);
        }
        Optional<Cart>cartOptional=cartRepository.findById(cartId);
        if(cartOptional.isEmpty()){
            throw new RuntimeException("EMPTY CART PLEASE SELECT PRODUCT "+ cartId);
        }
        Order order=new Order();
        order.setCart(cartOptional.get());
        order.setUser(userOptional.get());
        orderRepository.save(order);
        UserResponseDto userResponseDto=mapper.map(userOptional.get(),UserResponseDto.class);
//        CartResponseDto cartResponseDto=mapper.map(cartOptional.get(),CartResponseDto.class);
        CartResponseDto responseDto = new CartResponseDto();
        responseDto.setCartID(cartOptional.get().getId());
        responseDto.setTotalPrice(cartOptional.get().getTotalPrice());
        responseDto.setTotalNumberOfItemsSelected(cartOptional.get().getTotalQuantity());

        OrderResponseDto dto=new OrderResponseDto();
        dto.setOrderId(order.getId());
        dto.setUserResponseDto(userResponseDto);
        dto.setCartResponseDto(responseDto);
        return dto;
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        List<Order>orderList=orderRepository.findAll();
        List<OrderResponseDto>responseDtos=new ArrayList<>();
        for(Order order:orderList){
            OrderResponseDto dto=new OrderResponseDto();
            dto.setOrderId(order.getId());
            dto.setUserResponseDto(mapper.map(order.getUser(),UserResponseDto.class));
            dto.setCartResponseDto(mapper.map(order.getCart(),CartResponseDto.class));
            responseDtos.add(dto);
        }
        return responseDtos;
    }
}
