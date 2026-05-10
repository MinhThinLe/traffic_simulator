package org.render.drawcalls;

import org.render.Renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GraphicalDrawCall {
    float x;
    float y;
    float rotation;

    protected GraphicalDrawCall(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public void submit() {
        Renderer.addGraphicalDrawCall(this);
    }

    public abstract void draw(SpriteBatch graphicalRenderer);
}
