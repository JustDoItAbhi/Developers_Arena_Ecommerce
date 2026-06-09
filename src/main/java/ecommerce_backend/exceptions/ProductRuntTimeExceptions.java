package ecommerce_backend.exceptions;

import ecommerce_backend.OrderNotFoundEx;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ProductRuntTimeExceptions {

    @ExceptionHandler(ProductNotExsists.class)
    public ResponseEntity<ExceptionError> productNotExists(ProductNotExsists ex){

        ExceptionError productError=new ExceptionError(
                Instant.now(),
                404,
                ex.getMessage()
        );
        return new ResponseEntity<>(productError, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ProductOutOfStock.class)
    public ResponseEntity<ExceptionError> productoutOfStock(ProductOutOfStock ex){

        ExceptionError productError=new ExceptionError(
                Instant.now(),
                422,//Unprocessable Entity
                ex.getMessage()
        );
        return new ResponseEntity<>(productError, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ExceptionError> paymentError(PaymentException ex){

        ExceptionError productError=new ExceptionError(
                Instant.now(),
                500,//Unprocessable Entity
                ex.getMessage()
        );
        return new ResponseEntity<>(productError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ExceptionError> cartError(CartNotFoundException ex){

        ExceptionError productError=new ExceptionError(
                Instant.now(),
                404,//Unprocessable Entity
                ex.getMessage()
        );
        return new ResponseEntity<>(productError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(OrderNotFoundEx.class)
    public ResponseEntity<ExceptionError> orderException(OrderNotFoundEx ex){

        ExceptionError productError=new ExceptionError(
                Instant.now(),
                404,//Unprocessable Entity
                ex.getMessage()
        );
        return new ResponseEntity<>(productError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

