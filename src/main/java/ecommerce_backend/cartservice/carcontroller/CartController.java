package ecommerce_backend.cartservice.carcontroller;

import ecommerce_backend.cartservice.dto.*;
import ecommerce_backend.cartservice.service.CartService;
import ecommerce_backend.ratelimit.RateLimit;
import ecommerce_backend.utils.TrackPerformance;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@SecurityRequirement(name = "bearerAuth")
public class CartController {
    @Autowired
    private CartService cartService;


    @PostMapping("/add")
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<CartResponseDto> addtocart(@RequestBody AddToOrderRequest dto){
        return ResponseEntity.ok(cartService.addToCart(dto));
    }

    @GetMapping("/")
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<List<CartResponseDto>> getAllCartI(){
        return ResponseEntity.ok(cartService.getAllCarts());
    }


    @GetMapping("/{id}")
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<CartResponseDto> getCartById(@PathVariable("id")long id ){
        return ResponseEntity.ok(cartService.getCartByID(id));
    }
    @DeleteMapping("/{id}")
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<Boolean>deleteCartByID(@PathVariable("id")long id){
        return ResponseEntity.ok(cartService.deleteCart(id));
    }

}
