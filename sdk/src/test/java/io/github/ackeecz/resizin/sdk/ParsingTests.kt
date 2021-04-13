package io.github.ackeecz.resizin.sdk

import io.github.ackeecz.resizin.sdk.Crop
import io.github.ackeecz.resizin.sdk.Gravity
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests for parsing of urls
 */
class ParsingTests {

    private var resizin = Resizin("test")

    private fun assertCorrectlyGenerated(generator: Resizin.UrlGenerator) {
        val url = generator
            .generate("image")

        assertEquals(url, resizin.withUrl(url)
            .generate("image"))
    }

    @Test
    fun shouldRestoreWidth() {
        assertCorrectlyGenerated(resizin
            .url()
            .width(100)
        )
    }

    @Test
    fun shouldRestoreHeight() {
        assertCorrectlyGenerated(resizin
            .url()
            .height(100)
        )
    }

    @Test
    fun shouldRestoreCrop() {
        assertCorrectlyGenerated(resizin
            .url()
            .crop(Crop.FACE)
        )
    }

    @Test
    fun shouldRestoreGravity() {
        assertCorrectlyGenerated(resizin
            .url()
            .gravity(Gravity.CENTER)
        )
    }

    @Test
    fun shouldRestoreGrayscale() {
        assertCorrectlyGenerated(resizin
            .url()
            .grayscale(true)
        )
    }

    @Test
    fun shouldRestoreQuality() {
        assertCorrectlyGenerated(resizin
            .url()
            .quality(100)
        )
    }

    @Test
    fun shouldRestoreUpscale() {
        assertCorrectlyGenerated(resizin
            .url()
            .upscale(true)
        )
    }

    @Test
    fun shouldRestoreBorders() {
        assertCorrectlyGenerated(resizin
            .url()
            .border(6)
        )
    }

    @Test
    fun shouldRestoreBordersAll() {
        assertCorrectlyGenerated(resizin
            .url()
            .border(1, 2, 3, 4)
        )
    }

    @Test
    fun shouldRestoreBackground() {
        assertCorrectlyGenerated(resizin
            .url()
            .background("#ffffff")
        )
    }

    @Test
    fun shouldRestoreImageId() {
        assertEquals("test", resizin.withUrl(resizin.url().generate("test")).imageId)
    }

    @Test
    fun shouldRestoreImageIdWithExtension() {
        assertCorrectlyGenerated(resizin
            .url()
            .extension("jpg")
        )
    }

    @Test
    fun shouldRestoreAllAttributes() {
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
        )
    }
}
