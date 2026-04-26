package org.render;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Renderer {
    public static SpriteBatch graphicalRenderer = new SpriteBatch();
    public static ShapeRenderer primitiveRenderer = new ShapeRenderer();
    public static BitmapFont textRenderer = new BitmapFont();

    public static void startBatch() {
        graphicalRenderer.begin();
        primitiveRenderer.begin();
    }

    public static void endBatch() {
        graphicalRenderer.end();
        primitiveRenderer.end();
    }

    public static void setProjectionMatrix(Matrix4 projectionMatrix) {
        graphicalRenderer.setProjectionMatrix(projectionMatrix);
        graphicalRenderer.setProjectionMatrix(projectionMatrix);
    }
}
