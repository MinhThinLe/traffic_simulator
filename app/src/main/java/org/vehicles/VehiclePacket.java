package org.vehicles;

import org.road.Road;

import com.badlogic.gdx.math.Vector2;

// This class wraps the vehicle class, allowing a node to inform the sender node that its
// vehicle has been accepted into its new location.
public class VehiclePacket implements Comparable<VehiclePacket> {
    public Vehicle vehicle;
    public Road packetSender;

    private static final float TURN_ANGLE_SCALAR = 0.01f;
    @Override
    public int compareTo(VehiclePacket o) {
        int vehicle1Priority = this.vehicle.getVehiclePriority();
        int vehicle2Priority = o.vehicle.getVehiclePriority();

        float vehicle1TurnAngle = this.getTurnAngle() * TURN_ANGLE_SCALAR;
        float vehicle2TurnAngle = o.getTurnAngle() * TURN_ANGLE_SCALAR;

        // Because the vehicle with the lower turn angle gets precedent
        return Float.compare(vehicle1Priority - vehicle1TurnAngle, vehicle2Priority - vehicle2TurnAngle);
    }

    public VehiclePacket(Vehicle vehicle, Road sender) {
        this.vehicle = vehicle;
        this.packetSender = sender;
    }

    private float getTurnAngle() {
        Road start = this.packetSender;
        Road midpoint = this.vehicle.nextDestination();
        Road end = this.vehicle.nextNextDestination();

        if (start == null || midpoint == null || end == null) {
            return 0;
        }

        Vector2 edge1 = packetSender.getPosition().sub(midpoint.getPosition());
        Vector2 edge2 = end.getPosition().sub(midpoint.getPosition());

        return edge1.angleDeg(edge2);
    }
}
