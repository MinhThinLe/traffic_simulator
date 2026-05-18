package org.vehicles.factories;

import org.Globals;
import org.road.Road;
import org.vehicles.*;
import org.vehicles.vehicles.*;

import java.util.List;

public class CivicFactory implements VehicleFactory {
    private static final String TEXTURE_PATH = "org/vehicles/textures/civics/black.png";
    private static final int SPRITE_SIZE = 100;

    @Override
    public Vehicle createVehicle(List<Road> path) {
        int colorVariants = CivicColor.values().length;
        CivicColor color = CivicColor.values()[Globals.rng.nextInt(colorVariants)];
        return new Civic(path, color);
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
