package ecommerce_backend.userservice.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Address {
    private String city;
    private String state;
    private String country;
    private String postalCode;


}
