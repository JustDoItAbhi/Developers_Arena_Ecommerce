package ecommerce_backend.userservice.userrepository;

import ecommerce_backend.userservice.entity.Role;
import ecommerce_backend.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>findByEmail(String email);
    List<User> findByRoles(Role role);
}
