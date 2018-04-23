package cz.ackee.resizin;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Background parsing tests
 */
public class BackgroundTest {

    public static final String APP_ID = "test";
    public static final String URL = "test";

    @Test(expected = IllegalArgumentException.class)
    public void backgroundColorWrongFormat() throws Exception {
        Resizin.UrlGenerator generator = new Resizin.UrlGenerator(APP_ID, URL);
        generator.background("00abgh");
    }

    @Test
    public void backgroundColorCorrectFormat() throws Exception {
        Resizin.UrlGenerator generator = new Resizin.UrlGenerator(APP_ID, URL);
        generator.background("00bcd4");
        assertEquals("00bcd4", generator.background);
        generator.background("#00abcd");
        assertEquals("00abcd", generator.background);
    }

}