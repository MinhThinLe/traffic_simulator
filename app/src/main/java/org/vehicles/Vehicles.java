package org.vehicles;

import org.DrawMode;
import org.road.Road;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public abstract class Vehicles {
    private static final int DEFAULT_PRIORITY = 0;

    protected ArrayList<Road> path;
    protected Vector2 position;
    protected DrivingMode drivingMode;
    protected float speed;

    public Road nextDestination() {
        return this.path.getFirst();
    }

    public Road nextNextDestination() {
        return this.path.get(1);
    }

    public int getVehiclePriority() {
        return DEFAULT_PRIORITY;
    }

    public Vector2 getPosition() {
        return new Vector2(this.position);
    }

    public void moveToward(Vector2 newPosition) {
        Vector2 direction = newPosition.sub(this.position);
        this.position.add(direction.setLength(this.speed));
    }

    public void draw(DrawMode drawMode) {
        switch (drawMode) {
            case DrawMode.PRIMITIVE:
                primitiveDraw();
                break;
            default:
                graphicalDraw();
                break;
        }
    }

    private void primitiveDraw() {

    }

    private void graphicalDraw() {

    }
}
