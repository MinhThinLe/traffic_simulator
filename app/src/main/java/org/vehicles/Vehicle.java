package org.vehicles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import org.Globals;
import org.render.DrawMode;
import org.render.Renderer;
import org.render.drawcalls.PolygonDrawCall;
import org.render.drawcalls.WidgetDrawCall;
import org.road.Road;

import java.util.List;

public abstract class Vehicle {
    protected List<Road> path;
    protected Vector2 position;
    protected DrivingMode drivingMode;
    protected float speed;
    protected Vector2 direction;
    // A float ranging from 0 to 1 indicating the chance that this vehicle
    // would send an overtake request
    protected float impatientness;
    // A float ranging from 0 to 1 indicating the chance that this vehicle would
    // refuse an overtake request
    protected float stinginess;

    public Vehicle(List<Road> path) {
        this(path, DrivingMode.NORMAL, 0f);
    }

    public Vehicle(List<Road> path, DrivingMode drivingMode, float speed) {
        this(path, drivingMode, speed, 0f, 0f);
    }

    public Vehicle(
            List<Road> path,
            DrivingMode drivingMode,
            float speed,
            float impatientness,
            float stinginess) {
        this.path = path;
        this.position = path.getFirst().getPosition();
        this.drivingMode = drivingMode;
        this.speed = speed;
        this.impatientness = impatientness;
        this.stinginess = stinginess;
    }

    public Road nextDestination() {
        try {
            return this.path.getFirst();
        } catch (Exception e) {
            return null;
        }
    }

    public Road nextNextDestination() {
        try {
            return this.path.get(1);
        } catch (Exception e) {
            return null;
        }
    }

    public Vector2 getPosition() {
        return new Vector2(this.position);
    }

    public Vector2 getDirection() {
        return new Vector2(this.direction);
    }

    public void moveToward(Vector2 newPosition, float deltaTime) {
        Vector2 direction = newPosition.sub(this.position);
        this.direction = new Vector2(direction);
        this.position.add(direction.setLength(this.speed).scl(deltaTime));
    }

    public final void draw() {
        switch (Globals.drawMode) {
            case DrawMode.PRIMITIVE -> primitiveDraw();
            case DrawMode.GRAPHICAL -> graphicalDraw();
        }
    }

    public void increaseStinginess() {
        this.stinginess += 0.1;
    }

    public final void popDestination() {
        this.path.removeFirst();
    }

    public boolean shouldSendOvertakeRequest() {
        return Globals.rng.nextFloat() < impatientness;
    }

    public boolean shouldAcceptOvertakeRequest() {
        return Globals.rng.nextFloat() > stinginess;
    }

    public boolean shouldRunRedLight() {
        return switch (this.drivingMode) {
            case DrivingMode.AGGRESSIVE -> shouldSendOvertakeRequest();
            default -> false;
        };
    }

    private Polygon getPolygonMesh() {
        return new Polygon(
                new float[] {
                    -getWidth() / 2, -getHeight() / 2,
                    getWidth() / 2, -getHeight() / 2,
                    getWidth() / 2, getHeight() / 2,
                    -getWidth() / 2, getHeight() / 2
                });
    }

    public void primitiveDraw() {
        drawBody();
        drawText();
    }

    void drawBody() {
        float angle = getDirection().angleDeg();
        Polygon polygon = getPolygonMesh();

        polygon.rotate(angle);
        polygon.translate(position.x, position.y);

        PolygonDrawCall drawCall = new PolygonDrawCall(polygon, Color.BLACK, ShapeType.Line);
        Renderer.addPrimitiveDrawCall(drawCall);
    }

    void drawText() {
        LabelStyle style = new LabelStyle(Renderer.font, Color.RED);
        Label text = new Label(getVehicleName(), style);

        Container<Label> container = new Container<>(text);

        container.setTransform(true);
        container.setX(position.x);
        container.setY(position.y);

        float scaleX = getWidth() / text.getWidth();
        float scaleY = getHeight() / text.getHeight();

        container.setScaleX(scaleX);
        container.setScaleY(scaleY);

        float angle = getDirection().angleDeg();
        if (angle > 90 && angle < 270) {
            angle -= 180;
        }
        container.setRotation(angle);

        WidgetDrawCall drawCall = new WidgetDrawCall(container);
        Renderer.addGraphicalDrawCall(drawCall);
    }

    public abstract int getVehiclePriority();

    public abstract float getWidth();

    public abstract float getHeight();

    public abstract String getVehicleName();

    protected abstract void graphicalDraw();
}
