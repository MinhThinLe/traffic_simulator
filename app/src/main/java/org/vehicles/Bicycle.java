package org.vehicles;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import org.Road;

public class Bicycle extends Vehicle {
    public Bicycle(ArrayList<Road> path, Vector2 initialPosition) {
        super(initialPosition);
        this.path = path;
    }
}
