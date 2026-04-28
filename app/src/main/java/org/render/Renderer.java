package org.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.ScreenUtils;

public class Renderer {
    public static SpriteBatch graphicalRenderer = new SpriteBatch();
    public static ShapeRenderer primitiveRenderer = new ShapeRenderer();
    public static BitmapFont textRenderer = new BitmapFont();
    public static DrawMode drawMode = DrawMode.PRIMITIVE;

    public static Stage stage = new Stage();
    private static Stack stack = new Stack();

    static {
        primitiveRenderer.setAutoShapeType(true);
        stack.setFillParent(true);
        stage.addActor(stack);
        
        initializeUI();
    }

    private static void initializeUI() {
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = textRenderer;

        TextButton textButton = new TextButton(drawMode.toString(), textButtonStyle);

        textButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (drawMode == DrawMode.PRIMITIVE) {
                    drawMode = DrawMode.GRAPHICAL;
                }
                if (drawMode == DrawMode.GRAPHICAL) {
                    drawMode = DrawMode.PRIMITIVE;
                }
                textButton.setText(drawMode.toString());
                System.out.println(drawMode);
                return true;
            }
        });
        textButton.debugActor();

        stack.addActor(textButton);
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
