package org.vehicles;

import java.util.ArrayList;

import org.road.Road;

public class AmbulanceFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(ArrayList<Road> path) {
        return new Ambulance(path, path.getFirst().getPosition());
    }
}
