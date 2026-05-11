package org.render.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import org.Globals;
import org.render.DrawMode;
import org.render.Renderer;

public class Hud {
    private static final String UI_SKIN_PATH = "org/render/ui/skin/skin-composer-ui.atlas";

    public void initializeHUDGUI(Stage stage) {
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

        EventListener eventListener =
                new EventListener() {
                    @Override
                    public boolean handle(Event event) {
                        if (event.getClass() != ChangeEvent.class) {
                            return false;
                        }

                        if (event.getTarget() == button) {
                            flipDrawMode();
                            button.setText(Globals.drawMode.toString());
                        }
                        if (event.getTarget() == slider) {
                            Globals.vehicleSpawnDelay = slider.getValue();
                            label.setText("Seconds per vehicle: " + Globals.vehicleSpawnDelay);
                        }

                        return true;
                    }
                };

        table.addListener(eventListener);
    }

    private static void flipDrawMode() {
        Globals.drawMode =
                switch (Globals.drawMode) {
                    case DrawMode.PRIMITIVE -> DrawMode.GRAPHICAL;
                    case DrawMode.GRAPHICAL -> DrawMode.PRIMITIVE;
                };
    }
}
