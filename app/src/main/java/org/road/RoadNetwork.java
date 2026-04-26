package org.road;

import java.util.ArrayList;

import org.render.DrawMode;
import org.vehicles.VehicleFactory;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.google.common.graph.MutableGraph;

public class RoadNetwork {
    private static final float DEFAULT_TIMER = 10;

    private MutableGraph<Road> roadGraph;
    private VehicleManager vehicleManager;

    public RoadNetwork(MutableGraph<Road> roadGraph, ArrayList<Road> sources, ArrayList<Road> sinks) {
        this.roadGraph = roadGraph;
        this.vehicleManager = new VehicleManager(roadGraph, sources, sinks, DEFAULT_TIMER);
    }

    public void addVehicleFactory(VehicleFactory vehicleFactory) {
        vehicleManager.addVehicleFactory(vehicleFactory);
    }

    public void drawNodes(DrawMode drawMode, ShapeRenderer shapeRenderer) {
        var nodes = roadGraph.nodes().iterator();

        while (nodes.hasNext()) {
            nodes.next().draw(drawMode, shapeRenderer);
        }
    }

    public void drawEdges(DrawMode drawMode, ShapeRenderer shapeRenderer) {
        var edges = roadGraph.edges().iterator();

        while (edges.hasNext()) {
            var currentEdge = edges.next();

            Vector2 from = currentEdge.nodeU().getPosition();
            Vector2 to = currentEdge.nodeV().getPosition();

            Vector2 direction = new Vector2(to).sub(from).nor().setLength(20);

            from.add(direction);
            to.sub(direction);

            shapeRenderer.line(from, to);
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
