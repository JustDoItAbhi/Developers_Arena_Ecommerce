package ecommerce_backend.exceptions;

import lombok.Data;

import java.time.Instant;

@Data
public class ExceptionError {
    private Instant timeStamp;
    private int status;
    private String message;

    public ExceptionError(Instant timeStamp, int status, String message) {
        this.timeStamp = timeStamp;
        this.status = status;
        this.message = message;
    }
}
