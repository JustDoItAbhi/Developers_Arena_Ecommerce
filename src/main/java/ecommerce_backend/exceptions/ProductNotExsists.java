package ecommerce_backend.exceptions;

public class ProductNotExsists extends RuntimeException{
    public ProductNotExsists() {
    }

    public ProductNotExsists(String message) {
        super(message);
    }

    public ProductNotExsists(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotExsists(Throwable cause) {
        super(cause);
    }

    public ProductNotExsists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
