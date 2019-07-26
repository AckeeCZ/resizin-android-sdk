package cz.ackee.resizin

/**
 * Gravity of [Crop]
 */
enum class Gravity private constructor(private val value: String) {

    S("s"),
    N("n"),
    W("w"),
    E("e"),
    SW("sw"),
    SE("se"),
    NW("nw"),
    NE("ne"),
    CENTER("center"),
    FACE("face");

    override fun toString() = this.value

    companion object {

        fun fromValue(value: String): Gravity {
            return values().firstOrNull { it.value == value } ?: throw IllegalArgumentException("Invalid value $value")
        }
    }
}
