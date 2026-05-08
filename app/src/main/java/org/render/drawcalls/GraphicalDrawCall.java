package org.render.drawcalls;

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

    public abstract void draw(SpriteBatch graphicalRenderer); 
}
