package org.vehicles.factories;

import java.util.List;

import org.Globals;
import org.road.Road;
import org.vehicles.Vehicle;
import org.vehicles.VehicleFactory;
import org.vehicles.vehicles.Minivan;
import org.vehicles.vehicles.MinivanColor;

public class MinivanFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(List<Road> path) {
        int colorVariants = MinivanColor.values().length;
        MinivanColor color = MinivanColor.values()[Globals.rng.nextInt(colorVariants)];

        return new Minivan(path, color);
    }
    
}
