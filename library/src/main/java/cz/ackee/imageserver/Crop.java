package cz.ackee.imageserver;


public enum Crop {
    FILL("fill"),
    FIT("fit"),
    SCALE("scale"),
    PAD("pad");

    private final String value;

    private Crop(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
