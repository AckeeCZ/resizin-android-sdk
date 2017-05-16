package cz.ackee.imageserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for ImageServer
 *
 * @author Pavel Skala [pavel.skala@ackee.cz]
 * @since 15.05.2017
 **/
public class ImageServerTest {

    public static final String APP_ID = "test";
    public static final String URL = "test";

    @Test(expected = IllegalArgumentException.class)
    public void backgroundColorWrongFormat() throws Exception {
        ImageServer.UrlGenerator generator = new ImageServer.UrlGenerator(APP_ID, URL);
        generator.background("00abgh");
    }

    @Test
    public void backgroundColorCorrectFormat() throws Exception {
        ImageServer.UrlGenerator generator = new ImageServer.UrlGenerator(APP_ID, URL);
        generator.background("00bcd4");
        assertEquals("00bcd4", generator.background);
        generator.background("#00abcd");
        assertEquals("00abcd", generator.background);
    }

}
