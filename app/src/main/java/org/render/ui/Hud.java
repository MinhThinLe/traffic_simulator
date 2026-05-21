package org.render.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
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

import java.util.List;

public class Hud implements Gui, Inspector {
    private static final String SIMULATION_SPEED_MESSAGE_STRING = "Tốc độ mô phỏng: ";
    private static final float PADDING = 5;

    private static Table inspecteeTable = new Table();
    private static Table hudTable = new Table();

    @Override
    public Table createGUI() {
        Table table = new Table();
        table.setFillParent(true);

        hudTable.clear();
        hudTable.defaults().pad(PADDING).align(Align.left).growX();
        hudTable.add(makeDrawModeSwitcher()).row();
        hudTable.add(makeSpawnDelaySlider()).row();
        hudTable.add(makeSimulationSpeedSlider()).row();
        hudTable.add(makeVehicleToggleComponent()).row();
        hudTable.add(inspecteeTable).align(Align.center).row();

        hudTable.setBackground(Renderer.uiSkin.getDrawable("window2"));

        table.top().right().pad(PADDING).add(hudTable).width(280).row();
        table.addAction(
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        dropInspectable();
                        return false;
                    }
                });

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
        drawModeSwitcherComponent.defaults().pad(PADDING).growX();

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

        drawModeSwitcherComponent.add(label).left();
        drawModeSwitcherComponent.add(button).right().row();

        return drawModeSwitcherComponent;
    }

    private static Table makeSpawnDelaySlider() {
        Table spawnDelaySliderComponent = new Table();
        spawnDelaySliderComponent.defaults().pad(PADDING).growX();

        Slider slider = new Slider(1, 60, 1, false, Styles.getSliderStyle());
        slider.setValue(Globals.vehicleSpawnDelay);

        Label textLabel = new Label("Độ trễ sinh phương tiện:", Styles.getLabelStyle());
        Label delayLabel = new Label(String.format("%.0fs", Globals.vehicleSpawnDelay), Styles.getLabelStyle());

        spawnDelaySliderComponent.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (actor == slider) {
                            Globals.vehicleSpawnDelay = slider.getValue();
                            delayLabel.setText(String.format("%.0fs", Globals.vehicleSpawnDelay));
                        }
                    }
                });

        spawnDelaySliderComponent.add(textLabel).left();
        spawnDelaySliderComponent.add(delayLabel).right().row();
        spawnDelaySliderComponent.add(slider).colspan(2).row();

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

    @Override
    public void setInspectable(Inspectable inspectee) {
        // IDK why specifically 4 but it works ok
        inspecteeTable.add(inspectee.inspect()).minWidth(hudTable.getWidth() - 2 * PADDING - 4);
        inspecteeTable.setUserObject(inspectee);
    }

    @Override
    public void dropInspectable() {
        Inspectable inspectee = (Inspectable) inspecteeTable.getUserObject();
        if (inspectee == null) {
            return;
        }
        if (!Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
            return;
        }

        List<Group> groups = inspectee.getGroups();
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).isTouchFocusListener()) {
                return;
            }
        }

        Vector2 screenCoordinate =
                inspecteeTable
                        .getStage()
                        .getViewport()
                        .unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        Rectangle hudTableBox =
                new Rectangle(
                        hudTable.getX(),
                        hudTable.getY(),
                        hudTable.getWidth(),
                        hudTable.getHeight());
        if (!hudTableBox.contains(screenCoordinate)) {
            inspectee.dropInspect();
            inspecteeTable.clear();
            inspecteeTable.setUserObject(null);
        }
    }
}
