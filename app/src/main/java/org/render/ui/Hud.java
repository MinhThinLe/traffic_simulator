package org.render.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import org.Globals;
import org.render.DrawMode;
import org.render.Renderer;

public class Hud implements Gui {
    @Override
    public Table createGUI() {
        System.out.println("I ran");
        Table table = new Table();
        table.setFillParent(true);

        TextButton button =
                new TextButton(
                        DrawMode.PRIMITIVE + "",
                        Styles.makeButtonStyle(Renderer.uiSkin, Renderer.font));
        Slider slider =
                new Slider(1, 60, 1, false, Styles.makeSliderStyle(Renderer.uiSkin, Renderer.font));

        LabelStyle labelStyle = new LabelStyle(Renderer.font, Color.BLACK);
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

        return table;
    }

    private static void flipDrawMode() {
        Globals.drawMode =
                switch (Globals.drawMode) {
                    case DrawMode.PRIMITIVE -> DrawMode.GRAPHICAL;
                    case DrawMode.GRAPHICAL -> DrawMode.PRIMITIVE;
                };
    }
}
