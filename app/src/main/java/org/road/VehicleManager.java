package org.road;

import java.util.ArrayList;

import com.google.common.graph.MutableGraph;

import org.vehicles.Vehicle;

public class VehicleManager {
    private MutableGraph<Road> roadNetwork;
    private ArrayList<Road> sources;
    private ArrayList<Road> sinks;
    private ArrayList<Class<? extends Vehicle>> vehicleTypes;

    private Timer timer;
    
    public VehicleManager(MutableGraph<Road> roadGraph, ArrayList<Road> sources, ArrayList<Road> sinks, float timer) {
        this.roadNetwork = roadGraph;
        this.sources = sources;
        this.sinks = sinks;
        this.timer = new Timer(timer);
    }

    public void tick(float deltaTime) {
        this.timer.tick(deltaTime);
        if (!this.timer.hasFinished()) {
            return;
        }

        spawnVehicle();
    }

    private void spawnVehicle() {

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

    public void setDuration(float newDuration) {
        this.duration = newDuration;
    }
}
