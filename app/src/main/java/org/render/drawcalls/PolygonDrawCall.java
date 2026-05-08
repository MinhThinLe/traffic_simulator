package org.render.drawcalls;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;

public class PolygonDrawCall extends PrimitiveDrawCall {
    protected Polygon polygon;

    public PolygonDrawCall(Polygon polygon, Color color, ShapeType shapeType) {
        super(0, 0, shapeType, color);
        this.polygon = polygon;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        prepare(shapeRenderer);
        shapeRenderer.polygon(polygon.getTransformedVertices());
    }
}
