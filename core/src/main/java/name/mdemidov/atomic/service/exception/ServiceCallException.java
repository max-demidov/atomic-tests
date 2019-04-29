package name.mdemidov.atomic.service.exception;

public class ServiceCallException extends RuntimeException {

    public ServiceCallException(String message, Throwable cause) {
        super(message, cause);
    }
}
