import org.junit.Test;

import cz.ackee.imageserver.Crop;
import cz.ackee.imageserver.Gravity;
import cz.ackee.imageserver.ImageServer;

import static org.junit.Assert.assertEquals;

/**
 * TODO add class description
 *
 * @author David Bilik [david.bilik@ackee.cz]
 * @since 12/01/2018
 **/
public class ParsingTests {
    ImageServer imageServer = new ImageServer("test");


    private void assertCorrectlyGenerated(ImageServer.UrlGenerator generator) {
        String url = generator
                .generate("image");

        assertEquals(url, imageServer.withUrl(url)
                .generate("image"));
    }

    @Test
    public void shouldRestoreWidth() {
        assertCorrectlyGenerated(imageServer
                .url()
                .width(100)
        );
    }


    @Test
    public void shouldRestoreHeight() {
        assertCorrectlyGenerated(imageServer
                .url()
                .height(100)
        );
    }

    @Test
    public void shouldRestoreCrop() {
        assertCorrectlyGenerated(imageServer
                .url()
                .crop(Crop.FACE)
        );
    }

    @Test
    public void shouldRestoreGravity() {
        assertCorrectlyGenerated(imageServer
                .url()
                .gravity(Gravity.CENTER)
        );
    }

    @Test
    public void shouldRestoreGrayscale() {
        assertCorrectlyGenerated(imageServer
                .url()
                .grayscale(true)
        );
    }

    @Test
    public void shouldRestoreQuality() {
        assertCorrectlyGenerated(imageServer
                .url()
                .quality(100)
        );
    }

    @Test
    public void shouldRestoreUpscale() {
        assertCorrectlyGenerated(imageServer
                .url()
                .upscale(true)
        );
    }

    @Test
    public void shouldRestoreBorders() {
        assertCorrectlyGenerated(imageServer
                .url()
                .border(6)
        );
    }

    @Test
    public void shouldRestoreBordersAll() {
        assertCorrectlyGenerated(imageServer
                .url()
                .border(1, 2, 3, 4)
        );
    }

    @Test
    public void shouldRestoreBackground() {
        assertCorrectlyGenerated(imageServer
                .url()
                .background("#ffffff")
        );
    }

    @Test
    public void shouldRestoreImageId() {
       assertEquals("test", imageServer.withUrl(imageServer.url().generate("test")).getImageId());
    }

    @Test
    public void shouldRestoreImageIdWithExtension() {
        assertCorrectlyGenerated(imageServer
                .url()
                .extension("jpg")
        );
    }

    @Test
    public void shouldRestoreAllAttributes() {
        assertCorrectlyGenerated(imageServer
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
