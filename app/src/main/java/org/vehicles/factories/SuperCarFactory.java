package org.vehicles.factories;

import java.util.List;

import org.Globals;
import org.road.Road;
import org.vehicles.Vehicle;
import org.vehicles.VehicleFactory;
import org.vehicles.vehicles.SuperCar;
import org.vehicles.vehicles.SuperCarColor;

public class SuperCarFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(List<Road> path) {
        int colorVariants = SuperCarColor.values().length;
        SuperCarColor color = SuperCarColor.values()[Globals.rng.nextInt(colorVariants)];
        return new SuperCar(path, color);
    }
}
