package org.vehicles.factories;

import org.Globals;
import org.road.Road;
import org.vehicles.Vehicle;
import org.vehicles.VehicleFactory;
import org.vehicles.vehicles.Sedan;
import org.vehicles.vehicles.SedanColor;

import java.util.List;

public class SedanFactory implements VehicleFactory {
    private static final String TEXTURE_PATH = "org/vehicles/textures/sedans/white.png";

    @Override
    public Vehicle createVehicle(List<Road> path) {
        int colorVariants = SedanColor.values().length;
        SedanColor color = SedanColor.values()[Globals.rng.nextInt(colorVariants)];
        return new Sedan(path, color);
    }

    @Override
    public String getTexturePath() {
        return TEXTURE_PATH;
    }
}
