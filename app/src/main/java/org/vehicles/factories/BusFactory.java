package org.vehicles.factories;

import org.Globals;
import org.road.Road;
import org.vehicles.*;
import org.vehicles.vehicles.*;

import java.util.List;

public class BusFactory implements VehicleFactory {
    private static final String TEXTURE_PATH = "org/vehicles/textures/buses/black.png";
    private static final int SPRITE_SIZE = 210;

    @Override
    public Vehicle createVehicle(List<Road> path) {
        int colorvariants = BusColor.values().length;
        BusColor color = BusColor.values()[Globals.rng.nextInt(colorvariants)];
        return new Bus(path, color);
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
