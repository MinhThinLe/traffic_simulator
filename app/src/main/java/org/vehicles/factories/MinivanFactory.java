package org.vehicles.factories;

import org.Globals;
import org.road.Road;
import org.vehicles.Vehicle;
import org.vehicles.VehicleFactory;
import org.vehicles.vehicles.Minivan;
import org.vehicles.vehicles.MinivanColor;

import java.util.List;

public class MinivanFactory implements VehicleFactory {
    private static final String TEXTURE_PATH = "org/vehicles/textures/minivans/white.png";

    @Override
    public Vehicle createVehicle(List<Road> path) {
        int colorVariants = MinivanColor.values().length;
        MinivanColor color = MinivanColor.values()[Globals.rng.nextInt(colorVariants)];

        return new Minivan(path, color);
    }

    @Override
    public String getTexturePath() {
        return TEXTURE_PATH;
    }
}
