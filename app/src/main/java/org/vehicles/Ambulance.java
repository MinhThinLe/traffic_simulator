package org.vehicles;

import com.badlogic.gdx.math.Vector2;
import org.road.Road;

import java.util.List;

public class Ambulance extends Vehicle {
    private static final float DEFAULT_AMBULANCE_SPEED = 100;
    private static final float WIDTH = 30;
    private static final float HEIGHT = 15;

    public Ambulance(List<Road> path, Vector2 position) {
        super(path, position, DrivingMode.AGGRESSIVE, DEFAULT_AMBULANCE_SPEED);
        this.impatientness = 1;
    }

    @Override
    public int getVehiclePriority() {
        return 10;
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
        return "Ambulance";
    }

    @Override
    protected void graphicalDraw() {
        // TODO Auto-generated method stub
        
    }
}
