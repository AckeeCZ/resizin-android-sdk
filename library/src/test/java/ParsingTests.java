import org.junit.Test;

import cz.ackee.resizin.Crop;
import cz.ackee.resizin.Gravity;
import cz.ackee.resizin.Resizin;

import static org.junit.Assert.assertEquals;

/**
 * Tests for parsing of urls
 */
public class ParsingTests {

    Resizin resizin = new Resizin("test");

    private void assertCorrectlyGenerated(Resizin.UrlGenerator generator) {
        String url = generator
                .generate("image");

        assertEquals(url, resizin.withUrl(url)
                .generate("image"));
    }

    @Test
    public void shouldRestoreWidth() {
        assertCorrectlyGenerated(resizin
                .url()
                .width(100)
        );
    }

    @Test
    public void shouldRestoreHeight() {
        assertCorrectlyGenerated(resizin
                .url()
                .height(100)
        );
    }

    @Test
    public void shouldRestoreCrop() {
        assertCorrectlyGenerated(resizin
                .url()
                .crop(Crop.FACE)
        );
    }

    @Test
    public void shouldRestoreGravity() {
        assertCorrectlyGenerated(resizin
                .url()
                .gravity(Gravity.CENTER)
        );
    }

    @Test
    public void shouldRestoreGrayscale() {
        assertCorrectlyGenerated(resizin
                .url()
                .grayscale(true)
        );
    }

    @Test
    public void shouldRestoreQuality() {
        assertCorrectlyGenerated(resizin
                .url()
                .quality(100)
        );
    }

    @Test
    public void shouldRestoreUpscale() {
        assertCorrectlyGenerated(resizin
                .url()
                .upscale(true)
        );
    }

    @Test
    public void shouldRestoreBorders() {
        assertCorrectlyGenerated(resizin
                .url()
                .border(6)
        );
    }

    @Test
    public void shouldRestoreBordersAll() {
        assertCorrectlyGenerated(resizin
                .url()
                .border(1, 2, 3, 4)
        );
    }

    @Test
    public void shouldRestoreBackground() {
        assertCorrectlyGenerated(resizin
                .url()
                .background("#ffffff")
        );
    }

    @Test
    public void shouldRestoreImageId() {
        assertEquals("test", resizin.withUrl(resizin.url().generate("test")).getImageId());
    }

    @Test
    public void shouldRestoreImageIdWithExtension() {
        assertCorrectlyGenerated(resizin
                .url()
                .extension("jpg")
        );
    }

    @Test
    public void shouldRestoreAllAttributes() {
        assertCorrectlyGenerated(resizin
                .url()
                .width(100)
                .height(200)
                .background("#ffffff")
                .border(5)
                .quality(100)
                .gravity(Gravity.E)
                .crop(Crop.FILL)
                .upscale(true)
                .grayscale(true)
                .extension("jpeg")
        );
    }
}
