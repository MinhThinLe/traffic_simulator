package org;

import java.util.PriorityQueue;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import org.vehicles.*;

public class Road {
    private static final int MAX_VEHICLES = 2;
    public static final int RADIUS = 20;

    private PriorityQueue<Vehicle> priorityQueue;
    private ArrayList<Vehicle> vehicles;
    private Vector2 position;
    
    public Road(Vector2 position) {
        this.priorityQueue = new PriorityQueue<Vehicle>(
                (Vehicle vehicle1, Vehicle vehicle2) -> vehicle1.compareTo(vehicle2));
        this.vehicles = new ArrayList<>();
        this.position = position;
    }

    public void draw(DrawMode drawMode, ShapeRenderer shapeRenderer, BitmapFont font) {
        switch (drawMode) {
            case DrawMode.GRAPHICAL:
                graphicalDraw();
                break;

            case DrawMode.PRIMITIVE:
                primitiveDraw(shapeRenderer, font);
                break;

            default:
                break;
        }
    }

    public void graphicalDraw() {
        // TODO: Implement graphical draw
    }

    public void primitiveDraw(ShapeRenderer shapeRenderer, BitmapFont font) {
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(position.x, position.y, RADIUS);

        for (int i = 0; i < vehicles.size(); i++) {
            vehicles.get(i).primitiveDraw(shapeRenderer, font);
        }
    }

    public Vector2 getPosition() {
        return new Vector2(position);
    }

    public void addVehicle(Vehicle vehicle) {
        this.priorityQueue.add(vehicle);
    }

    private void circulateVehicles() {
        if (this.vehicles.size() >= MAX_VEHICLES) {
            return;
        }
        if (this.priorityQueue.isEmpty()) {
            return;
        }

        // TODO: Add more constraints later
        this.vehicles.add(this.priorityQueue.poll());
    }

    public void update() {
        circulateVehicles();

        if (this.priorityQueue.size() != 0) {
            System.out.println("Queue not empty!");
        }

        for (int i = 0; i < vehicles.size(); i++) {
            var currentVehicle = vehicles.get(i);

            if (currentVehicle.shouldFree()) {
                this.vehicles.remove(i);

                if (currentVehicle.nextDestination() == null) {
                    continue;
                }

                // Reroutes the vehicle 
                currentVehicle.nextDestination().addVehicle(currentVehicle);
                currentVehicle.popDestination();

                continue;
            }

            currentVehicle.update(getPosition());
        }
    }
}

