package org.vehicles;

import java.util.ArrayList;

import org.Road;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;


public abstract class Vehicle implements Comparable<Vehicle> {
    static int DEFAULT_PRIORITY = 0;
    static float SPEED = 10;
    static float WIDTH = 10;
    static float HEIGHT = 20;

    Vector2 position;
    Vector2 direction;

    ArrayList<Road> path;

    public Road nextDestination() {
        return this.path.getFirst();
    }

    public Road nexNextDestination() {
        return this.path.get(1);
    }

    public int getVehiclePriority() {
        return DEFAULT_PRIORITY;
    }

    public void graphicalDraw() {

    }

    public void primitiveDraw(ShapeRenderer shapeRenderer, BitmapFont font) {
        Polygon vehicle = new Polygon(new float[] {
            position.x - WIDTH / 2, position.y - HEIGHT / 2,
            position.x + WIDTH / 2, position.y - HEIGHT / 2,
            position.x + WIDTH / 2, position.y + HEIGHT / 2,
            position.x - WIDTH / 2, position.y + HEIGHT / 2
        });
        vehicle.rotate(this.direction.angleDeg());
        shapeRenderer.rect(position.x, position.y, WIDTH, HEIGHT);
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
}
