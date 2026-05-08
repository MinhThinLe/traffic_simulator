package org.render;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.Globals;
import org.render.drawcalls.*;

public class Renderer {
    private static final String FONT_PATH = "org/render/ui/skin/font-export.fnt";

    private static SpriteBatch graphicalRenderer = new SpriteBatch();
    private static List<GraphicalDrawCall> graphicalDrawCalls = new ArrayList<>();

    private static ShapeRenderer primitiveRenderer = new ShapeRenderer();
    private static List<PrimitiveDrawCall> primitiveDrawCalls = new ArrayList<>();
    
    public static BitmapFont textRenderer = new BitmapFont(Gdx.files.internal(FONT_PATH));

    static {
        primitiveRenderer.setAutoShapeType(true);
    }

    public static void render(Matrix4 matrix) {
        primitiveRenderer.setProjectionMatrix(new Matrix4(matrix));
        graphicalRenderer.setProjectionMatrix(new Matrix4(matrix));
        
        ScreenUtils.clear(Color.WHITE);
        renderShapes();
        renderTextures();
        Globals.stage.draw();
    }  

    private static void renderShapes() {
        primitiveDrawCalls.sort(new DrawCallComparator());
        primitiveRenderer.begin();

        for (int i = 0; i < primitiveDrawCalls.size(); i++) {
            primitiveDrawCalls.get(i).draw(primitiveRenderer);
        }

        primitiveRenderer.end();
        primitiveDrawCalls.clear();
    }

    private static void renderTextures() {
        // Figure out how to batch texture draw calls later
        graphicalRenderer.begin();

        for (int i = 0; i < graphicalDrawCalls.size(); i++) {
            graphicalDrawCalls.get(i).draw(graphicalRenderer);
        }

        graphicalRenderer.end();
        graphicalDrawCalls.clear();
    }

    public static void addPrimitiveDrawCall(PrimitiveDrawCall drawCall) {
        primitiveDrawCalls.add(drawCall);
    }

    public static void addGraphicalDrawCall(GraphicalDrawCall drawCall) {
        graphicalDrawCalls.add(drawCall);
    }

    public static void resize(int width, int height) {
        Viewport viewport = Globals.stage.getViewport();
        viewport.update(width, height, true);
    }

    public static void processUI(float deltaTime) {
        Globals.stage.act(deltaTime);
    }
}
