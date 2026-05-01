package org.road;

import com.google.common.graph.MutableGraph;

import org.Globals;
import org.vehicles.Vehicle;
import org.vehicles.VehicleFactory;
import org.vehicles.VehiclePacket;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VehicleManager {
    private MutableGraph<Road> roadNetwork;
    private ArrayList<Road> sources;
    private ArrayList<Road> sinks;
    private ArrayList<VehicleFactory> vehicleFactories;
    private Random rng;

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
        this.rng = new Random();
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

        Road source = sources.get(rng.nextInt(this.sources.size()));
        Road sink = sinks.get(rng.nextInt(this.sinks.size()));
        List<Road> path = PathFinder.breathFirstSearch(roadNetwork, source, sink);

        // Can't find a valid path
        if (path == null) {
            return;
        }

        ArrayList<Road> vehiclePath = new ArrayList<>(path);

        var vehicleFactory = vehicleFactories.get(rng.nextInt(this.vehicleFactories.size()));
        Vehicle vehicle = vehicleFactory.createVehicle(vehiclePath);

        VehiclePacket vehiclePacket = new VehiclePacket(vehicle, null);
        vehicle.nextDestination().addVehicle(vehiclePacket);
    }
}

class Timer {
    private float duration;
    private float remaining;

    public Timer(float duration) {
        this.duration = duration;
        this.remaining = duration;
    }

    public void tick(float time) {
        this.remaining -= time;
    }

    public boolean hasFinished() {
        if (this.remaining < 0) {
            this.remaining = this.duration;
            return true;
        }
        return false;
    }

    public float getDuration() {
        return this.duration;
    }

    public void setDuration(float newDuration) {
        duration = newDuration;

        if (remaining > duration) {
            remaining = duration;
        }
    }

    public float getTimeRemaining() {
        return this.remaining;
    }

    public void reset() {
        remaining = duration;
    }
}
