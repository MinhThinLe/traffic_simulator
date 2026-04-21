package org.road;

import java.util.ArrayList;

import org.DrawMode;
import org.vehicles.Bicycle;
import org.vehicles.VehiclePacket;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

public class RoadNetwork {
    private MutableGraph<Road> roadGraph;

    public RoadNetwork() {
        roadGraph = GraphBuilder.directed().build();
        Road roadNode1 = new Road(new Vector2(100, 50));
        Road roadNode2 = new Road(new Vector2(200, 100));

        ArrayList<Road> path = new ArrayList<>();
        path.add(roadNode1);
        path.add(roadNode2);
        Bicycle bike = new Bicycle(path, roadNode1.getPosition());

        roadNode1.addVehicle(new VehiclePacket(bike, null));

        roadGraph.addNode(roadNode1);
        roadGraph.addNode(roadNode2);

        roadGraph.putEdge(roadNode1, roadNode2);
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

    public void circulateTraffic() {
        var nodes = roadGraph.nodes().iterator();

        while (nodes.hasNext()) {
            nodes.next().circulate();
        }
    }
}
