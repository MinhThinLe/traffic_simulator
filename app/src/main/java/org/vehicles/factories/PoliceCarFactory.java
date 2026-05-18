package org.vehicles.factories;

import org.road.Road;
import org.vehicles.Vehicle;
import org.vehicles.VehicleFactory;
import org.vehicles.vehicles.PoliceCar;

import java.util.List;

public class PoliceCarFactory implements VehicleFactory {
    private static final String TEXTURE_PATH = "org/vehicles/textures/police_car/police_car.png";
    private static final int SPRITE_SIZE = 100;

    @Override
    public Vehicle createVehicle(List<Road> path) {
        return new PoliceCar(path);
    }

    @Override
    public int getSpriteSize() {
        return SPRITE_SIZE;
    }

    @Override
    public String getTexturePath() {
        return TEXTURE_PATH;
    }
}
