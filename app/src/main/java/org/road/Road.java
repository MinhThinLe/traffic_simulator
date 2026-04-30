package org.road;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import org.render.*;
import org.vehicles.*;

import java.util.PriorityQueue;

public class Road {
    public static final float RADIUS = 20;
    private static final float TOLERANCE = 5;

    private PriorityQueue<VehiclePacket> priorityQueue;
    private Vehicle vehicle;
    private Vector2 position;
    private boolean sentVehicle;
    private boolean moveToCenter;

    private int id;
    private NodeType nodeType;

    public Road(float x, float y, NodeType nodeType, int id) {
        this.priorityQueue = new PriorityQueue<VehiclePacket>();
        this.position = new Vector2(x, y);
        this.nodeType = nodeType;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public NodeType getNodeType() {
        return this.nodeType;
    }

    public void draw() {
        switch (Renderer.drawMode) {
            case DrawMode.GRAPHICAL:
                graphicalDraw();
                break;

            case DrawMode.PRIMITIVE:
                primitiveDraw();
                break;

            default:
                break;
        }
        if (vehicle != null) {
            vehicle.draw();
        }
    }

    public void graphicalDraw() {
        // TODO: Implement graphical draw
    }

    public void primitiveDraw() {
        ShapeRenderer renderer = Renderer.primitiveRenderer;

        renderer.setColor(Color.BLACK);
        renderer.circle(position.x, position.y, RADIUS);
    }

    public Vector2 getPosition() {
        return new Vector2(position);
    }

    public void circulate(float deltaTime) {
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
        if (vehiclePosition.dst(vehicleDestination) > RADIUS + vehicle.getSize()) {
            routeVehicle(deltaTime, vehicleDestination);
            return;
        }

        // Make a request to send the vehicle to its next node
        if (!sentVehicle) {
            Road nextNode = this.vehicle.nextDestination();
            nextNode.addVehicle(new VehiclePacket(this.vehicle, this));
            sentVehicle = true;
        }
    }

    private void routeVehicle(float deltaTime, Vector2 vehicleDestination) {
        if (this.vehicle.getPosition().dst2(this.position) < TOLERANCE) {
            this.moveToCenter = false;
        }

        if (!this.moveToCenter) {
            this.vehicle.moveToward(vehicleDestination, deltaTime);
            return;
        }

        this.vehicle.moveToward(getPosition(), deltaTime);
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
            this.moveToCenter = true;
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
