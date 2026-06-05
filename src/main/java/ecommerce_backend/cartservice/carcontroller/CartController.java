package ecommerce_backend.cartservice.carcontroller;

import ecommerce_backend.cartservice.dto.*;
import ecommerce_backend.cartservice.entity.Cart;
import ecommerce_backend.cartservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/save")
    public ResponseEntity<List<CartItemResponseDto>> selectedProduct(@RequestBody ProductCartRequestDto dto){
        return ResponseEntity.ok(cartService.savesToDatabase(dto));
    }
    @PostMapping("/add")
    public ResponseEntity<CartResponseDto> addtocart(@RequestBody AddToCartRequest dto){
        return ResponseEntity.ok(cartService.addToCart(dto));
    }
    @GetMapping
    public ResponseEntity<List<CartItemResponseDtoList>> getAllCartItems(){
        return ResponseEntity.ok(cartService.findAllCartItems());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletetocartItme(@PathVariable("id")long id ){
        return ResponseEntity.ok(cartService.deleteCartItems(id));
    }
    @GetMapping("/")
    public ResponseEntity<List<CartResponseDto>> getAllCartI(){
        return ResponseEntity.ok(cartService.getAllCarts());
    }

}
