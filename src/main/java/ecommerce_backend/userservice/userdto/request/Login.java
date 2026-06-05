package ecommerce_backend.userservice.userdto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Login {
    @Email(message = "please enter valid email")
private String email;
    @Size(min = 6,max = 20)
    @NotNull(message = "password cannot be null")
private String password;
}
