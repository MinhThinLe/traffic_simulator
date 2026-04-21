package org.road;

import org.DrawMode;

import java.util.PriorityQueue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import org.vehicles.*;

public class Road {
    private static final int MAX_VEHICLES = 2;
    private static final int RADIUS = 20;
    private PriorityQueue<Vehicles> priorityQueue;
    private Vehicles[] vehicles;
    private Vector2 position;
    
    public Road(Vector2 position) {
        this.priorityQueue = new PriorityQueue<Vehicles>();
        this.vehicles = new Vehicles[MAX_VEHICLES];
        this.position = position;
    }

    public void draw(DrawMode drawMode, ShapeRenderer shapeRenderer) {
        switch (drawMode) {
            case DrawMode.GRAPHICAL:
                graphicalDraw();
                break;

            case DrawMode.PRIMITIVE:
                primitiveDraw(shapeRenderer);
                break;

            default:
                break;
        }
    }

    public void graphicalDraw() {
        // TODO: Implement graphical draw
    }

    public void primitiveDraw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(position.x, position.y, RADIUS);
    }

    public Vector2 getPosition() {
        return new Vector2(position);
    }
}

