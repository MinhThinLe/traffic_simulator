package org.render;

public enum DrawMode {
    GRAPHICAL,
    PRIMITIVE,
    ;

    @Override
    public String toString() {
        switch (this.ordinal()) {
            case 0:
                return "Graphical";
            case 1:
                return "Primitive";
            default:
                break;
        }

        return "INVALID STATE";
    }
}
