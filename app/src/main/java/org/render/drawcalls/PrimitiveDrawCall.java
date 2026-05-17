package org.render.drawcalls;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import org.render.Renderer;

public abstract class PrimitiveDrawCall {
    protected float x;
    protected float y;
    protected Color color;
    protected ShapeType shapeType;

    protected PrimitiveDrawCall(float x, float y, ShapeType shapeType, Color color) {
        this.x = x;
        this.y = y;
        this.shapeType = shapeType;
        this.color = color;
    }

    protected void prepare(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        shapeRenderer.set(shapeType);
    }

    public abstract void draw(ShapeRenderer shapeRenderer);

    public void submit() {
        Renderer.addPrimitiveDrawCall(this);
    }
}
