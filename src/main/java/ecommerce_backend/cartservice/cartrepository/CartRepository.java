package ecommerce_backend.cartservice.cartrepository;

import ecommerce_backend.cartservice.entity.Cart;
import ecommerce_backend.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends  JpaRepository<Cart,Long>  {
    Optional<Cart> findByUserEmail(String userEmail);
}
