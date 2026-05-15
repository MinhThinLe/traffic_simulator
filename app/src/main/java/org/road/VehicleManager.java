package org.road;

import com.google.common.graph.MutableGraph;

import org.Globals;
import org.vehicles.Vehicle;
import org.vehicles.VehicleFactory;
import org.vehicles.VehiclePacket;
import org.utils.Timer;

import java.util.ArrayList;
import java.util.List;

public class VehicleManager {
    private MutableGraph<Road> roadNetwork;
    private ArrayList<Road> sources;
    private ArrayList<Road> sinks;
    private ArrayList<VehicleFactory> vehicleFactories;

    private Timer timer;

    public VehicleManager(
            MutableGraph<Road> roadGraph,
            ArrayList<Road> sources,
            ArrayList<Road> sinks,
            float timer) {
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
        this.vehicleFactories.add(vehicleFactory);
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

        ArrayList<Road> vehiclePath = new ArrayList<>(path);

        var vehicleFactory =
                vehicleFactories.get(Globals.rng.nextInt(this.vehicleFactories.size()));
        Vehicle vehicle = vehicleFactory.createVehicle(vehiclePath);

        VehiclePacket vehiclePacket = new VehiclePacket(vehicle, null);
        vehicle.nextDestination().addVehicle(vehiclePacket);
    }
}
