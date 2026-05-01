package org;

import org.render.*;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.Gdx;
import org.render.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class Globals {
    private static final String UI_SKIN_PATH = "org/render/ui/skin/skin-composer-ui.atlas";

    public static DrawMode drawMode = DrawMode.PRIMITIVE;
    public static float vehicleSpawnDelay = 10;
    public static Stage stage = new Stage(new FitViewport(1280, 720));

    static {
        initializeUI();
    }

    private static void initializeUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(UI_SKIN_PATH));
        Skin uiSkin = new Skin(atlas);

        RenderModeButton button = new RenderModeButton(Renderer.textRenderer, uiSkin);
        VehicleDensitySlider slider = new VehicleDensitySlider(uiSkin);

        LabelStyle labelStyle = new LabelStyle(Renderer.textRenderer, Color.BLACK);
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
}
