package org.vehicles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import org.render.Renderer;
import org.road.Road;

import java.util.ArrayList;

public class Motorbike extends Vehicle {
    private static final float DEFAULT_SPEED = 100;
    private static final float WIDTH = 24;
    private static final float HEIGHT = 10;
    private static final String VEHICLE_NAME = "Moto";

    private static final float[] polygonMesh = new float[] {
            -WIDTH / 2, -HEIGHT / 2,
             WIDTH / 2, -HEIGHT / 2,
             WIDTH / 2,  HEIGHT / 2,
            -WIDTH / 2,  HEIGHT / 2
    };

    public Motorbike(ArrayList<Road> path, Vector2 position) {
        super(path, position, DrivingMode.AGGRESSIVE, DEFAULT_SPEED);
    }

    @Override
    public int getVehiclePriority() {
        return 1;
    }

    @Override
    protected void graphicalDraw() {
        primitiveDraw();
    }

    @Override
    protected void primitiveDraw() {
        drawBody();
        drawText();
    }

    @Override
    public float getSize() {
        return WIDTH;
    }

    private float getDirectionAngle() {
        return getDirection().angleDeg();
    }

    private void drawBody() {
        float angle = getDirectionAngle();
        Polygon polygon = new Polygon(polygonMesh);
        polygon.rotate(angle);
        polygon.translate(position.x, position.y);

        ShapeRenderer shapeRenderer = Renderer.primitiveRenderer;
        shapeRenderer.setColor(Color.ORANGE);
        shapeRenderer.polygon(polygon.getTransformedVertices());
    }

    private void drawText() {
        LabelStyle style = new LabelStyle(Renderer.textRenderer, Color.BLACK);
        Label text = new Label(VEHICLE_NAME, style);
        Container<Label> container = new Container<>(text);

        container.setTransform(true);
        container.setX(position.x);
        container.setY(position.y);

        float scaleX = WIDTH / text.getWidth();
        float scaleY = HEIGHT / text.getHeight();
        container.setScaleX(scaleX);
        container.setScaleY(scaleY);

        float angle = getDirectionAngle();
        if (angle > 90 && angle < 270) {
            angle -= 180;
        }

        container.setRotation(angle);
        container.draw(Renderer.graphicalRenderer, 1);
    }
}