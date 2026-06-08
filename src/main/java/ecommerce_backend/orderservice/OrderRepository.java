package ecommerce_backend.orderservice;

import ecommerce_backend.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order>findByCartId(long id);
    Optional<Order>findByUserEmail(String email);
    List<Order>findByUserId(long id);

}
