package ecommerce_backend.paymentservice.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PlanCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import ecommerce_backend.OrderNotFoundEx;
import ecommerce_backend.cartservice.entity.CartItem;
import ecommerce_backend.categoryservice.exceptions.UserNotFoundException;
import ecommerce_backend.orderservice.OrderRepository;
import ecommerce_backend.orderservice.dto.OrderConfimrationDto;
import ecommerce_backend.orderservice.model.Order;
import ecommerce_backend.orderservice.model.OrderEnum;
import ecommerce_backend.orderservice.model.OrderItems;
import ecommerce_backend.paymentservice.dto.CheckOutResponseDto;
import ecommerce_backend.paymentservice.PaymentStatus;
import ecommerce_backend.paymentservice.dto.PaymentResponseDto;
import ecommerce_backend.productservice.dtos.ProductResponseDTO;
import ecommerce_backend.userservice.entity.User;
import ecommerce_backend.userservice.userdto.response.UserResponseDto;
import ecommerce_backend.userservice.usermapper.UserMapper;
import ecommerce_backend.userservice.userrepository.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentGateway{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
@Autowired
private ModelMapper mapper;
    @Value("${spring.strip.secretKey}")
    private String stripeUniversalLink;
    @Value(("${spring.strip.redirect.url}"))
    private String paymentRedirectUrl;


    @Override
    public CheckOutResponseDto topay(long orderId, String email) throws StripeException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("USER NOT FOUND " + email);
        }

        Stripe.apiKey = stripeUniversalLink;

        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new OrderNotFoundEx("NOT FOUND " + orderId);
        }

        Order order = optionalOrder.get();

        ProductCreateParams productCreateParams = ProductCreateParams.builder()
                .setName("Order #" + order.getId())
                .build();
        Product product = Product.create(productCreateParams);

        PriceCreateParams priceCreateParams = PriceCreateParams.builder()
                .setCurrency("usd")
                .setUnitAmount(
                        order.getTotalPrice()
                                .multiply(new BigDecimal("100"))
                                .longValue()
                )
                .setProduct(product.getId())
                .build();
        Price priceParam = Price.create(priceCreateParams);

        // Explicitly specify payment methods
        PaymentLinkCreateParams linkParams = PaymentLinkCreateParams.builder()
                .addLineItem(
                        PaymentLinkCreateParams.LineItem.builder()
                                .setPrice(priceParam.getId())
                                .setQuantity(1L)
                                .build()
                )
                .addPaymentMethodType(PaymentLinkCreateParams.PaymentMethodType.CARD)  // Add this
                .addPaymentMethodType(PaymentLinkCreateParams.PaymentMethodType.LINK)  // Optional
                .setAfterCompletion(
                        PaymentLinkCreateParams.AfterCompletion.builder()
                                .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                .setRedirect(
                                        PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                .setUrl(paymentRedirectUrl+email+"/"+orderId)
                                                .build()
                                )
                                .build()
                )
                .build();

        PaymentLink paymentLink = PaymentLink.create(linkParams);

        return CheckOutResponseDto.builder()
                .url(paymentLink.getUrl())
                .message("Please click on link to complete payment")
                .status(PaymentStatus.TO_PAY)
                .build();
    }

    public PaymentResponseDto FromOrder(String email, long orderId) {

        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new OrderNotFoundEx("ORDER NOT FOUND WITH ID: " + orderId);
        }

        Order order = orderOpt.get();

        if (!order.getUser().getEmail().equals(email)) {
            throw new OrderNotFoundEx("Order " + orderId + " does not belong to user: " + email);
        }

        User user = order.getUser();

        PaymentResponseDto dto = new PaymentResponseDto();
        dto.setMessage(PaymentStatus.PAID);
        dto.setTotalPrice(order.getTotalPrice());
        dto.setOrderID(orderId);
        dto.setTotalQuantity(order.getTotalQuantity());
        dto.setOrderCreatedAt(order.getCreatedAt());
        dto.setUserAddress(
                user.getName() + "  " +
                        (user.getAddress() != null
                                ? user.getAddress().toString()
                                : "No address provided") +
                        " "+ "PARCEL WILL BE DELIVERED IN 2 DAYS"
        );

        return dto;
    }

}
