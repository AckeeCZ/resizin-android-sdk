import org.junit.Test;

import cz.ackee.imageserver.ImageServer;
import cz.ackee.imageserver.InvalidUrlException;

/**
 * TODO add class description
 *
 * @author David Bilik [david.bilik@ackee.cz]
 * @since 12/01/2018
 **/
public class InvalidUrlTests {

    @Test(expected = InvalidUrlException.class)
    public void shouldThrowWhenInvalidScheme() {
        new ImageServer("test").withUrl("blabla");
    }

    @Test(expected = InvalidUrlException.class)
    public void shouldThrowWhenMissingImageId() {
        new ImageServer("test").withUrl("https://seznam.cz");
    }

    @Test(expected = InvalidUrlException.class)
    public void shouldThrowWhenInvalidWidth() {
        new ImageServer("test").withUrl("https://img.server.ack.ee/test/image/w_bla/123");
    }

    @Test(expected = InvalidUrlException.class)
    public void shouldThrowWhenInvaliHeight() {
        new ImageServer("test").withUrl("https://img.server.ack.ee/test/image/w_bla/123");
    }

    @Test(expected = InvalidUrlException.class)
    public void shouldThrowWhenInvalidCrop() {
        new ImageServer("test").withUrl("https://img.server.ack.ee/test/image/c_bla/123");
    }

    @Test(expected = InvalidUrlException.class)
    public void shouldThrowWhenInvalidGravity() {
        new ImageServer("test").withUrl("https://img.server.ack.ee/test/image/g_bla/123");
    }
}
