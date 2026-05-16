package org.vehicles.factories;

import org.Globals;
import org.road.Road;
import org.vehicles.*;
import org.vehicles.vehicles.*;

import java.util.List;

public class CivicFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(List<Road> path) {
        int colorVariants = CivicColor.values().length;
        CivicColor color = CivicColor.values()[Globals.rng.nextInt(colorVariants)];
        return new Civic(path, color);
    }
}
