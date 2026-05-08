package org.render.drawcalls;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class LineDrawCall extends PrimitiveDrawCall {
    protected float x2;
    protected float y2;
    protected float thickness;

    public LineDrawCall(
            float x1,
            float y1,
            float x2,
            float y2,
            float thickness,
            Color color,
            ShapeType shapeType) {
        super(x1, y1, shapeType, color);
        this.x2 = x2;
        this.y2 = y2;
        this.thickness = thickness;
    }

    public LineDrawCall(
            Vector2 start, Vector2 end, float thickness, Color color, ShapeType shapeType) {
        super(start.x, start.y, shapeType, color);
        this.x2 = end.x;
        this.y2 = end.y;
        this.thickness = thickness;
    }

    public LineDrawCall(Vector2 start, Vector2 end, Color color, ShapeType shapeType) {
        super(start.x, start.y, shapeType, color);
        this.x2 = end.x;
        this.y2 = end.y;
        this.thickness = 1;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        prepare(shapeRenderer);
        if (thickness > 1) {
            shapeRenderer.rectLine(x, y, x2, y2, thickness);
            return;
        }
        shapeRenderer.line(x, y, x2, y2);
    }
}
