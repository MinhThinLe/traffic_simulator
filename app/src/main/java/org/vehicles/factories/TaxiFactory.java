package org.vehicles.factories;

import java.util.List;

import org.road.Road;
import org.vehicles.Vehicle;
import org.vehicles.VehicleFactory;
import org.vehicles.vehicles.Taxi;

public class TaxiFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(List<Road> path) {
        return new Taxi(path);
    }
}
