package org.render.drawcalls;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureDrawCall extends GraphicalDrawCall {
    protected TextureRegion texture;
    protected float originX;
    protected float originY;
    protected float width;
    protected float height;
    protected float scaleX;
    protected float scaleY;

    public TextureDrawCall(
            TextureRegion texture,
            float x,
            float y,
            float z,
            float originX,
            float originY,
            float width,
            float height,
            float scaleX,
            float scaleY,
            float rotation) {
        super(x, y, z, rotation);
        this.texture = texture;
        this.originX = originX;
        this.originY = originY;
        this.width = width;
        this.height = height;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public TextureDrawCall(
            TextureRegion texture,
            float x,
            float y,
            float originX,
            float originY,
            float width,
            float height,
            float scaleX,
            float scaleY,
            float rotation) {
        this(texture, x, y, 0f, originX, originY, width, height, scaleX, scaleY, rotation);
    }

    public TextureDrawCall(TextureRegion texture, float x, float y, float z, float width, float height) {
        this(texture, x, y, z, width / 2, height / 2, width, height, 1, 1, 0);
    }

    @Override
    public void draw(SpriteBatch graphicalRenderer) {
        graphicalRenderer.draw(
                texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
    }
}
