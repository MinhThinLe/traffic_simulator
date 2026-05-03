package org.vehicles;

import org.road.Road;

import java.util.ArrayList;

public class CarFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(ArrayList<Road> path) {
        return new Car(path, path.getFirst().getPosition());
    }
}