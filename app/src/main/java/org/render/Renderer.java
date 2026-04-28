package org.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.ScreenUtils;

public class Renderer {
    public static SpriteBatch graphicalRenderer = new SpriteBatch();
    public static ShapeRenderer primitiveRenderer = new ShapeRenderer();
    public static BitmapFont textRenderer = new BitmapFont();
    public static DrawMode drawMode = DrawMode.PRIMITIVE;

    private static Stage stage = new Stage();
    private static Table table = new Table();

    static {
        primitiveRenderer.setAutoShapeType(true);

        table.setFillParent(true);
        stage.addActor(table);

        LabelStyle style = new LabelStyle(textRenderer, Color.RED);
        Label label = new Label("Drawn from UI", style);

        table.add(label);
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

    public static void processUI(float deltaTime) {
        stage.act(deltaTime);
    }

    public static void drawUI() {
        stage.draw();
    }
}
