package org.vehicles;

import com.badlogic.gdx.math.Vector2;

import org.render.drawcalls.TextureDrawCall;
import org.road.Road;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.List;

public class Ambulance extends Vehicle {
    private static final float DEFAULT_AMBULANCE_SPEED = 100;
    private static final float WIDTH = 40;
    private static final float HEIGHT = 20;

    private static final Texture AMBULANCE_TEXTURE = new Texture(Gdx.files.internal("org/vehicles/textures/Ambulance.png"));
    private static final Sprite AMBULANCE_SPRITE = new Sprite(AMBULANCE_TEXTURE, 0, 0, 140, 140);

    public Ambulance(List<Road> path, Vector2 position) {
        super(path, position, DrivingMode.AGGRESSIVE, DEFAULT_AMBULANCE_SPEED);
        this.impatientness = 1;
    }

    @Override
    public int getVehiclePriority() {
        return 10;
    }

    @Override
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public String getVehicleName() {
        return "Ambulance";
    }

    @Override
    protected void graphicalDraw() {
        new TextureDrawCall(AMBULANCE_SPRITE, position.x, position.y, 0, 0, 70, 70, 1, 1, 0).submit();;
    }
}
