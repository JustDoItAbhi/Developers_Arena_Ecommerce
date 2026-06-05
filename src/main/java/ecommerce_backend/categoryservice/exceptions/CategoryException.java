package ecommerce_backend.categoryservice.exceptions;

import ecommerce_backend.productservice.exceptions.ExceptionError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class CategoryException {
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionError> CategoryNotExists(CategoryNotFoundException ex){

        ExceptionError productError=new ExceptionError(
                Instant.now(),
                404,
                ex.getMessage()
        );
        return new ResponseEntity<>(productError, HttpStatus.NOT_FOUND);
    }
}
