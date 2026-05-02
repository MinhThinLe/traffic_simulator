package org.vehicles;

import com.badlogic.gdx.math.Vector2;

import org.render.DrawMode;
import org.road.Road;
import org.Globals;

import java.util.ArrayList;

public abstract class Vehicle {
    private static float ACCELERATE_AMOUNT = 0.15f;
    private static final float DEFAULT_SPEED_MODIFIER = 1;

    protected ArrayList<Road> path;
    protected Vector2 position;
    protected DrivingMode drivingMode;
    protected float speed;
    protected Vector2 direction;
    protected float speedModifier;

    protected float impatientness;  // A float ranging from 0 to 1 indicating the chance that this vehicle would
                                    // send an overtake request
    protected float stinginess; // A float ranging from 0 to 1 indicating the chance that this vehicle would
                                // refuse an overtake request

    public Vehicle(ArrayList<Road> path, Vector2 position, DrivingMode drivingMode, float speed) {
        this.path = path;
        this.position = position;
        this.drivingMode = drivingMode;
        this.speed = speed;
        this.speedModifier = DEFAULT_SPEED_MODIFIER;
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

    public float getSpeed() {
        return this.speed * this.speedModifier;
    }

    public void moveToward(Vector2 newPosition, float deltaTime) {
        Vector2 direction = newPosition.sub(this.position);
        this.direction = new Vector2(direction);
        this.position.add(direction.setLength(getSpeed()).scl(deltaTime));
    }

    public final void draw() {
        switch (Globals.drawMode) {
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

    public boolean shouldSendOvertakeRequest() {
        return Globals.rng.nextFloat() < impatientness;
    }

    public boolean shouldAcceptOvertakeRequest() {
        return Globals.rng.nextFloat() > stinginess;
    }

    public void accelerate() {
        this.speedModifier += ACCELERATE_AMOUNT;
    }

    public void resetSpeedModifier() {
        this.speedModifier = DEFAULT_SPEED_MODIFIER;
    }

    public abstract int getVehiclePriority();
    public abstract float getSize();

    protected abstract void primitiveDraw();

    protected abstract void graphicalDraw();
}
