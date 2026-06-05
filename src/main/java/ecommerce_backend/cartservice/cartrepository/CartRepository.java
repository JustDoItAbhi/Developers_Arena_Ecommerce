package ecommerce_backend.cartservice.cartrepository;

import ecommerce_backend.cartservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends  JpaRepository<Cart,Long>  {



}
