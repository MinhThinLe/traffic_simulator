package org.vehicles;

import org.road.Road;

import java.util.List;

public interface VehicleFactory {
    public Vehicle createVehicle(List<Road> path);
}
