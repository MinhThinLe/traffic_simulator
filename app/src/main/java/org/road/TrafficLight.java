package org.road;

import java.util.ArrayList;

import org.render.*;

import com.google.common.graph.MutableGraph;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

enum TrafficLightType {
    FULL_COUNT_DOWN,
    NO_COUNT_DOWN,
    LAST_TEN_SECONDS
}

public class TrafficLight {
    private static final float DEFAULT_TIMER_DURATION = 5;
    private ArrayList<Road> memberNodes;
    private ArrayList<RoadEdge> ingressNodes;
    private Timer timer;
    private int permittedNodeIndex;
    private TrafficLightType type;
    private int id;

    public TrafficLight(int id) {
        this.memberNodes = new ArrayList<>();
        this.ingressNodes = new ArrayList<>();
        this.timer = new Timer(DEFAULT_TIMER_DURATION);
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setType(int type) {
        this.type = TrafficLightType.values()[type];
    }

    public void setDuration(float newDuration) {
        this.timer.setDuration(newDuration);
    }

    public void addMemberNode(Road memberNode) {
        this.memberNodes.add(memberNode);
    }

    public void draw() {
        switch (Renderer.drawMode) {
            case DrawMode.PRIMITIVE:
                primitiveDraw();               
                break;
            case DrawMode.GRAPHICAL:
                graphicalDraw();
                break;
            default:
                break;
        }
    }

    private void primitiveDraw() {
        for (int i = 0; i < ingressNodes.size(); i++) {
            primitiveDrawEdge(ingressNodes.get(i));
        }
    }
    
    private static final float WIDTH = 15;
    private static final float HEIGHT = 2 * WIDTH;
    private static final float[] polygonMesh = new float[] {
        -WIDTH / 2, -HEIGHT / 2,
        WIDTH / 2, -HEIGHT / 2,
        WIDTH / 2, HEIGHT / 2,
        -WIDTH / 2, HEIGHT / 2
    };
    private void primitiveDrawEdge(RoadEdge edge) {
        Vector2 direction = edge.source().getPosition().sub(edge.target().getPosition()).setLength(Road.RADIUS + HEIGHT / 2);
        Vector2 offset = new Vector2(direction).rotate90(1).setLength(Road.RADIUS + WIDTH / 2);

        Vector2 location = edge.target().getPosition().add(direction).add(offset);

        Polygon polygon = new Polygon(polygonMesh);
        polygon.rotate(offset.angleDeg());
        polygon.translate(location.x, location.y);

        ShapeRenderer shapeRenderer = Renderer.primitiveRenderer;
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.polygon(polygon.getTransformedVertices());
    
        location.add(new Vector2(0, HEIGHT / 4).rotateDeg(offset.angleDeg()));
        ShapeRenderer filledRenderer = Renderer.filledPrimitiveRenderer;
        if (isPermittedNode(edge.source())) {
            filledRenderer.setColor(Color.GREEN);
        } else {
            filledRenderer.setColor(Color.RED);
        }
        filledRenderer.circle(location.x, location.y, WIDTH * 0.45f);
        filledRenderer.setAutoShapeType(true);
    }

    private void graphicalDraw() {

    }

    public void tick(float deltaTime) {
        this.timer.tick(deltaTime);
        if (!timer.hasFinished()) {
            return;
        }

        permittedNodeIndex = (permittedNodeIndex + 1) % ingressNodes.size();
    }

    public void addIngressNodes(MutableGraph<Road> roadGraph) {
        for (int i = 0; i < this.memberNodes.size(); i++) {
            Road currentNode = this.memberNodes.get(i);
            var predecessors = roadGraph.predecessors(currentNode).iterator();
        
            while (predecessors.hasNext()) {
                var predecessor = predecessors.next();

                if (isMember(predecessor)) {
                    continue;
                }

                ingressNodes.add(new RoadEdge(predecessor, currentNode));
            }
        }
    }

    public boolean isPermittedNode(Road node) {
        for (int i = 0; i < memberNodes.size(); i++) {
            if (memberNodes.get(i) == node) {
                return true;
            }
        }
        return this.ingressNodes.get(this.permittedNodeIndex).source() == node;
    }

    private boolean isMember(Road node) {
        for (int i = 0; i < this.memberNodes.size(); i++) {
            if (this.memberNodes.get(i) == node) {
                return true;
            }
        }

        return false;
    }
}
