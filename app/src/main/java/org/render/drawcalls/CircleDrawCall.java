package org.render.drawcalls;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class CircleDrawCall extends PrimitiveDrawCall {
    protected float radius;

    public CircleDrawCall(float x, float y, float radius, ShapeType shapeType, Color color) {
        super(x, y, shapeType, color);
        this.radius = radius;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        prepare(shapeRenderer);
        shapeRenderer.circle(x, y, radius);
    }
}
