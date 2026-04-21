package org.vehicles;

import org.road.Road;

// This class wraps the vehicle class, allowing a node to inform the sender node that its
// vehicle has been accepted into its new location.
public class VehiclePacket implements Comparable<VehiclePacket> {
    public Vehicle vehicle;
    public Road packetSender;

    @Override
    public int compareTo(VehiclePacket o) {
        return Integer.compare(this.vehicle.getVehiclePriority(), o.vehicle.getVehiclePriority());
    }

    public VehiclePacket(Vehicle vehicle, Road sender) {
        this.vehicle = vehicle;
        this.packetSender = sender;
    }
}
