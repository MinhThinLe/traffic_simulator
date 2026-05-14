package org.vehicles;

import java.util.ArrayList;

import org.Globals;
import org.road.Road;

public class CivicFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(ArrayList<Road> path) {
        int colorVariants = CivicColor.values().length;
        CivicColor color = CivicColor.values()[Globals.rng.nextInt(colorVariants)];
        return new Civic(path, color);
    }
    
}
