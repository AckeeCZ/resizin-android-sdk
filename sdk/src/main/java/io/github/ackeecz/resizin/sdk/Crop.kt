package io.github.ackeecz.resizin.sdk

/**
 * Types of crop avaliable at resizin.com
 */
enum class Crop(private val value: String) {

    FILL("fill"),
    FIT("fit"),
    SCALE("scale"),
    FACE("face"),
    PAD("pad");

    override fun toString() = value

    companion object {

        fun fromValue(value: String): Crop {
            return values().firstOrNull { it.value == value } ?: throw IllegalArgumentException("Invalid value $value")
        }
    }
}
