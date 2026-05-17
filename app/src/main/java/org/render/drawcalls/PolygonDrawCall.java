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
        if (shapeType == ShapeType.Filled) {
            float[] verticies = polygon.getTransformedVertices();
            shapeRenderer.triangle(
                    verticies[0],
                    verticies[1],
                    verticies[2],
                    verticies[3],
                    verticies[4],
                    verticies[5]);
            shapeRenderer.triangle(
                    verticies[0],
                    verticies[1],
                    verticies[4],
                    verticies[5],
                    verticies[6],
                    verticies[7]);
            return;
        }
        shapeRenderer.polygon(polygon.getTransformedVertices());
    }
}
