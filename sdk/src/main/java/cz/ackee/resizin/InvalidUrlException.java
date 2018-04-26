package cz.ackee.resizin;

/**
 * Exception indicating that url passed to {@link Resizin.UrlGenerator} is invalid
 */
public class InvalidUrlException extends RuntimeException {
    public InvalidUrlException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
