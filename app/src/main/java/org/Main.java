package org;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import org.render.*;
import org.render.ui.GameState;
import org.road.*;
import org.vehicles.VehicleFactory;

import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        new Lwjgl3Application(new Game(), Game.getApplicationConfiguration());
    }
}

class Game implements ApplicationListener {
    private RoadNetwork roadNetwork;
    private Camera camera;
    private GameState state;

    static Lwjgl3ApplicationConfiguration getApplicationConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Traffic Simulator");
        configuration.useVsync(true);
        configuration.setForegroundFPS(
                Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        configuration.setWindowedMode(1280, 720);

        return configuration;
    }

    @Override
    public void resize(int width, int height) {
        Renderer.resize(width, height);
        camera.resize(width, height);
    }

    @Override
    public void resume() {}

    @Override
    public void dispose() {}

    @Override
    public void render() {
        tick();
        draw();
    }

    @Override
    public void pause() {}

    @Override
    public void create() {
        this.camera = new Camera();

        Gdx.input.setInputProcessor(Globals.inputMultiplexer);
        addListener();
    }

    private void addListener() {
        ChangeListener changeListener =
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (actor.getName() == "start button") {
                            setState(GameState.LEVEL_SELECTION);
                            return;
                        }
                        if (actor.getName() == "level selection button") {
                            loadMap((String) actor.getUserObject());
                            setState(GameState.NORMAL);
                            return;
                        }
                        if (actor.getName() == "unpause button") {
                            setState(GameState.NORMAL);
                            return;
                        }
                        if (actor.getName() == "title screen button") {
                            setState(GameState.MAIN_MENU);
                            Globals.simulationSpeed = Globals.DEFAULT_SIMULATION_SPEED;
                            Globals.vehicleSpawnDelay = Globals.DEFAULT_VEHICLE_SPAWN_DELAY;
                            return;
                        }
                        if (actor.getName() == "vehicle factory checkbox") {
                            CheckBox checkBox = (CheckBox) actor;
                            VehicleFactory vehicleFactory = (VehicleFactory) checkBox.getUserObject();
                            if (checkBox.isChecked()) {
                                roadNetwork.addVehicleFactory(vehicleFactory);
                                return;
                            }
                            roadNetwork.removeVehicleFactory(vehicleFactory);
                            return;
                        }

                        if (actor.getName() != null) {
                            System.err.println(
                                    "Warning: Unhandled event from actor with name: "
                                            + actor.getName());
                        }
                    }
                };

        Globals.stage.addListener(changeListener);

        InputAdapter inputAdapter =
                new InputAdapter() {
                    @Override
                    public boolean keyDown(int keycode) {
                        if (keycode == Keys.ESCAPE && state == GameState.NORMAL) {
                            setState(GameState.PAUSED);
                            return true;
                        }
                        if (keycode == Keys.ESCAPE && state == GameState.PAUSED) {
                            setState(GameState.NORMAL);
                            return true;
                        }

                        return false;
                    }
                };

        Globals.inputMultiplexer.addProcessor(inputAdapter);
    }

    private void loadMap(String mapName) {
        InputStream resource = Road.class.getResourceAsStream(mapName);
        roadNetwork = RoadNetworkLoader.readFromStream(resource);

        Globals.VEHICLE_FACTORIES.forEach(vehicleFactory -> roadNetwork.addVehicleFactory(vehicleFactory));
    }

    private void setState(GameState state) {
        this.state = state;
        Renderer.resetUI(state);
    }

    private void draw() {
        if (state == GameState.NORMAL || state == GameState.PAUSED) {
            roadNetwork.drawNodes();
            roadNetwork.drawEdges();
        }

        Renderer.render(camera.getCameraProjection());
    }

    private void tick() {
        if (state != GameState.NORMAL) {
            return;
        }

        float deltaTime = Gdx.graphics.getDeltaTime() * Globals.simulationSpeed;
        Renderer.processUI(deltaTime);
        camera.update();
        roadNetwork.circulateTraffic(deltaTime);
    }
}
