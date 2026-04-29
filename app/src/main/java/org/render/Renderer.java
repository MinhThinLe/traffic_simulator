package org.render;

import org.render.ui.RenderModeButton;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Renderer {
    public static SpriteBatch graphicalRenderer = new SpriteBatch();
    public static ShapeRenderer primitiveRenderer = new ShapeRenderer();
    public static BitmapFont textRenderer = new BitmapFont();
    public static DrawMode drawMode = DrawMode.PRIMITIVE;

    public static Stage stage = new Stage(new FitViewport(1280, 720));
    private static Table table = new Table();

    static {
        primitiveRenderer.setAutoShapeType(true);
        table.setFillParent(true);
        stage.addActor(table);

        initializeUI();
    }

    private static void initializeUI() {
        RenderModeButton button = new RenderModeButton(textRenderer);

        table.top().right().add(button);

        table.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event.getTarget() == button) {
                    flipDrawMode();
                    button.setText(drawMode.toString());
                }
                return false;
            }           
        });
    }

    private static void flipDrawMode() {
        switch (drawMode) {
            case DrawMode.PRIMITIVE:
                drawMode = DrawMode.GRAPHICAL;
                break;
            case DrawMode.GRAPHICAL:
                drawMode = DrawMode.PRIMITIVE;
            default:
                break;
        }
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

    public static void resize(int width, int height) {
        Viewport viewport = stage.getViewport();
        viewport.update(width, height, true);

    }

    public static void processUI(float deltaTime) {
        stage.act(deltaTime);
    }

    public static void drawUI() {
        stage.draw();
    }
}
