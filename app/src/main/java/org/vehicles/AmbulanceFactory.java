package org.vehicles;

import org.road.Road;

import java.util.ArrayList;

public class AmbulanceFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(ArrayList<Road> path) {
        return new Ambulance(path, path.getFirst().getPosition());
    }
}