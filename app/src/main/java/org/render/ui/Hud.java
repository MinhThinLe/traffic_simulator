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
    private static final String VEHICLE_SPAWN_DELAY = "Độ trễ sinh phương tiện";
    @Override
    public Table createGUI() {
        Table table = new Table();
        table.setFillParent(true);

        TextButton button =
                new TextButton(
                        DrawMode.PRIMITIVE + "",
                        Styles.makeButtonStyle(Renderer.uiSkin, Renderer.getFont(Globals.FONT_SIZE)));
        Slider slider =
                new Slider(1, 60, 1, false, Styles.makeSliderStyle(Renderer.uiSkin, Renderer.getFont(Globals.FONT_SIZE)));
        slider.setValue(10);

        LabelStyle labelStyle = new LabelStyle(Renderer.getFont(Globals.FONT_SIZE), Color.BLACK);
        Label label = new Label(VEHICLE_SPAWN_DELAY + ": 10", labelStyle);

        Table innerTable = new Table();
        innerTable.add(button).pad(10).row();
        innerTable.add(slider).row();
        innerTable.add(label).row();

        innerTable.setBackground(Renderer.uiSkin.getDrawable("window2"));

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
                            label.setText(VEHICLE_SPAWN_DELAY + ": " + Globals.vehicleSpawnDelay);
                        }

                        return true;
                    }
                };

        table.addListener(eventListener);

        table.top().right().pad(7).add(innerTable);

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
