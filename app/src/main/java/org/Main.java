package org;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.road.RoadNetwork;

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

        roadNetwork = new RoadNetwork();

        drawMode = DrawMode.PRIMITIVE;
    }

    private void draw() {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        spriteBatch.begin();
        shapeRenderer.begin();

        roadNetwork.drawNodes(this.drawMode, shapeRenderer);
        roadNetwork.drawEdges(this.drawMode, shapeRenderer);

        spriteBatch.end();
        shapeRenderer.end();
    }

    private void tick() {
        roadNetwork.circulateTraffic();
    }
}
