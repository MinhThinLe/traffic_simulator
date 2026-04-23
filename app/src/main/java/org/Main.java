package org;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.net.URL;

import org.road.Road;
import org.road.RoadNetwork;
import org.road.RoadNetworkLoader;

public class Main {
    public static void main(String[] args) {
        new Lwjgl3Application(new Game(), Game.getApplicationConfiguration());
    }
}

class Game implements ApplicationListener {
    FitViewport viewport;
    private SpriteBatch spriteBatch;
    // private BitmapFont font; // Uncomment this later
    private RoadNetwork roadNetwork;
    private ShapeRenderer shapeRenderer;
    private DrawMode drawMode;
    private Camera camera;

    static Lwjgl3ApplicationConfiguration getApplicationConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Hello World");
        configuration.useVsync(true);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        configuration.setWindowedMode(1280, 720);

        return configuration;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void dispose() {
        
    }

    @Override
    public void render() {
        tick();
        draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void create() {
        viewport = new FitViewport(16, 9);
        spriteBatch = new SpriteBatch();
        // font = new BitmapFont(); // Uncomment this later
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        URL resource = Road.class.getResource("simple.graphml");
        roadNetwork = RoadNetworkLoader.readFromFile(resource.getPath()); 

        OrthographicCamera camera = new OrthographicCamera();
        viewport.setCamera(camera);
        this.camera = new Camera(camera);

        drawMode = DrawMode.PRIMITIVE;
    }

    private void draw() {
        spriteBatch.setProjectionMatrix(camera.getCameraProjection());
        shapeRenderer.setProjectionMatrix(camera.getCameraProjection());

        ScreenUtils.clear(1f, 1f, 1f, 1f);

        spriteBatch.begin();
        shapeRenderer.begin();

        roadNetwork.drawNodes(this.drawMode, shapeRenderer);
        roadNetwork.drawEdges(this.drawMode, shapeRenderer);

        spriteBatch.end();
        shapeRenderer.end();
    }

    private void tick() {
        // So that the simulation could be easily sped up later;
        float deltaTime = Gdx.graphics.getDeltaTime();
        camera.update(deltaTime);
        roadNetwork.circulateTraffic();
    }
}
