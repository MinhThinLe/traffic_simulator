package org.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;
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
        table.top().right().add(makeRenderModeToggleButton());
    }

    private static TextButton makeRenderModeToggleButton() {
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = textRenderer;

        TextButton textButton = new TextButton(drawMode.toString(), textButtonStyle);

        textButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switch (drawMode) {
                    case DrawMode.PRIMITIVE:
                        drawMode = DrawMode.GRAPHICAL;
                        break;
                    case DrawMode.GRAPHICAL:
                        drawMode = DrawMode.PRIMITIVE;
                        break;
                    default:
                        break;
                }
                textButton.setText(drawMode.toString());
                return true;
            }
        });
        textButton.setOrigin(Align.center);

        return textButton;
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
