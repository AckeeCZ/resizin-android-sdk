package cz.ackee.resizin;

/**
 * Gravity of {@link Crop}
 */
public enum Gravity {
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

    private final String value;

    Gravity(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }


    public static Gravity fromValue(String value) {
        for (Gravity c : values()) {
            if (c.value.equals(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Invalid value " + value);
    }
}
