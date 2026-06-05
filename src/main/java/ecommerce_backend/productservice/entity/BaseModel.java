package ecommerce_backend.productservice.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CurrentTimestamp
    private LocalDateTime createdAt;

}
