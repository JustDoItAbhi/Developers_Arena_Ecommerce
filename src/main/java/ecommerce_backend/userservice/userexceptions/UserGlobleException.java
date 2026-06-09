package ecommerce_backend.userservice.userexceptions;

import ecommerce_backend.categoryservice.exceptions.UserNotFoundException;
import ecommerce_backend.exceptions.ExceptionError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class UserGlobleException {
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ExceptionError> roleNotExists(RoleNotFoundException ex){

        ExceptionError productError=new ExceptionError(
                Instant.now(),
                404,
                ex.getMessage()
        );
        return new ResponseEntity<>(productError, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionError> CategoryNotExists(UserNotFoundException ex){

        ExceptionError productError=new ExceptionError(
                Instant.now(),
                404,
                ex.getMessage()
        );
        return new ResponseEntity<>(productError, HttpStatus.NOT_FOUND);
    }
}
