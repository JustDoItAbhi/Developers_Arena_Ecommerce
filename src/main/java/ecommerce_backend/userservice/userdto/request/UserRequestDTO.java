package ecommerce_backend.userservice.userdto.request;

import ecommerce_backend.userservice.entity.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UserRequestDTO {
    @NotBlank(message = "name must be valid ")
    @Size(min = 4,max = 20,message = "name cannot be less then 4 and cannot be more then 20")
    private String name;
    @Email(message = "please enter valid email")
    private String email;
    @Size(min = 6,max = 20)
    @NotNull(message = "password cannot be null")
    private String password;
    @Size(min = 10,max = 15)
    @NotNull(message = "phone number cannot be null")
    private String contactNumber;
    private AddressDTO address;
    private List<Role> roles;
}