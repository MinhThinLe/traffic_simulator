package org.road;

import com.google.common.graph.MutableGraph;

import org.Globals;
import org.utils.Timer;
import org.vehicles.Vehicle;
import org.vehicles.VehicleFactory;
import org.vehicles.VehiclePacket;

import java.util.ArrayList;
import java.util.List;

public class VehicleManager {
    private MutableGraph<Road> roadNetwork;
    private List<Road> sources;
    private List<Road> sinks;
    private List<VehicleFactory> vehicleFactories;

    private Timer timer;

    public VehicleManager(
            MutableGraph<Road> roadGraph, List<Road> sources, List<Road> sinks, float timer) {
        this.roadNetwork = roadGraph;
        this.sources = sources;
        this.sinks = sinks;
        this.timer = new Timer(timer);
        this.vehicleFactories = new ArrayList<>();
    }

    public void tick(float deltaTime) {
        if (Globals.vehicleSpawnDelay != this.timer.getDuration()) {
            this.timer.setDuration(Globals.vehicleSpawnDelay);
        }
        this.timer.tick(deltaTime);
        if (!this.timer.hasFinished()) {
            return;
        }

        spawnVehicle();
    }

    public void addVehicleFactory(VehicleFactory vehicleFactory) {
        if (this.vehicleFactories.contains(vehicleFactory)) {
            System.err.println("Warning: vehicle factory already exists, skipping");
            return;
        }
        this.vehicleFactories.add(vehicleFactory);
    }

    public void removeVehicleFactory(VehicleFactory vehicleFactory) {
        if (!this.vehicleFactories.contains(vehicleFactory)) {
            System.err.println("Warning: vehicle factory doesn't exist, skipping");
            return;
        }
        this.vehicleFactories.remove(vehicleFactory);
    }

    private void spawnVehicle() {
        if (this.vehicleFactories.isEmpty() || this.sinks.isEmpty() || this.sources.isEmpty()) {
            return;
        }

        Road source = sources.get(Globals.rng.nextInt(this.sources.size()));
        Road sink = sinks.get(Globals.rng.nextInt(this.sinks.size()));
        List<Road> path = PathFinder.breathFirstSearch(roadNetwork, source, sink);

        // Can't find a valid path
        if (path == null) {
            return;
        }

        var vehicleFactory =
                vehicleFactories.get(Globals.rng.nextInt(this.vehicleFactories.size()));
        Vehicle vehicle = vehicleFactory.createVehicle(path);

        VehiclePacket vehiclePacket = new VehiclePacket(vehicle, null);
        vehicle.nextDestination().addVehicle(vehiclePacket);
    }
}
