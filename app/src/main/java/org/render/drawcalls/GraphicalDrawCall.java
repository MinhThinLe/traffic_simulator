package org.render.drawcalls;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.render.Renderer;

public abstract class GraphicalDrawCall {
    float x;
    float y;
    float z;
    float rotation;

    protected GraphicalDrawCall(float x, float y, float z, float rotation) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotation = rotation;
    }

    protected GraphicalDrawCall(float x, float y, float rotation) {
        this(x, y, 0, rotation);
    }

    public void submit() {
        Renderer.addGraphicalDrawCall(this);
    }

    public abstract void draw(SpriteBatch graphicalRenderer);
}
