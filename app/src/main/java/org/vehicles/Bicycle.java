package org.vehicles;

import org.road.Road;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class Bicycle extends Vehicle {
    private static final float WIDTH = 10;
    private static final float HEIGHT = 20;
    private static final float[] polygonMesh = new float[] {
        -WIDTH / 2, -HEIGHT / 2,
         WIDTH / 2, -HEIGHT / 2,
         WIDTH / 2,  HEIGHT / 2,
        -WIDTH / 2,  HEIGHT / 2
    };

    public Bicycle(ArrayList<Road> path, Vector2 position) {
        super(path, position, DrivingMode.NORMAL, 0.5f);
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
    protected void primitiveDraw(ShapeRenderer shapeRenderer) {
        System.out.println(position);
        Vector2 destination = nextDestination().getPosition();
        Vector2 direction = destination.sub(position);

        float angle = direction.angleDeg();
        Polygon polygon = new Polygon(polygonMesh);
        polygon.rotate(angle - 90);
        polygon.translate(position.x, position.y);

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.polygon(polygon.getTransformedVertices());
    }
}
