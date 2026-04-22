package org.road;

import org.DrawMode;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.google.common.graph.MutableGraph;

public class RoadNetwork {
    private MutableGraph<Road> roadGraph;

    public RoadNetwork(MutableGraph<Road> roadGraph) {
        this.roadGraph = roadGraph;
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
