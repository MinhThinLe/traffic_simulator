package org.render;

public enum DrawMode {
    GRAPHICAL,
    PRIMITIVE,
    ;

    @Override
    public String toString() {
        return switch (this.ordinal()) {
            case 0 -> "Đồ họa";
            case 1 -> "Cơ bản";
            default -> "INVALID STATE";
        };
    }
}
