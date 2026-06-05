package ecommerce_backend.userservice.userdto.request;

import lombok.Data;

@Data
public class AddressDTO {
    private String city;
    private String state;
    private String country;
    private String postalCode;
}