package org.vehicles.factories;

import java.util.ArrayList;

import org.road.Road;
import org.vehicles.Vehicle;
import org.vehicles.VehicleFactory;
import org.vehicles.vehicles.PoliceCar;

public class PoliceCarFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(ArrayList<Road> path) {
        return new PoliceCar(path);
    }
    
}
