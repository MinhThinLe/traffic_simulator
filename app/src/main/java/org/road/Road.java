package org.road;

import org.DrawMode;

import java.util.PriorityQueue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import org.vehicles.*;

public class Road {
    private static final int RADIUS = 20;

    private PriorityQueue<VehiclePacket> priorityQueue;
    private Vehicle vehicle;
    private Vector2 position;
    private boolean sentVehicle;
    
    public Road(Vector2 position) {
        this.priorityQueue = new PriorityQueue<VehiclePacket>();
        this.position = position;
    }

    public void draw(DrawMode drawMode, ShapeRenderer shapeRenderer) {
        switch (drawMode) {
            case DrawMode.GRAPHICAL:
                graphicalDraw();
                break;

            case DrawMode.PRIMITIVE:
                primitiveDraw(shapeRenderer);
                break;

            default:
                break;
        }
        if (vehicle != null) {
            vehicle.draw(drawMode, shapeRenderer);
        }
    }

    public void graphicalDraw() {
        // TODO: Implement graphical draw
    }

    public void primitiveDraw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(position.x, position.y, RADIUS);
    }

    public Vector2 getPosition() {
        return new Vector2(position);
    }

    public void circulate() {
        if (vehicle == null) {
            acceptVehicle();
        }
        // Because vehicle could still be null after calling acceptVehicle
        if (vehicle == null) {
            return;
        }

        Vector2 vehiclePosition = this.vehicle.getPosition();
        Vector2 vehicleDestination = this.vehicle.nextDestination().getPosition();
        // This means that the vehicle hasn't reached its destination
        if (vehiclePosition.dst2(vehicleDestination) > RADIUS) {
            this.vehicle.moveToward(vehicleDestination);
            return;
        }

        // Make a request to send the vehicle to its next node
        if (!sentVehicle) {
            Road nextNode = this.vehicle.nextDestination();
            nextNode.addVehicle(new VehiclePacket(this.vehicle, this));
            sentVehicle = true;
        }
    }

    public void addVehicle(VehiclePacket vehiclePacket) {
        this.priorityQueue.add(vehiclePacket);
    }

    private void acceptVehicle() {
        VehiclePacket vehiclePacket = this.priorityQueue.poll();
        if (vehiclePacket == null) {
            return;
        }
        
        vehiclePacket.vehicle.popDestination();
        // Only accepts vehicles that still have somewhere left to go
        if (vehiclePacket.vehicle.nextDestination() != null) {
            this.vehicle = vehiclePacket.vehicle;
        }

        if (vehiclePacket.packetSender != null) {
            vehiclePacket.packetSender.removeCurrentVehicle();
        }
    }

    private void removeCurrentVehicle() {
        this.vehicle = null;
        this.sentVehicle = false;
    }
}
