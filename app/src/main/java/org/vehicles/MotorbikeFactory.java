package org.vehicles;

import org.road.Road;

import java.util.ArrayList;

public class MotorbikeFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(ArrayList<Road> path) {
        return new Motorbike(path, path.getFirst().getPosition());
    }
}