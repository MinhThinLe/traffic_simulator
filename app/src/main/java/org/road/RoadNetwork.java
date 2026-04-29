package org.road;

import com.badlogic.gdx.math.Vector2;
import com.google.common.graph.MutableGraph;

import org.render.*;
import org.vehicles.VehicleFactory;

import java.util.ArrayList;

public class RoadNetwork {
    private static final float DEFAULT_TIMER = 10;

    private MutableGraph<Road> roadGraph;
    private VehicleManager vehicleManager;

    public RoadNetwork(
            MutableGraph<Road> roadGraph, ArrayList<Road> sources, ArrayList<Road> sinks) {
        this.roadGraph = roadGraph;
        this.vehicleManager = new VehicleManager(roadGraph, sources, sinks, DEFAULT_TIMER);
    }

    public void addVehicleFactory(VehicleFactory vehicleFactory) {
        vehicleManager.addVehicleFactory(vehicleFactory);
    }

    public void drawNodes() {
        var nodes = roadGraph.nodes().iterator();

        while (nodes.hasNext()) {
            nodes.next().draw();
        }
    }

    public void drawEdges() {
        var edges = roadGraph.edges().iterator();

        while (edges.hasNext()) {
            var currentEdge = edges.next();

            Vector2 from = currentEdge.nodeU().getPosition();
            Vector2 to = currentEdge.nodeV().getPosition();

            Vector2 direction = new Vector2(to).sub(from).nor().setLength(20);

            from.add(direction);
            to.sub(direction);

            Renderer.primitiveRenderer.line(from, to);
        }
    }

    public void circulateTraffic(float deltaTime) {
        vehicleManager.tick(deltaTime);
        var nodes = roadGraph.nodes().iterator();

        while (nodes.hasNext()) {
            nodes.next().circulate(deltaTime);
        }
    }
}
