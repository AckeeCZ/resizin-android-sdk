package io.github.ackeecz.resizin.sdk

/**
 * Exception indicating that url passed to [Resizin.UrlGenerator] is invalid
 */
class InvalidUrlException(message: String, throwable: Throwable) : RuntimeException(message, throwable)
