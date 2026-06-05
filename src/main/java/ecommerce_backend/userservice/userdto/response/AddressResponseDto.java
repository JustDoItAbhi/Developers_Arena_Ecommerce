package ecommerce_backend.userservice.userdto.response;

import lombok.Data;

@Data
public class AddressResponseDto {
    private String city;
    private String state;
    private String country;
    private String postalCode;
}
