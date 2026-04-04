package org.vehicles;

import org.Road;

// TODO: Add classes that implement this interface
public interface Vehicles {
    public Road nextDestination();
    public Road nexNextDestination();

    public int getVehiclePriority();
}
