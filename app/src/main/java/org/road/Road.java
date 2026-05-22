package org.road;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import org.Globals;
import org.render.*;
import org.render.drawcalls.CircleDrawCall;
import org.utils.*;
import org.vehicles.*;

import java.util.PriorityQueue;
import java.util.List;

public class Road {
    public static final float RADIUS = 25;
    private static final float TOLERANCE = 0.01f;

    private PriorityQueue<VehiclePacket> priorityQueue;
    private Vehicle vehicle;
    private Vector2 position;
    private boolean sentVehicle;
    private BezierPath bezierPath;
    private TrafficLight trafficLight;
    private Vehicle pullOverVehicle;
    private Vector2 pullOverPosition;

    private int id;

    public Road(float x, float y, int id) {
        this.priorityQueue =
                new PriorityQueue<VehiclePacket>(
                        (VehiclePacket packet1, VehiclePacket packet2) ->
                                packet1.compareTo(packet2));
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
        if (Globals.drawMode == DrawMode.PRIMITIVE) {
            primitiveDraw();
        }

        if (vehicle != null) {
            vehicle.draw();
        }
        if (pullOverVehicle != null) {
            pullOverVehicle.draw();
        }
    }

    public void primitiveDraw() {
        new CircleDrawCall(position.x, position.y, RADIUS, ShapeType.Line, Color.BLACK).submit();
    }

    public Vector2 getPosition() {
        return new Vector2(position);
    }

    private boolean hasVehicleReachedDestination() {
        Vector2 vehiclePosition = this.vehicle.getPosition();
        Vector2 vehicleDestination = this.vehicle.nextDestination().getPosition();

        if (bezierPath != null) {
            return bezierPath.hasFinished();
        }

        return vehiclePosition.dst(vehicleDestination) < RADIUS + this.vehicle.getWidth() / 2;
    }

    public void circulate(float deltaTime) {
        if (vehicle == null) {
            acceptVehicle();
            return;
        }
        if (pullOverVehicle != null) {
            routePulledOverVehicle(deltaTime);
        }

        if (vehicle.nextDestination() == null) {
            this.vehicle = null;
            return;
        }

        if (!hasVehicleReachedDestination() || bezierPath != null) {
            routeVehicle(deltaTime);
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

        AudioPlayer.playHonk();

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

    private boolean negotiateOvertake(Vehicle vehicle) {
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
        if (this.getPosition().dst(this.vehicle.getPosition()) < STRAFE_LENGTH) {
            return false;
        }

        Vector2 vehicleDestination = this.vehicle.nextDestination().getPosition();
        float distanceToCover = this.vehicle.getPosition().dst(vehicleDestination);

        if (distanceToCover < STRAFE_LENGTH) {
            return false;
        }

        setupPulloverPosition();

        this.pullOverVehicle = this.vehicle;
        this.pullOverVehicle.increaseStinginess();

        this.vehicle = vehicle;
        this.vehicle.resetTimer();
        this.vehicle.popDestination();
        this.bezierPath = new BezierPath(this, vehicle.getSpeed());

        // Since the vehicle has been accepted to its destination already, we should
        // remove it from the queue to prevent further problems
        removeFromQueue();
        return true;
    }

    private static final float MINIMUM_DISTANCE = 20;
    private static final float STRAFE_LENGTH = 40;
    private static final float STRAFE_ANGLE = -45;

    private void setupPulloverPosition() {
        Vector2 relativeVehiclePosition = this.vehicle.getPosition().sub(this.getPosition());
        Vector2 relativeDestinationPosition = this.vehicle.nextDestination().getPosition().sub(this.getPosition());

        float distanceFromMainTrack =
                relativeVehiclePosition.len()
                        * (float)
                                Math.sin(
                                        relativeVehiclePosition.angleRad(
                                                relativeDestinationPosition));

        Vector2 pullOverOffset = new Vector2(relativeDestinationPosition).rotateDeg(STRAFE_ANGLE).setLength(STRAFE_LENGTH);
        if (Math.abs(distanceFromMainTrack) > MINIMUM_DISTANCE) {
            pullOverOffset.setLength(STRAFE_LENGTH - distanceFromMainTrack);
        }

        this.pullOverPosition = this.vehicle.getPosition().add(pullOverOffset);
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

    private void routeVehicle(float deltaTime) {
        if (bezierPath != null) {
            this.vehicle.moveToward(bezierPath.nextPoint(vehicle.getSpeed() * deltaTime), deltaTime);
            if (bezierPath.hasFinished()) {
                this.bezierPath = null;
            }
            return;
        }

        this.vehicle.moveToward(vehicle.nextDestination().getPosition(), deltaTime);

    }

    private void routePulledOverVehicle(float deltaTime) {
        if (this.pullOverVehicle.getPosition().dst(pullOverPosition) > TOLERANCE) {
            this.pullOverVehicle.moveToward(new Vector2(pullOverPosition), deltaTime);
        }
    }

    public void addVehicle(VehiclePacket vehiclePacket) {
        this.priorityQueue.add(vehiclePacket);
    }

    private void acceptVehicle() {
        if (this.pullOverVehicle != null) {
            this.vehicle = this.pullOverVehicle;
            this.pullOverVehicle = null;
            this.pullOverPosition = null;
            return;
        }
        VehiclePacket vehiclePacket = this.priorityQueue.peek();
        if (vehiclePacket == null) {
            return;
        }

        if (vehiclePacket.packetSender != null) {
            if (this.trafficLight != null
                    && !this.trafficLight.isPermittedNode(vehiclePacket.packetSender)
                    && !vehiclePacket.vehicle.shouldRunRedLight()) {
                return;
            }
            vehiclePacket.packetSender.removeCurrentVehicle();
        }

        this.vehicle = vehiclePacket.vehicle;
        this.vehicle.popDestination();
        this.bezierPath = new BezierPath(this, vehicle.getSpeed());

        this.priorityQueue.poll();
    }

    Vehicle getCurrentVehicle() {
        return this.vehicle;
    }

    private void removeCurrentVehicle() {
        this.vehicle = null;
        this.sentVehicle = false;
    }

    private boolean isOccupied() {
        return this.vehicle != null;
    }
}

class BezierPath {
    private NDegreeBezier path;
    private float pathLength;
    private float travelled;

    BezierPath(Road node, float vehicleSpeed) {
        Vector2 controlPoint1 = node.getCurrentVehicle().getPosition();
        Vector2 controlPoint2 = node.getPosition();
        Vector2 controlPoint3 = node.getPosition().sub(new Vector2(controlPoint1).sub(controlPoint2));
        if (node.getCurrentVehicle().nextDestination() != null) {
            controlPoint3 = node.getCurrentVehicle().nextDestination().getPosition().sub(node.getPosition()).setLength(Road.RADIUS).add(node.getPosition());
        }

        pathLength = controlPoint1.dst(controlPoint2) + controlPoint2.dst(controlPoint3);
        this.path = new NDegreeBezier(List.of(controlPoint1, controlPoint2, controlPoint3));
    }

    boolean hasFinished() {
        return travelled >= pathLength;
    }

    Vector2 nextPoint(float vehicleSpeed) {
        this.travelled += vehicleSpeed;
        if (this.travelled >= this.pathLength) {
            this.travelled = this.pathLength;
        }

        return path.interpolate(travelled / pathLength);
    }
}
