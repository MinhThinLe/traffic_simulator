package org.render.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import org.Globals;
import org.render.DrawMode;
import org.render.Renderer;
import org.vehicles.VehicleFactory;

public class Hud implements Gui {
    private static final String VEHICLE_SPAWN_DELAY = "Độ trễ sinh phương tiện: ";
    private static final String SIMULATION_SPEED_MESSAGE_STRING = "Tốc độ mô phỏng: ";

    @Override
    public Table createGUI() {
        Table table = new Table();
        table.setFillParent(true);

        Table innerTable = new Table();
        innerTable.add(makeDrawModeSwitcher()).align(Align.left).pad(5).row();
        innerTable.add(makeSpawnDelaySlider()).align(Align.left).pad(5).row();
        innerTable.add(makeSimulationSpeedSlider()).align(Align.left).pad(5).row();
        innerTable.add(makeVehicleToggleComponent()).align(Align.left).pad(5).row();

        innerTable.setBackground(Renderer.uiSkin.getDrawable("window2"));

        table.top().right().pad(7).add(innerTable).row();

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
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (actor == button) {
                            flipDrawMode();
                            button.setText(Globals.drawMode.toString());
                        }
                    }
                });

        drawModeSwitcherComponent.add(label).padRight(5);
        drawModeSwitcherComponent.add(button).align(Align.right);

        return drawModeSwitcherComponent;
    }

    private static Table makeSpawnDelaySlider() {
        Table spawnDelaySliderComponent = new Table();

        Slider slider = new Slider(1, 60, 1, false, Styles.getSliderStyle());
        slider.setValue(Globals.vehicleSpawnDelay);

        Label label = new Label(vehicleSpawnDelayAsString(), Styles.getLabelStyle());

        spawnDelaySliderComponent.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (actor == slider) {
                            Globals.vehicleSpawnDelay = slider.getValue();
                            label.setText(vehicleSpawnDelayAsString());
                        }
                    }
                });

        spawnDelaySliderComponent.add(slider).align(Align.left).row();
        spawnDelaySliderComponent.add(label);

        return spawnDelaySliderComponent;
    }

    private static Table makeSimulationSpeedSlider() {
        Table simulationSpeedSliderComponent = new Table();

        Slider slider = new Slider(0f, 5f, 0.1f, false, Styles.getSliderStyle());
        slider.setValue(Globals.simulationSpeed);

        Label label = new Label(simulationSpeedAsString(), Styles.getLabelStyle());

        simulationSpeedSliderComponent.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (actor == slider) {
                            Globals.simulationSpeed = slider.getValue();
                            label.setText(simulationSpeedAsString());
                        }
                    }
                });

        simulationSpeedSliderComponent.add(slider).align(Align.left).row();
        simulationSpeedSliderComponent.add(label);

        return simulationSpeedSliderComponent;
    }

    private static String vehicleSpawnDelayAsString() {
        String padding = "";
        if (Globals.vehicleSpawnDelay < 10) {
            // Spaces in noto sans are apparently only half as wide as other characters
            padding = "  ";
        }
        return VEHICLE_SPAWN_DELAY + padding + (int) Globals.vehicleSpawnDelay + "s";
    }

    private static String simulationSpeedAsString() {
        return SIMULATION_SPEED_MESSAGE_STRING
                + (Globals.simulationSpeed + "").substring(0, 3)
                + "x";
    }

    private static final int COLUMNS = 3;
    private static final float THUMBNAIL_SIZE = 64;
    private static Table makeVehicleToggleComponent() {
        Table vehicleToggleTable = new Table();

        for (int i = 0; i < Globals.VEHICLE_FACTORIES.size(); i++) {
            VehicleFactory vehicleFactory = Globals.VEHICLE_FACTORIES.get(i);

            Sprite thumbnail = vehicleFactory.getThumbnail();
            Image vehicleThumbnail = new Image(thumbnail);

            CheckBox checkBox = new CheckBox("", Styles.getCheckBoxStyle());
            checkBox.setChecked(true);
            checkBox.setUserObject(vehicleFactory);
            checkBox.setName("vehicle factory checkbox");

            vehicleToggleTable.add(vehicleThumbnail).size(THUMBNAIL_SIZE);
            vehicleToggleTable.add(checkBox);

            if (i % COLUMNS == COLUMNS - 1) {
                vehicleToggleTable.row();
            }
        }

        return vehicleToggleTable;
    }
}
