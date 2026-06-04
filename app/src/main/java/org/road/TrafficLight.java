package org.road;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.google.common.graph.MutableGraph;

import org.Globals;
import org.render.*;
import org.render.drawcalls.CircleDrawCall;
import org.render.drawcalls.PolygonDrawCall;
import org.render.drawcalls.WidgetDrawCall;
import org.render.ui.Hud;
import org.render.ui.Inspectable;
import org.render.ui.Styles;
import org.utils.Timer;

import java.util.ArrayList;
import java.util.List;

enum TrafficLightType {
    FULL_COUNT_DOWN,
    NO_COUNT_DOWN,
    LAST_TEN_SECONDS,
    FULLY_MANUAL;

    @Override
    public String toString() {
        return switch (this) {
            case FULL_COUNT_DOWN -> "Đếm giờ đầy đủ";
            case NO_COUNT_DOWN -> "Không đếm giờ";
            case LAST_TEN_SECONDS -> "Đếm 10 giây cuối";
            case FULLY_MANUAL -> "Thủ công";
        };
    }
}

public class TrafficLight implements Inspectable {
    private static final float DEFAULT_TIMER_DURATION = 5;
    private List<Road> memberNodes;
    private List<RoadEdge> ingressNodes;
    private Timer timer;
    private int permittedNodeIndex;
    private TrafficLightType type;
    private int id;
    private List<Group> groups;

    public TrafficLight(int id) {
        this.memberNodes = new ArrayList<>();
        this.ingressNodes = new ArrayList<>();
        this.timer = new Timer(DEFAULT_TIMER_DURATION);
        this.id = id;

        this.groups = new ArrayList<>();
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
        for (int i = 0; i < ingressNodes.size(); i++) {
            drawEdge(ingressNodes.get(i));
        }
    }

    private static final float WIDTH = 20;
    private static final float HEIGHT = 2 * WIDTH;
    private static final float[] POLYGON_MESH =
            new float[] {
                -WIDTH / 2, -HEIGHT / 2,
                WIDTH / 2, -HEIGHT / 2,
                WIDTH / 2, HEIGHT / 2,
                -WIDTH / 2, HEIGHT / 2
            };

    private Polygon getPolygonBody(RoadEdge edge) {
        Vector2 direction =
                edge.source()
                        .getPosition()
                        .sub(edge.target().getPosition())
                        .setLength(Road.RADIUS + HEIGHT / 2);
        Vector2 offset = new Vector2(direction).rotate90(1).setLength(Road.RADIUS + WIDTH / 2);
        Vector2 location = edge.target().getPosition().add(direction).add(offset);

        Polygon polygon = new Polygon(POLYGON_MESH);
        polygon.rotate(offset.angleDeg());
        polygon.translate(location.x, location.y);

        return polygon;
    }

    private void drawEdge(RoadEdge edge) {
        drawBody(edge);
        drawContent(edge);
    }

    private void drawBody(RoadEdge edge) {
        Polygon polygon = getPolygonBody(edge);
        new PolygonDrawCall(polygon, Color.BLACK, ShapeType.Filled).submit();
    }

    private void drawContent(RoadEdge edge) {
        Polygon polygon = getPolygonBody(edge);
        Vector2 location = new Vector2(polygon.getX(), polygon.getY());
        location.add(new Vector2(0, HEIGHT / 4).rotateDeg(polygon.getRotation()));

        drawLight(location, edge.source());
        location.sub(new Vector2(0, HEIGHT / 2).rotateDeg(polygon.getRotation()));
        drawCounter(location, edge.source(), polygon.getRotation());
    }

    private void drawLight(Vector2 location, Road sourceNode) {
        CircleDrawCall drawCall =
                new CircleDrawCall(
                        location.x,
                        location.y,
                        WIDTH * 0.45f,
                        ShapeType.Filled,
                        getColor(sourceNode));
        Renderer.addPrimitiveDrawCall(drawCall);
    }

    private void drawCounter(Vector2 location, Road sourceNode, float angle) {
        if (this.type == TrafficLightType.NO_COUNT_DOWN
                || this.type == TrafficLightType.FULLY_MANUAL) {
            return;
        }
        if (this.type == TrafficLightType.LAST_TEN_SECONDS
                && Math.ceil(getRemainingTime(sourceNode)) > 10) {
            return;
        }

        LabelStyle labelStyle = Styles.getLabelStyle();
        labelStyle.fontColor = getColor(sourceNode);
        Label label =
                new Label(
                        (int) Math.clamp(Math.ceil(getRemainingTime(sourceNode)), 0, 99) + "",
                        labelStyle);

        Container<Label> container = new Container<Label>(label);

        container.setTransform(true);
        container.setPosition(location.x, location.y);
        container.setRotation(angle);

        WidgetDrawCall drawCall = new WidgetDrawCall(container);
        Renderer.addGraphicalDrawCall(drawCall);
    }

