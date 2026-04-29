package org.vehicles;

import com.badlogic.gdx.math.Vector2;

import org.render.*;
import org.road.Road;

import java.util.ArrayList;

public abstract class Vehicle {
    protected ArrayList<Road> path;
    protected Vector2 position;
    protected DrivingMode drivingMode;
    protected float speed;
    protected Vector2 direction;

    public Vehicle(ArrayList<Road> path, Vector2 position, DrivingMode drivingMode, float speed) {
        this.path = path;
        this.position = position;
        this.drivingMode = drivingMode;
        this.speed = speed;
    }

    public Road nextDestination() {
        try {
            return this.path.getFirst();
        } catch (Exception e) {
            return null;
        }
    }

    public Road nextNextDestination() {
        try {
            return this.path.get(1);
        } catch (Exception e) {
            return null;
        }
    }

    public Vector2 getPosition() {
        return new Vector2(this.position);
    }

    public Vector2 getDirection() {
        return new Vector2(this.direction);
    }

    public void moveToward(Vector2 newPosition, float deltaTime) {
        Vector2 direction = newPosition.sub(this.position);
        this.direction = new Vector2(direction);
        this.position.add(direction.setLength(this.speed).scl(deltaTime));
    }

    public final void draw() {
        switch (Renderer.drawMode) {
            case DrawMode.PRIMITIVE:
                primitiveDraw();
                break;
            default:
                graphicalDraw();
                break;
        }
    }

    public final void popDestination() {
        this.path.removeFirst();
    }

    public abstract int getVehiclePriority();

    protected abstract void primitiveDraw();

    protected abstract void graphicalDraw();
}
