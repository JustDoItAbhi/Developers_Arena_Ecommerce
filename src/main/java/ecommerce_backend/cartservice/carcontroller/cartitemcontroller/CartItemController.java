package ecommerce_backend.cartservice.carcontroller.cartitemcontroller;

import ecommerce_backend.cartservice.dto.CartItemResponseDto;
import ecommerce_backend.cartservice.dto.CartItemResponseDtoList;
import ecommerce_backend.cartservice.dto.ProductCartRequestDto;
import ecommerce_backend.cartservice.service.CartItemsService;
import ecommerce_backend.ratelimit.RateLimit;
import ecommerce_backend.utils.TrackPerformance;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/cartitems")
@SecurityRequirement(name = "bearerAuth")
public class CartItemController {
    @Autowired
    private CartItemsService cartItemsService;
    @GetMapping("/save")
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<List<CartItemResponseDto>> selectedProduct(@RequestBody ProductCartRequestDto dto){
        return ResponseEntity.ok(cartItemsService.savesToDatabase(dto));
    }
    @GetMapping
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<List<CartItemResponseDtoList>> getAllCartItems(){
        return ResponseEntity.ok(cartItemsService.findAllCartItems());
    }
    @DeleteMapping("/{id}")
    @TrackPerformance
    @RateLimit(value = 50,duration = 60000)
    public ResponseEntity<String> deletetocartItme(@PathVariable("id")long id ){
        return ResponseEntity.ok(cartItemsService.deleteCartItems(id));
    }
}
