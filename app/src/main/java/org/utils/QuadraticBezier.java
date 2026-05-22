package org.utils;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class QuadraticBezier {
    private List<Vector2> controlPoints;

    public QuadraticBezier(List<Vector2> controlPoints) {
        this.controlPoints = controlPoints;
    }

    public Vector2 interpolate(float t) {
        Vector2 point0 = controlPoints.get(0);
        Vector2 point1 = controlPoints.get(1);
        Vector2 point2 = controlPoints.get(2);

        // (1 - t)^2 P0 + 2t(1 - t) P1 + t^2 P2, 0 <= t <= 1
        return new Vector2(point0)
                .scl((float) Math.pow(1 - t, 2))
                .add(new Vector2(point1).scl(2 * t * (1 - t)))
                .add(new Vector2(point2).scl(t * t));
    }
}
