package ecommerce_backend;

public class OrderNotFoundEx extends RuntimeException{
    public OrderNotFoundEx() {
    }

    public OrderNotFoundEx(String message) {
        super(message);
    }

    public OrderNotFoundEx(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotFoundEx(Throwable cause) {
        super(cause);
    }

    public OrderNotFoundEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
