package ecommerce_backend.productservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ValidationExceptionsHandling {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred ", ex);

        ApiResponse response = ApiResponse.error(
                "Internal server error",
                ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.debug("Id not exsists error: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.ok(ApiResponse.error("Validation failed", errors));
    }

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ApiResponse> handleRuntimeException(ProductNotExsists ex) {
//        log.error("Runtime exception occurred: {}", ex.getMessage(), ex);
//
//        Map<String, String> errors = new HashMap<>();
//        errors.put("error", ex.getMessage());
//
//        if (ex.getCause() != null) {
//            errors.put("cause", ex.getCause().getMessage());
//        }
//
//        HttpStatus status = HttpStatus.NOT_FOUND;
//
//        if (ex instanceof ProductNotExsists) {
//            status = HttpStatus.NOT_FOUND;
//        } else if (ex instanceof ProductNotExsists) {
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//
//        return new ResponseEntity<>(
//                ApiResponse.error("Runtime error occurred", errors),
//                status
//        );
//    }
}
