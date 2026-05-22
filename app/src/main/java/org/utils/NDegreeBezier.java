package org.utils;

import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class NDegreeBezier {
    private List<Vector2> controlPoints;

    public NDegreeBezier(List<Vector2> controlPoints) {
        this.controlPoints = controlPoints;
    }

    public Vector2 interpolate(float t) {
        if (controlPoints.size() == 4) {
            return Bezier.cubic(new Vector2(), t, controlPoints.get(0), controlPoints.get(1), controlPoints.get(2), controlPoints.get(3), new Vector2());
        }
        if (controlPoints.size() == 3) {
            return Bezier.quadratic(new Vector2(), t, controlPoints.get(0), controlPoints.get(1), controlPoints.get(2), new Vector2());
        }
        throw new IllegalStateException("Only quadratic and cubic bézier curves are supported");
    }
}
