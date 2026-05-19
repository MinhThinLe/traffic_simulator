package org.vehicles.factories;

import org.Globals;
import org.road.Road;
import org.vehicles.Vehicle;
import org.vehicles.VehicleFactory;
import org.vehicles.vehicles.SUV;
import org.vehicles.vehicles.SUVColor;

import java.util.List;

public class SUVFactory implements VehicleFactory {
    private static final String TEXTURE_PATH = "org/vehicles/textures/suvs/black.png";
    private static final int SPRITE_SIZE = 100;

    @Override
    public Vehicle createVehicle(List<Road> path) {
        int colorVariants = SUVColor.values().length;
        SUVColor color = SUVColor.values()[Globals.rng.nextInt(colorVariants)];
        return new SUV(path, color);
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
