package cz.ackee.imageserver;


public enum Crop {
    FILL("fill"),
    FIT("fit"),
    SCALE("scale"),
    FACE("face"),
    PAD("pad");

    private final String value;

    private Crop(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    public static Crop fromValue(String value) {
        for (Crop c : values()) {
            if (c.value.equals(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Invalid value " + value);
    }
}
