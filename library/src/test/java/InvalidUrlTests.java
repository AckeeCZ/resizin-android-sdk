import org.junit.Test;

import cz.ackee.resizin.Resizin;
import cz.ackee.resizin.InvalidUrlException;

/**
 * Tests for invalid urls
 */
public class InvalidUrlTests {

    @Test(expected = InvalidUrlException.class)
    public void shouldThrowWhenInvalidScheme() {
        new Resizin("test").withUrl("blabla");
    }

    @Test(expected = InvalidUrlException.class)
    public void shouldThrowWhenMissingImageId() {
        new Resizin("test").withUrl("https://seznam.cz");
    }

    @Test(expected = InvalidUrlException.class)
    public void shouldThrowWhenInvalidWidth() {
        new Resizin("test").withUrl("https://img.server.ack.ee/test/image/w_bla/123");
    }

    @Test(expected = InvalidUrlException.class)
    public void shouldThrowWhenInvaliHeight() {
        new Resizin("test").withUrl("https://img.server.ack.ee/test/image/w_bla/123");
    }

    @Test(expected = InvalidUrlException.class)
    public void shouldThrowWhenInvalidCrop() {
        new Resizin("test").withUrl("https://img.server.ack.ee/test/image/c_bla/123");
    }

    @Test(expected = InvalidUrlException.class)
    public void shouldThrowWhenInvalidGravity() {
        new Resizin("test").withUrl("https://img.server.ack.ee/test/image/g_bla/123");
    }
}
