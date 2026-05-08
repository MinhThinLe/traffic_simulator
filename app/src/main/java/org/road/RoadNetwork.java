package org.road;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.google.common.collect.Sets;
import com.google.common.graph.MutableGraph;

import org.Globals;
import org.render.*;
import org.render.drawcalls.LineDrawCall;
import org.vehicles.VehicleFactory;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;

public class RoadNetwork {
    private static final float DEFAULT_TIMER = 10;

    private MutableGraph<Road> roadGraph;
    private VehicleManager vehicleManager;
    private ArrayList<TrafficLight> trafficLights;

    public RoadNetwork(
            MutableGraph<Road> roadGraph, ArrayList<Road> sources, ArrayList<Road> sinks) {
        this.roadGraph = roadGraph;
        this.vehicleManager = new VehicleManager(roadGraph, sources, sinks, DEFAULT_TIMER);
        this.trafficLights = new ArrayList<>();
    }

    public void addVehicleFactory(VehicleFactory vehicleFactory) {
        vehicleManager.addVehicleFactory(vehicleFactory);
    }

    public void setTrafficLightArray(ArrayList<TrafficLight> trafficLights) {
        this.trafficLights = trafficLights;
    }

    public void drawNodes() {
        var nodes = roadGraph.nodes().iterator();

        while (nodes.hasNext()) {
            nodes.next().draw();
        }

        for (int i = 0; i < this.trafficLights.size(); i++) {
            this.trafficLights.get(i).draw();
        }
    }

    public void drawEdges() {
        switch (Globals.drawMode) {
            case DrawMode.PRIMITIVE:
                drawEdgesPrimitive();
                break;
            case DrawMode.GRAPHICAL:
                drawEdgesGraphical();
                break;
            default:
                break;
        }
    }

    private void drawEdgesPrimitive() {
        var edges = roadGraph.edges().iterator();

        while (edges.hasNext()) {
            var currentEdge = edges.next();

            Vector2 from = currentEdge.nodeU().getPosition();
            Vector2 to = currentEdge.nodeV().getPosition();

            Vector2 direction = new Vector2(to).sub(from).nor().setLength(20);

            from.add(direction);
            to.sub(direction);

            LineDrawCall line = new LineDrawCall(from, to, Color.BLACK, ShapeType.Line);
            Renderer.addPrimitiveDrawCall(line);
        }
    }

    private void drawEdgesGraphical() {
        var nodes = roadGraph.nodes().iterator();
        while (nodes.hasNext()) {
            var currentNode = nodes.next();

            Set<Road> ingressNodes = roadGraph.predecessors(currentNode);
            Set<Road> egressNodes = roadGraph.successors(currentNode);

            Set<List<Road>> roadPairs = Sets.cartesianProduct(ingressNodes, egressNodes);
            var road = roadPairs.iterator();
            while (road.hasNext()) {
                var currentPair = road.next();
                drawRoad(currentPair.get(0).getPosition(), currentNode.getPosition(), currentPair.get(1).getPosition());
            }
        }
    }
    
    private static final float ROAD_WIDTH = 15;
    private static final float EXTRA_WIDTH = 2.5f; // Adding this to ROAD_WIDTH makes the curves match each other for some reason
    private static final int POINTS = 50;
    private void drawRoad(Vector2 start, Vector2 middle, Vector2 end) {
        start.lerp(middle, 0.5f);
        end.lerp(middle, 0.5f);

        Vector2 firstSegmentEnd = new Vector2(middle).add(new Vector2(start).sub(middle).setLength(Road.RADIUS));
        Vector2 lastSegmentStart = new Vector2(middle).add(new Vector2(end).sub(middle).setLength(Road.RADIUS));

        LineDrawCall startLineDrawCall = new LineDrawCall(start, firstSegmentEnd, ROAD_WIDTH, Color.GRAY, ShapeType.Filled);
        LineDrawCall endLineDrawCall = new LineDrawCall(lastSegmentStart, end, ROAD_WIDTH, Color.GRAY, ShapeType.Filled);

        Renderer.addPrimitiveDrawCall(startLineDrawCall);
        Renderer.addPrimitiveDrawCall(endLineDrawCall);
        
        QuadraticBerzier berzier = new QuadraticBerzier(List.of(start, middle, end));
        
        for (int i = 0; i < POINTS; i++) {
            Vector2 current = berzier.interpolate((float) i / POINTS);
            Vector2 next = berzier.interpolate((float) (i + 2) / POINTS);

            LineDrawCall curvedLineDrawCall = new LineDrawCall(current, next, ROAD_WIDTH + EXTRA_WIDTH, Color.GRAY, ShapeType.Filled);
            Renderer.addPrimitiveDrawCall(curvedLineDrawCall);
        }
    }

    public void circulateTraffic(float deltaTime) {
        vehicleManager.tick(deltaTime);

        for (int i = 0; i < trafficLights.size(); i++) {
            trafficLights.get(i).tick(deltaTime);
        }

        var nodes = roadGraph.nodes().iterator();
        while (nodes.hasNext()) {
            nodes.next().circulate(deltaTime);
        }
    }
}
