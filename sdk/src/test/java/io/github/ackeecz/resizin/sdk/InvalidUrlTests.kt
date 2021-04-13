package io.github.ackeecz.resizin.sdk

import org.junit.Test

/**
 * Tests for invalid urls
 */
class InvalidUrlTests {

    @Test(expected = InvalidUrlException::class)
    fun shouldThrowWhenInvalidScheme() {
        Resizin("test").withUrl("blabla")
    }

    @Test(expected = InvalidUrlException::class)
    fun shouldThrowWhenMissingImageId() {
        Resizin("test").withUrl("https://seznam.cz")
    }

    @Test(expected = InvalidUrlException::class)
    fun shouldThrowWhenInvalidWidth() {
        Resizin("test").withUrl("https://img.server.ack.ee/test/image/w_bla/123")
    }

    @Test(expected = InvalidUrlException::class)
    fun shouldThrowWhenInvaliHeight() {
        Resizin("test").withUrl("https://img.server.ack.ee/test/image/w_bla/123")
    }

    @Test(expected = InvalidUrlException::class)
    fun shouldThrowWhenInvalidCrop() {
        Resizin("test").withUrl("https://img.server.ack.ee/test/image/c_bla/123")
    }

    @Test(expected = InvalidUrlException::class)
    fun shouldThrowWhenInvalidGravity() {
        Resizin("test").withUrl("https://img.server.ack.ee/test/image/g_bla/123")
    }
}
