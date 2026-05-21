package org.vehicles;

import com.badlogic.gdx.math.Vector2;

import org.road.Road;

// This class wraps the vehicle class, allowing a node to inform the sender node that its
// vehicle has been accepted into its new location.
public class VehiclePacket implements Comparable<VehiclePacket> {
    public Vehicle vehicle;
    public Road packetSender;

    @Override
    public int compareTo(VehiclePacket o) {
        int vehicle1Priority = this.vehicle.getVehiclePriority();
        int vehicle2Priority = o.vehicle.getVehiclePriority();

        if (vehicle1Priority != vehicle2Priority) {
            return Integer.compare(vehicle2Priority, vehicle1Priority);
        }

        float vehicle1TurnAngle = this.getTurnAngle();
        float vehicle2TurnAngle = o.getTurnAngle();

        return Float.compare(vehicle2TurnAngle, vehicle1TurnAngle);
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
