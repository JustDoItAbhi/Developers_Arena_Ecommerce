package ecommerce_backend.userservice.userdto.response;

import ecommerce_backend.userservice.entity.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {
    private long id;
    private String name;
    private String email;
//    private String password;
    private String contactNumber;
    private AddressResponseDto address;
    private List<Role> roles;
}
