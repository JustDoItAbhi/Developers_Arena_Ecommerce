package ecommerce_backend.userservice.controller;

import ecommerce_backend.userservice.userdto.request.Login;
import ecommerce_backend.userservice.userdto.request.UserRequestDTO;
import ecommerce_backend.userservice.userdto.response.UserResponseDto;
import ecommerce_backend.userservice.userservice.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUser(@RequestParam (defaultValue = "0")int page,
                                                           @RequestParam(defaultValue = "5")int pageSize){
        return ResponseEntity.ok(userService.getallUsers(page,pageSize));
    }

    @GetMapping("/profile")
    public String profile() {
        return "You are authenticated!";
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto>getByID(@PathVariable("id")long id){
        return ResponseEntity.ok(userService.getUserByID(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean>deleteByID(@PathVariable("id")long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDto>createUser(@PathVariable("id")long id, @Valid @RequestBody UserRequestDTO dto){
        return ResponseEntity.ok(userService.updateUser(id,dto));
    }


}
