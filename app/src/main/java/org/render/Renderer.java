package org.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.render.ui.RenderModeButton;
import org.render.ui.VehicleDensitySlider;

public class Renderer {
    private static final String FONT_PATH = "org/render/ui/skin/font-export.fnt";
    private static final String UI_SKIN_PATH = "org/render/ui/skin/skin-composer-ui.atlas";

    public static SpriteBatch graphicalRenderer = new SpriteBatch();
    public static ShapeRenderer primitiveRenderer = new ShapeRenderer();
    public static ShapeRenderer filledPrimitiveRenderer = new ShapeRenderer();
    public static BitmapFont textRenderer = new BitmapFont(Gdx.files.internal(FONT_PATH));

    public static DrawMode drawMode = DrawMode.PRIMITIVE;
    public static float vehicleSpawnDelay = 10;

    public static Stage stage = new Stage(new FitViewport(1280, 720));

    static {
        primitiveRenderer.setAutoShapeType(true);
        filledPrimitiveRenderer.setAutoShapeType(true);

        initializeUI();
    }

    private static void initializeUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(UI_SKIN_PATH));
        Skin uiSkin = new Skin(atlas);

        RenderModeButton button = new RenderModeButton(textRenderer, uiSkin);
        VehicleDensitySlider slider = new VehicleDensitySlider(uiSkin);

        LabelStyle labelStyle = new LabelStyle(textRenderer, Color.BLACK);
        Label label = new Label("Seconds per vehicle: 10", labelStyle);

        table.top().right().add(button);
        table.row();
        table.top().right().add(slider);
        table.row();
        table.top().right().add(label);

        table.addListener(
                new EventListener() {
                    @Override
                    public boolean handle(Event event) {
                        if (event.getClass() != ChangeEvent.class) {
                            return false;
                        }

                        if (event.getTarget() == button) {
                            flipDrawMode();
                            button.setText(drawMode.toString());
                        }
                        if (event.getTarget() == slider) {
                            vehicleSpawnDelay = slider.getValue();
                            label.setText("Seconds per vehicle: " + vehicleSpawnDelay);
                        }

                        return true;
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
