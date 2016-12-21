package cz.ackee.imageserver;

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

    private Gravity(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
