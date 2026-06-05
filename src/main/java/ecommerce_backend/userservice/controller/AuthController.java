package ecommerce_backend.userservice.controller;

import ecommerce_backend.userservice.userdto.request.Login;
import ecommerce_backend.userservice.userdto.request.UserRequestDTO;
import ecommerce_backend.userservice.userdto.response.UserResponseDto;
import ecommerce_backend.userservice.userservice.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/register")
public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDTO dto){
        return ResponseEntity.ok(userService.createUser(dto));
    }
    @PostMapping("/login")
    public ResponseEntity<String>login(@Valid @RequestBody Login login){
        return ResponseEntity.ok(userService.login(login.getEmail(), login.getPassword()));
    }
}
