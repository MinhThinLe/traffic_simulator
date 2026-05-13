package org;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import org.render.*;
import org.render.ui.GameState;
import org.road.*;
import org.vehicles.AmbulanceFactory;
import org.vehicles.BusFactory;

import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        new Lwjgl3Application(new Game(), Game.getApplicationConfiguration());
    }
}

class Game implements ApplicationListener {
    private RoadNetwork roadNetwork;
    private Camera camera;

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
    }

    private void reload() {
        InputStream resource = Road.class.getResourceAsStream(Globals.mapName);
        roadNetwork = RoadNetworkLoader.readFromStream(resource);

        roadNetwork.addVehicleFactory(new AmbulanceFactory());
        roadNetwork.addVehicleFactory(new BusFactory());
    }

    private void draw() {
        if (Globals.gameState == GameState.NORMAL) {
            roadNetwork.drawNodes();
            roadNetwork.drawEdges();
        }

        Renderer.render(camera.getCameraProjection());
    }

    private void tick() {
        if (Globals.gameState != GameState.NORMAL) {
            return;
        }
        if (roadNetwork == null) {
            reload();
        }

        // So that the simulation could be easily sped up later;
        float deltaTime = Gdx.graphics.getDeltaTime();
        camera.update(deltaTime);
        roadNetwork.circulateTraffic(deltaTime);

        Renderer.processUI(deltaTime);
    }
}
