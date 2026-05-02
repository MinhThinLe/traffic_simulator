package org.road;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import org.Globals;
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
    private TrafficLight trafficLight;
    private Vehicle pullOverVehicle;

    private int id;

    public Road(float x, float y, int id) {
        this.priorityQueue = new PriorityQueue<VehiclePacket>();
        this.position = new Vector2(x, y);
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setTrafficLight(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }

    public void draw() {
        switch (Globals.drawMode) {
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
        if (pullOverVehicle != null) {
            pullOverVehicle.draw();
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

        if (vehicle.nextDestination() == null) {
            this.vehicle = null;
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
            sendVehicle();
            return;
        }

        boolean nextNodeIsOccupied = this.vehicle.nextDestination().isOccupied();
        if (!this.vehicle.shouldSendOvertakeRequest() || !nextNodeIsOccupied) {
            return;
        }

        boolean reply = this.vehicle.nextDestination().negotiateOvertake(this.vehicle);
        if (!reply) {
            return;
        }
        removeCurrentVehicle();
    }

    private void sendVehicle() {
        Road nextNode = this.vehicle.nextDestination();
        nextNode.addVehicle(new VehiclePacket(this.vehicle, this));
        sentVehicle = true;
    }

    private static final float ACCELERATE_THRESHOLD = 0.3f;

    private boolean negotiateOvertake(Vehicle vehicle) {
        Vector2 vehicleDestination = this.vehicle.nextDestination().getPosition();
        float distanceFromDestination = this.vehicle.getPosition().dst(vehicleDestination);
        float pathLength = this.getPosition().dst(vehicleDestination);

        if (distanceFromDestination / pathLength > ACCELERATE_THRESHOLD) {
            this.vehicle.accelerate();
            return false;
        }
        if (this.vehicle.getVehiclePriority() >= vehicle.getVehiclePriority()) {
            return false;
        }
        if (!this.vehicle.shouldAcceptOvertakeRequest()) {
            return false;
        }
        if (sentVehicle) {
            return false;
        }
        if (this.pullOverVehicle != null) {
            return false;
        }


        this.pullOverVehicle = this.vehicle;
        vehicle.popDestination();
        this.vehicle = vehicle;
        
        // Since the vehicle has been accepted to its destination already, we should
        // remove it from the queue to prevent further problems
        removeFromQueue();
        return true;
    }

    private void removeFromQueue() {
        var queueItems = priorityQueue.iterator();
        while (queueItems.hasNext()) {
            var queueItem = queueItems.next();
            if (queueItem.vehicle == vehicle) {
                priorityQueue.remove(queueItem);
                return;
            }
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
        if (this.pullOverVehicle != null) {
            this.vehicle = this.pullOverVehicle;
            this.pullOverVehicle = null;
            return;
        }
        VehiclePacket vehiclePacket = this.priorityQueue.peek();
        if (vehiclePacket == null) {
            return;
        }

        if (vehiclePacket.packetSender != null) {
            if (this.trafficLight != null && !this.trafficLight.isPermittedNode(vehiclePacket.packetSender)) {
                return;
            }
            vehiclePacket.packetSender.removeCurrentVehicle();
        }

        vehiclePacket.vehicle.popDestination();
        this.vehicle = vehiclePacket.vehicle;
        this.vehicle.resetSpeedModifier();
        this.moveToCenter = true;

        this.priorityQueue.poll();
    }

    private void removeCurrentVehicle() {
        this.vehicle = null;
        this.sentVehicle = false;
    }

    private boolean isOccupied() {
        return this.vehicle != null;
    }
}
