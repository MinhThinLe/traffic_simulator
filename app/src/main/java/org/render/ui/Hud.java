package org.render.ui;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;

import org.Globals;
import org.render.DrawMode;
import org.render.Renderer;

public class Hud implements Gui {
    private static final String VEHICLE_SPAWN_DELAY = "Độ trễ sinh phương tiện: ";

    @Override
    public Table createGUI() {
        Table table = new Table();
        table.setFillParent(true);

        Table innerTable = new Table();
        innerTable.add(makeDrawModeSwitcher()).align(Align.left).pad(5).row();
        innerTable.add(makeSpawnDelaySlider()).pad(5);

        innerTable.setBackground(Renderer.uiSkin.getDrawable("window2"));

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

    private static Table makeDrawModeSwitcher() {
        Table drawModeSwitcherComponent = new Table();

        TextButton button = new TextButton(Globals.drawMode.toString(), Styles.getButtonStyle());
        Label label = new Label("Chế độ hiển thị:", Styles.getLabelStyle());

        drawModeSwitcherComponent.addListener(
                new EventListener() {
                    @Override
                    public boolean handle(Event event) {
                        if (!(event instanceof ChangeEvent)) {
                            return false;
                        }

                        if (event.getTarget() == button) {
                            flipDrawMode();
                            button.setText(Globals.drawMode.toString());
                        }
                        return true;
                    }
                });

        drawModeSwitcherComponent.add(label).padRight(5);
        drawModeSwitcherComponent.add(button);

        return drawModeSwitcherComponent;
    }

    private static Table makeSpawnDelaySlider() {
        Table spawnDelaySliderComponent = new Table();

        Slider slider = new Slider(1, 60, 1, false, Styles.getSliderStyle());
        slider.setValue(Globals.vehicleSpawnDelay);

        Label label = new Label(vehicleSpawnDelayAsString(), Styles.getLabelStyle());

        spawnDelaySliderComponent.addListener(
                new EventListener() {
                    @Override
                    public boolean handle(Event event) {
                        if (!(event instanceof ChangeEvent)) {
                            return false;
                        }

                        if (event.getTarget() == slider) {
                            Globals.vehicleSpawnDelay = slider.getValue();
                            label.setText(vehicleSpawnDelayAsString());
                        }
                        return true;
                    }
                });

        spawnDelaySliderComponent.add(slider).row();
        spawnDelaySliderComponent.add(label);

        return spawnDelaySliderComponent;
    }

    private static String vehicleSpawnDelayAsString() {
        String padding = "";
        if (Globals.vehicleSpawnDelay < 10) {
            // Spaces in noto sans are apparently only half as wide as other characters
            padding = "  ";
        }
        return VEHICLE_SPAWN_DELAY + padding + (int) Globals.vehicleSpawnDelay + "s";
    }
}
