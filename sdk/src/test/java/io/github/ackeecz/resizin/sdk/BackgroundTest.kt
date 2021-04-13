package io.github.ackeecz.resizin.sdk

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Background parsing tests
 */
class BackgroundTest {

    companion object {

        private const val APP_ID = "test"
        private const val URL = "test"
    }

    @Test(expected = IllegalArgumentException::class)
    @Throws(Exception::class)
    fun backgroundColorWrongFormat() {
        val generator = Resizin.UrlGenerator(APP_ID, URL)
        generator.background("00abgh")
    }

    @Test
    @Throws(Exception::class)
    fun backgroundColorCorrectFormat() {
        val generator = Resizin.UrlGenerator(APP_ID, URL)
        generator.background("00bcd4")
        assertEquals("00bcd4", generator.background)
        generator.background("#00abcd")
        assertEquals("00abcd", generator.background)
    }
}