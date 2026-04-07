package org;

import java.util.ArrayList;

import org.vehicles.Bicycle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

public class Main {
    public static void main(String[] args) {
        new Lwjgl3Application(new Game(), Game.getApplicationConfiguration());
    }
}

class Game implements ApplicationListener {
    FitViewport viewport;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private MutableGraph<Road> roadGraph;

    private ShapeRenderer shapeRenderer;

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

    private void update() {
        var roadIter = this.roadGraph.nodes().iterator();
        while (roadIter.hasNext()) {
            roadIter.next().update();
        }
    }

    @Override
    public void render() {
        update();
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        spriteBatch.begin();
        shapeRenderer.begin();

        drawNodes();
        drawEdges();

        spriteBatch.end();
        shapeRenderer.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void create() {
        viewport = new FitViewport(16, 9);
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        roadGraph = GraphBuilder.directed().build();

        Road roadNode1 = new Road(new Vector2(100, 50));
        Road roadNode2 = new Road(new Vector2(200, 100));
        Road roadNode3 = new Road(new Vector2(300, 75));

        ArrayList<Road> vehiclePath = new ArrayList<>();

        vehiclePath.add(roadNode3);
        vehiclePath.add(roadNode2);
        vehiclePath.add(roadNode1);
        vehiclePath.add(roadNode3);
        vehiclePath.add(roadNode2);
        vehiclePath.add(roadNode1);
        vehiclePath.add(roadNode3);
        vehiclePath.add(roadNode2);
        vehiclePath.add(roadNode1);
        vehiclePath.add(roadNode3);
        vehiclePath.add(roadNode2);
        vehiclePath.add(roadNode1);
        
        Bicycle bike = new Bicycle(vehiclePath, roadNode1.getPosition());

        roadNode1.addVehicle(bike);

        roadGraph.addNode(roadNode1);
        roadGraph.addNode(roadNode2);
        roadGraph.addNode(roadNode3);

        roadGraph.putEdge(roadNode2, roadNode1);
        roadGraph.putEdge(roadNode1, roadNode3);
        roadGraph.putEdge(roadNode3, roadNode2);
    }

    private void drawNodes() {
        var nodes = roadGraph.nodes().iterator();

        while (nodes.hasNext()) {
            nodes.next().draw(DrawMode.PRIMITIVE, shapeRenderer, font);
        }
    }
    
    private static final int DOMINANT_SIDE = 90; // change to -90 if you want to drive on the left side of the road
    private static final int ROAD_SPACING = 20;

    private void drawEdges() {
        var edges = roadGraph.edges().iterator();

        while (edges.hasNext()) {
            var currentNode = edges.next();
            
            Vector2 from = currentNode.nodeU().getPosition();
            Vector2 to = currentNode.nodeV().getPosition();

            Vector2 direction = new Vector2(from).sub(to).rotateDeg(DOMINANT_SIDE).nor().scl(ROAD_SPACING);

            from.add(direction);
            to.add(direction);

            shapeRenderer.line(from, to);
        }
    }
}
