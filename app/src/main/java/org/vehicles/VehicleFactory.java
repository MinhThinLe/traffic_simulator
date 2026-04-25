package org.vehicles;

import org.road.Road;

import java.util.ArrayList;

public interface VehicleFactory {
    public Vehicle createVehicle(ArrayList<Road> path);
}
