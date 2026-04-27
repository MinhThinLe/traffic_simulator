package org.vehicles;

import org.render.Renderer;
import org.road.Road;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Bicycle extends Vehicle {
    private static final float DEFAULT_BICYCLE_SPEED = 50;
    private static final float WIDTH = 20;
    private static final float HEIGHT = 10;
    private static final String VEHICLE_NAME = "Bicycle";
    private static final float[] polygonMesh = new float[] {
        -WIDTH / 2, -HEIGHT / 2,
         WIDTH / 2, -HEIGHT / 2,
         WIDTH / 2,  HEIGHT / 2,
        -WIDTH / 2,  HEIGHT / 2
    };

    public Bicycle(ArrayList<Road> path, Vector2 position) {
        super(path, position, DrivingMode.NORMAL, DEFAULT_BICYCLE_SPEED);
    }

    @Override
    public int getVehiclePriority() {
        return 0; // Default priority
    }

    @Override
    protected void graphicalDraw() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void primitiveDraw() {
        drawBody();
        drawText();
    }

    private float getDirection() {
        Vector2 destination = nextDestination().getPosition();
        Vector2 direction = destination.sub(position);

        return direction.angleDeg();
    }

    private void drawBody() {
        float angle = getDirection();
        Polygon polygon = new Polygon(polygonMesh);

        polygon.rotate(angle);
        polygon.translate(position.x, position.y);

        ShapeRenderer shapeRenderer = Renderer.primitiveRenderer;

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.polygon(polygon.getTransformedVertices());
    }

    private void drawText() {
        LabelStyle style = new LabelStyle(Renderer.textRenderer, Color.RED);
        Label text = new Label(VEHICLE_NAME , style);

        Container<Label> container = new Container<>(text);

        container.setTransform(true);
        container.setX(position.x);
        container.setY(position.y);

        float scaleX = WIDTH / text.getWidth();
        float scaleY = HEIGHT / text.getHeight(); 

        container.setScaleX(scaleX);
        container.setScaleY(scaleY);

        float angle = getDirection();
        if (angle > 90 && angle < 270) {
            angle -= 180;
        }
        container.setRotation(angle);

        container.draw(Renderer.graphicalRenderer, 1);
    }
}