    private Color getColor(Road soureNode) {
        Color color = Color.RED;
        if (isPermittedNode(soureNode)) {
            color = Color.GREEN;
        }

        return color;
    }

    private float getRemainingTime(Road ingressNode) {
        if (isPermittedNode(ingressNode)) {
            return this.timer.getTimeRemaining();
        }

        int nodeIndex = getIngressNodeIndex(ingressNode);
        if (nodeIndex > permittedNodeIndex) {
            return this.timer.getTimeRemaining()
                    + this.timer.getDuration() * (nodeIndex - permittedNodeIndex - 1);
        }

        int untilLoopAround = this.ingressNodes.size() - permittedNodeIndex - 1;
        return this.timer.getTimeRemaining()
                + this.timer.getDuration() * (nodeIndex + untilLoopAround);
    }

    @Override
    public List<Group> getGroups() {
        return this.groups;
    }

    @Override
    public Table inspect() {
        Table hudTable = new Table();
        hudTable.setBackground(Renderer.uiSkin.getDrawable("window2"));
        hudTable.defaults().pad(PADDING).growX();

        hudTable.add(createTrafficLightTimerSlider()).row();
        hudTable.add(createTrafficLightDropDownMenu()).row();

        return hudTable;
    }

    @Override
    public void dropInspect() {
        groups.clear();
    }

    public void tick(float deltaTime) {
        if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
            int lightClickedIndex = getJustClickedLight();
            if (lightClickedIndex == -1) {
                return;
            }
            new Hud().setInspectable(this);

            permittedNodeIndex = lightClickedIndex;
            timer.reset();
        }
        if (this.type == TrafficLightType.FULLY_MANUAL) {
            return;
        }
        this.timer.tick(deltaTime);
        if (!timer.hasFinished()) {
            return;
        }

        permittedNodeIndex = (permittedNodeIndex + 1) % ingressNodes.size();
    }

    private static final float PADDING = 5;

    private Table createTrafficLightTimerSlider() {
        Table trafficLightTimer = new Table();
        trafficLightTimer.defaults().growX();

        Slider slider = new Slider(1, 60, 1, false, Styles.getSliderStyle());
        slider.setValue(this.timer.getDuration());

        Label textLabel = new Label("Thời gian đèn đỏ:", Styles.getLabelStyle());
        Label timerLabel = new Label(this.timer.getDuration() + "s", Styles.getLabelStyle());
        timerLabel.setAlignment(Align.right);

        trafficLightTimer.add(textLabel);
        trafficLightTimer.add(timerLabel).row();
        trafficLightTimer.add(slider).colspan(2).row();

        trafficLightTimer.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (actor == slider) {
                            timer.setDuration(slider.getValue());
                            timerLabel.setText(timer.getDuration() + "s");
                        }
                    }
                });

        return trafficLightTimer;
    }

    private Table createTrafficLightDropDownMenu() {
        Table trafficLightDropDownComponent = new Table();
        trafficLightDropDownComponent.defaults().growX();

        SelectBox<TrafficLightType> selectBox = new SelectBox<>(Styles.getSelectBoxStyle());
        selectBox.setItems(TrafficLightType.values());
        selectBox.setSelectedIndex(type.ordinal());

        Label label = new Label("Chế độ đèn đỏ:", Styles.getLabelStyle());

        trafficLightDropDownComponent.add(label).left().row();
        trafficLightDropDownComponent.left().add(selectBox).row();

        trafficLightDropDownComponent.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (actor == selectBox) {
                            type = selectBox.getSelected();
                        }
                    }
                });

        groups.add(selectBox.getScrollPane());

        return trafficLightDropDownComponent;
    }

    private int getJustClickedLight() {
        for (int i = 0; i < ingressNodes.size(); i++) {
            Polygon polygon = getPolygonBody(ingressNodes.get(i));
            if (polygon.contains(Globals.mouseWorldPosition)) {
                return i;
            }
        }

        return -1;
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
