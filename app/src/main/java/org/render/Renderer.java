package org.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.Globals;

public class Renderer {
    private static final String FONT_PATH = "org/render/ui/skin/font-export.fnt";

    public static SpriteBatch graphicalRenderer = new SpriteBatch();
    public static ShapeRenderer primitiveRenderer = new ShapeRenderer();
    public static ShapeRenderer filledPrimitiveRenderer = new ShapeRenderer();
    public static BitmapFont textRenderer = new BitmapFont(Gdx.files.internal(FONT_PATH));

    static {
        primitiveRenderer.setAutoShapeType(true);
        filledPrimitiveRenderer.setAutoShapeType(true);
    }

    public static void startBatch() {
        graphicalRenderer.begin();
        primitiveRenderer.begin();
        filledPrimitiveRenderer.begin();
        filledPrimitiveRenderer.set(ShapeType.Filled);

        ScreenUtils.clear(Color.WHITE);
    }

    public static void endBatch() {
        graphicalRenderer.end();
        primitiveRenderer.end();
        filledPrimitiveRenderer.end();
    }

    public static void resize(int width, int height) {
        Viewport viewport = Globals.stage.getViewport();
        viewport.update(width, height, true);
    }

    public static void processUI(float deltaTime) {
        Globals.stage.act(deltaTime);
    }

    public static void drawUI() {
        Globals.stage.draw();
    }
}
