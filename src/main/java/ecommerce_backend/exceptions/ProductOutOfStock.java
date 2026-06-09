package ecommerce_backend.exceptions;

public class ProductOutOfStock extends RuntimeException{
    public ProductOutOfStock() {
    }

    public ProductOutOfStock(String message) {
        super(message);
    }

    public ProductOutOfStock(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductOutOfStock(Throwable cause) {
        super(cause);
    }

    public ProductOutOfStock(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
