package cz.ackee.imageserver;

/**
 * Exception indicating that url passed to ImageGenerator.UrlGenerator is invalid
 *
 * @author David Bilik [david.bilik@ackee.cz]
 * @since 12/01/2018
 **/
public class InvalidUrlException extends RuntimeException {
    public InvalidUrlException(String var1, Throwable var2) {
        super(var1, var2);
    }
}
