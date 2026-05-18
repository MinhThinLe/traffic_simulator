package org.vehicles.factories;

import org.Globals;
import org.road.Road;
import org.vehicles.Vehicle;
import org.vehicles.VehicleFactory;
import org.vehicles.vehicles.SuperCar;
import org.vehicles.vehicles.SuperCarColor;

import java.util.List;

public class SuperCarFactory implements VehicleFactory {
    private static final String TEXTURE_PATH = "org/vehicles/textures/super_car/black.png";
    private static final int SPRITE_SIZE = 100;

    @Override
    public Vehicle createVehicle(List<Road> path) {
        int colorVariants = SuperCarColor.values().length;
        SuperCarColor color = SuperCarColor.values()[Globals.rng.nextInt(colorVariants)];
        return new SuperCar(path, color);
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
