package org.vehicles;

import java.util.ArrayList;

import org.Road;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;


public abstract class Vehicle implements Comparable<Vehicle> {
    static int DEFAULT_PRIORITY = 0;
    static float SPEED = 1;
    static float WIDTH = 10;
    static float HEIGHT = 20;

    Vector2 position = Vector2.Zero;
    Vector2 direction;

    ArrayList<Road> path;

    public Vehicle(Vector2 initialPosition) {
        this.position = initialPosition;
    }

    public Road nextDestination() {
        try {
            return this.path.getFirst();
        } catch (Exception e) {
            return null;
        }
    }

    public Road nexNextDestination() {
        try {
            return this.path.get(1);
        } catch (Exception e) {
            return null;
        }
    }

    public int getVehiclePriority() {
        return DEFAULT_PRIORITY;
    }

    public void graphicalDraw() {

    }

    public void popDestination() {
        this.path.removeFirst();
    }

    public void primitiveDraw(ShapeRenderer shapeRenderer, BitmapFont font) {
        Polygon vehicle = new Polygon(new float[] {
            - WIDTH / 2, - HEIGHT / 2,
            + WIDTH / 2, - HEIGHT / 2,
            + WIDTH / 2, + HEIGHT / 2,
            - WIDTH / 2, + HEIGHT / 2
        });

        Vector2 offset = new Vector2(this.direction).rotate90(1).setLength(20);

        vehicle.rotate(offset.angleDeg());
        vehicle.translate(this.position.x, this.position.y);
        vehicle.translate(-offset.x, -offset.y);

        shapeRenderer.polygon(vehicle.getTransformedVertices());
    }

    public void update(Vector2 parentPosition) {
        Vector2 nextPosition = nextDestination().getPosition();

        Vector2 direction = new Vector2(nextPosition).sub(parentPosition).nor();
    
        this.direction = direction;
        this.position.add(new Vector2(direction).scl(SPEED));
    }

    @Override
    public int compareTo(Vehicle otherVehicle) {
        return Integer.compare(this.getVehiclePriority(), otherVehicle.getVehiclePriority());
    }

    public boolean shouldFree() {
        if (nextDestination() == null) {
            return true;
        }
        Vector2 nextPosition = nextDestination().getPosition();

        return (this.position.dst2(nextPosition) - Road.RADIUS < 0.001);
    }
}
