package org.vehicles;

import org.road.Road;

import java.util.ArrayList;

public class BicycleFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(ArrayList<Road> path) {
        return new Bicycle(path, path.getFirst().getPosition());
    }
}
