package org;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import org.render.*;
import org.render.ui.GameState;
import org.road.*;
import org.vehicles.factories.*;

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
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (actor.getName() == "start button") {
                    state = GameState.LEVEL_SELECTION;
                    Renderer.resetUI(state);
                    return;
                }
                if (actor.getName() == "level selection button") {
                    loadMap((String) actor.getUserObject());
                    state = GameState.NORMAL;
                    Renderer.resetUI(state);
                    return;
                }
                if (actor.getName() != null) {
                    System.err.println("Warning: Unhandled event from actor with name: " + actor.getName());
                }
            }
        };

        Globals.stage.addListener(changeListener);
    }

    private void loadMap(String mapName) {
        InputStream resource = Road.class.getResourceAsStream(mapName);
        roadNetwork = RoadNetworkLoader.readFromStream(resource);

        roadNetwork.addVehicleFactory(new AmbulanceFactory());
        roadNetwork.addVehicleFactory(new BusFactory());
        roadNetwork.addVehicleFactory(new CivicFactory());
        roadNetwork.addVehicleFactory(new PoliceCarFactory());
    }

    private void draw() {
        if (state == GameState.NORMAL) {
            roadNetwork.drawNodes();
            roadNetwork.drawEdges();
        }

        Renderer.render(camera.getCameraProjection());
    }

    private void tick() {
        if (state != GameState.NORMAL) {
            return;
        }

        // So that the simulation could be easily sped up later;
        float deltaTime = Gdx.graphics.getDeltaTime();
        camera.update(deltaTime);
        roadNetwork.circulateTraffic(deltaTime);

        Renderer.processUI(deltaTime);
    }
}
