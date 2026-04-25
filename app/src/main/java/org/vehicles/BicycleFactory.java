package org.vehicles;

import java.util.ArrayList;

import org.road.Road;

public class BicycleFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(ArrayList<Road> path) {
        return new Bicycle(path, path.getFirst().getPosition());
    }
}
