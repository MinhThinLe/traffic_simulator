package org.vehicles.factories;

import org.road.Road;
import org.vehicles.*;
import org.vehicles.vehicles.Ambulance;

import java.util.List;

public class AmbulanceFactory implements VehicleFactory {
    private static final String TEXTURE_PATH =  "org/vehicles/textures/ambulance/ambulance.png";
    private static final int SPRITE_SIZE = 140;

    @Override
    public Vehicle createVehicle(List<Road> path) {
        return new Ambulance(path);
    }

    @Override
    public String getTexturePath() {
        return TEXTURE_PATH;
    }

    @Override
    public int getSpriteSize() {
        return SPRITE_SIZE;
    }
}
