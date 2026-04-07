package org.vehicles;

import org.Road;

// TODO: Add classes that implement this interface
public interface Vehicle {
    public Road nextDestination();
    public Road nexNextDestination();

    public int getVehiclePriority();
    public void draw();
}
