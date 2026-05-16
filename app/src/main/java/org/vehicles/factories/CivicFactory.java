package org.vehicles.factories;

import org.Globals;
import org.road.Road;
import org.vehicles.*;
import org.vehicles.vehicles.*;

import java.util.ArrayList;

public class CivicFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(ArrayList<Road> path) {
        int colorVariants = CivicColor.values().length;
        CivicColor color = CivicColor.values()[Globals.rng.nextInt(colorVariants)];
        return new Civic(path, color);
    }
}
