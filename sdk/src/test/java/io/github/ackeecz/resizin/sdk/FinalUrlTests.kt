package io.github.ackeecz.resizin.sdk

import junit.framework.Assert.assertEquals
import org.junit.Test

class FinalUrlTests {

    @Test
    fun `should have correct urls without modifiers`() {
        // arrange
        val resizin = Resizin("appid")
        // act
        val url = resizin.url().generate(imageId = "img")
        // assert
        assertEquals("https://img.resizin.com/appid/image/img", url)
    }

    @Test
    fun `should have correct format with specified width and height`() {
        // arrange
        val resizin = Resizin("appid")
        // act
        val url = resizin.url()
            .width(100)
            .height(100)
            .generate(imageId = "img")
        // assert
        assertEquals("https://img.resizin.com/appid/image/w_100-h_100/img", url)
    }
}