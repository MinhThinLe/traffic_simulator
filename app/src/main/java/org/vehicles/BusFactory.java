package org.vehicles;

import org.road.Road;

import java.util.ArrayList;

public class BusFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(ArrayList<Road> path) {
        return new Bus(path, path.getFirst().getPosition());
    }
}