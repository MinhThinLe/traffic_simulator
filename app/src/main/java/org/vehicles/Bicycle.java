package org.vehicles;

import com.badlogic.gdx.math.Vector2;

import org.road.Road;

import java.util.List;

public class Bicycle extends Vehicle {
    private static final float DEFAULT_BICYCLE_SPEED = 50;
    private static final float WIDTH = 20;
    private static final float HEIGHT = 10;

    public Bicycle(List<Road> path, Vector2 position) {
        super(path, position, DrivingMode.NORMAL, DEFAULT_BICYCLE_SPEED);
    }

    @Override
    public int getVehiclePriority() {
        return 0; // Default priority
    }

    @Override
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public String getVehicleName() {
        return "Bicycle";
    }

    @Override
    protected void graphicalDraw() {
        // TODO Auto-generated method stub
    }
}
