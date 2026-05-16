package org.vehicles.factories;

import org.road.Road;
import org.vehicles.*;
import org.vehicles.vehicles.Ambulance;

import java.util.List;

public class AmbulanceFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(List<Road> path) {
        return new Ambulance(path);
    }
}
