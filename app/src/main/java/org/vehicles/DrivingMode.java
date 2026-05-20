package org.vehicles;

public enum DrivingMode {
    NORMAL,
    AGGRESSIVE;

    @Override
    public String toString() {
        return switch (this) {
            case NORMAL -> "Bình thường";
            case AGGRESSIVE -> "Vượt đèn đỏ";
        };
    }
}
