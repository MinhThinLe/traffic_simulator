package org.render;

public enum DrawMode {
    GRAPHICAL,
    PRIMITIVE,
    ;

    @Override
    public String toString() {
        return switch (this.ordinal()) {
            case 0 -> "Graphical";
            case 1 -> "Primitive";
            default -> "INVALID STATE";
        };
    }
}
