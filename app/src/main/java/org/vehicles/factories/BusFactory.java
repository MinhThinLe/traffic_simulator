package org.vehicles.factories;

import org.Globals;
import org.road.Road;
import org.vehicles.*;
import org.vehicles.vehicles.*;

import java.util.List;

public class BusFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(List<Road> path) {
        int colorvariants = BusColor.values().length;
        BusColor color = BusColor.values()[Globals.rng.nextInt(colorvariants)];
        return new Bus(path, color);
    }
}
