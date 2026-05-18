package org.vehicles;

import org.road.Road;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.List;

public interface VehicleFactory {
    public Vehicle createVehicle(List<Road> path);
    public Sprite getThumbnail();
}
