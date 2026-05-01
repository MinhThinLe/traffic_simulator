package org;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import org.render.*;
import org.road.*;
import org.vehicles.BicycleFactory;

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
        configuration.setTitle("Hello World");
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
        InputStream resource = Road.class.getResourceAsStream("3-way-intersection-traffic-light.graphml");
        roadNetwork = RoadNetworkLoader.readFromStream(resource);
        roadNetwork.addVehicleFactory(new BicycleFactory());

        this.camera = new Camera();

        Gdx.input.setInputProcessor(Globals.inputMultiplexer);

    }

    private void draw() {
        // Because wonky shits happen when you try to wrap this in a method
        Renderer.graphicalRenderer.setProjectionMatrix(camera.getCameraProjection());
        Renderer.primitiveRenderer.setProjectionMatrix(camera.getCameraProjection());
        Renderer.filledPrimitiveRenderer.setProjectionMatrix(camera.getCameraProjection());

        Renderer.startBatch();

        roadNetwork.drawNodes();
        roadNetwork.drawEdges();

        Renderer.endBatch();

        Renderer.drawUI();
    }

    private void tick() {
        // So that the simulation could be easily sped up later;
        float deltaTime = Gdx.graphics.getDeltaTime();
        camera.update(deltaTime);
        roadNetwork.circulateTraffic(deltaTime);

        Renderer.processUI(deltaTime);
    }
}
