package org.road;

import java.util.ArrayList;

import org.Globals;
import org.render.*;

import com.google.common.graph.MutableGraph;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

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
        switch (Globals.drawMode) {
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

        drawBody(location, offset.angleDeg());
    
        location.add(new Vector2(0, HEIGHT / 4).rotateDeg(offset.angleDeg()));

        drawContent(location, edge.source(), offset.angleDeg());
    }

    private void drawBody(Vector2 location, float rotation) {
        Polygon polygon = new Polygon(polygonMesh);
        polygon.rotate(rotation);
        polygon.translate(location.x, location.y);

        ShapeRenderer shapeRenderer = Renderer.primitiveRenderer;
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.polygon(polygon.getTransformedVertices());
    }

    private void drawContent(Vector2 location, Road sourceEdge, float angle) {
        drawLight(location, sourceEdge);
        location.sub(new Vector2(0, HEIGHT / 2).rotateDeg(angle));
        drawCounter(location, sourceEdge, angle);
    }

    private void drawLight(Vector2 location, Road sourceEdge) {
        ShapeRenderer filledRenderer = Renderer.filledPrimitiveRenderer;
        if (isPermittedNode(sourceEdge)) {
            filledRenderer.setColor(Color.GREEN);
        } else {
            filledRenderer.setColor(Color.RED);
        }
        filledRenderer.circle(location.x, location.y, WIDTH * 0.45f);
        filledRenderer.setAutoShapeType(true);
    }

    private void drawCounter(Vector2 location, Road sourceEdge, float angle) {
        LabelStyle labelStyle = new LabelStyle(Renderer.textRenderer, Color.BLACK);
        if (this.type == TrafficLightType.NO_COUNT_DOWN) {
            return;
        }
        if (this.type == TrafficLightType.LAST_TEN_SECONDS && Math.ceil(getRemainingTime(sourceEdge)) > 10) {
            return;
        }

        Label label = new Label((int) Math.ceil(getRemainingTime(sourceEdge)) + "", labelStyle);

        Container<Label> container = new Container<Label>(label);

        container.setTransform(true);
        container.setPosition(location.x, location.y);
        container.setRotation(angle);
        
        container.draw(Renderer.graphicalRenderer, 1);
    }

    private float getRemainingTime(Road ingressNode) {
        if (isPermittedNode(ingressNode)) {
            return this.timer.getTimeRemaining();
        }

        int nodeIndex = getIngressNodeIndex(ingressNode);
        if (nodeIndex > permittedNodeIndex) {
            return this.timer.getTimeRemaining() + timer.getDuration() * (nodeIndex - permittedNodeIndex - 1);
        }

        int untilLoopAround = this.ingressNodes.size() - nodeIndex - 1;
        return this.timer.getTimeRemaining() + this.timer.getDuration() * (nodeIndex + untilLoopAround - 1);
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
        if (isMember(node)) {
            return true;
        }
        return this.ingressNodes.get(this.permittedNodeIndex).source() == node;
    }

    // This is actually prefferable to using ArrayList.contains() since this relies
    // on pointer comparison rather than the .equals() method, which can get more
    // expensive the larger the class is.
    private boolean isMember(Road node) {
        for (int i = 0; i < this.memberNodes.size(); i++) {
            if (this.memberNodes.get(i) == node) {
                return true;
            }
        }

        return false;
    }

    private int getIngressNodeIndex(Road ingressNode) {
        for (int i = 0; i < ingressNodes.size(); i++) {
            if (ingressNodes.get(i).source() == ingressNode) {
                return i;
            }
        }

        return -1;
    }
}
