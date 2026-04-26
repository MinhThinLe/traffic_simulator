package org.render;

import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Renderer {
    public static SpriteBatch graphicalRenderer = new SpriteBatch();
    public static ShapeRenderer primitiveRenderer = new ShapeRenderer();
    public static BitmapFont textRenderer = new BitmapFont();

    static {
        primitiveRenderer.setAutoShapeType(true);
    }

    public static void startBatch() {
        graphicalRenderer.begin();
        primitiveRenderer.begin();
        
        ScreenUtils.clear(Color.WHITE);
    }

    public static void endBatch() {
        graphicalRenderer.end();
        primitiveRenderer.end();
    }
}
