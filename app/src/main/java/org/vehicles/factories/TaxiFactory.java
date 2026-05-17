package org.vehicles.factories;

import org.road.Road;
import org.vehicles.Vehicle;
import org.vehicles.VehicleFactory;
import org.vehicles.vehicles.Taxi;

import java.util.List;

public class TaxiFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(List<Road> path) {
        return new Taxi(path);
    }
}
