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

public class Ambulance extends Vehicle {
    private static final float DEFAULT_SPEED = 110;
    private static final float WIDTH = 42;
    private static final float HEIGHT = 20;
    private static final String VEHICLE_NAME = "Ambu";

    private static final float[] polygonMesh = new float[] {
            -WIDTH / 2, -HEIGHT / 2,
             WIDTH / 2, -HEIGHT / 2,
             WIDTH / 2,  HEIGHT / 2,
            -WIDTH / 2,  HEIGHT / 2
    };

    public Ambulance(ArrayList<Road> path, Vector2 position) {
        super(path, position, DrivingMode.AGGRESSIVE, DEFAULT_SPEED);
    }

    @Override
    public int getVehiclePriority() {
        return 2;
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
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.polygon(polygon.getTransformedVertices());
    }

    private void drawText() {
        LabelStyle style = new LabelStyle(Renderer.textRenderer, Color.RED);
        Label text = new Label(VEHICLE_NAME, style);
        Container<Label> container = new Container<>(text);

        container.setTransform(true);
        container.setX(position.x - WIDTH / 2);
        container.setY(position.y - HEIGHT / 2);
        container.setWidth(WIDTH);
        container.setHeight(HEIGHT);

        float scaleX = WIDTH / text.getWidth();
        float scaleY = HEIGHT / text.getHeight();
        float scale = Math.min(scaleX, scaleY) * 0.8f;
        container.setScale(scale);

        float angle = getDirectionAngle();
        if (angle > 90 && angle < 270) {
            angle -= 180;
        }

        container.setOrigin(WIDTH / 2, HEIGHT / 2);
        container.setRotation(angle);
        container.draw(Renderer.graphicalRenderer, 1);
    }
}