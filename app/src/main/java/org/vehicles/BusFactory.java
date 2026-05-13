package org.vehicles;

import org.Globals;
import org.road.Road;

import java.util.ArrayList;

public class BusFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(ArrayList<Road> path) {
        int colorvariants = BusColor.values().length;
        BusColor color = BusColor.values()[Globals.rng.nextInt(colorvariants)];
        return new Bus(path, color);
    }
}
