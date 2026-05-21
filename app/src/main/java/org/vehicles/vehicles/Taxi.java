package org.vehicles.vehicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import org.render.drawcalls.TextureDrawCall;
import org.road.Road;
import org.vehicles.DrivingMode;
import org.vehicles.Vehicle;

import java.util.List;

public class Taxi extends Vehicle {
    private static final String TEXTURE_PATH = "org/vehicles/textures/taxi/taxi.png";
    private static final float DEFAULT_SPEED = 60;
    private static final float WIDTH = 35;
    private static final float HEIGHT = 20;

    private static final int SPRITE_SIZE = 100;
    private static final int RENDERED_SIZE = 50;

    private Sprite sprite;

    public Taxi(List<Road> path) {
        super(path, DrivingMode.NORMAL, DEFAULT_SPEED, 0.5f, 0f);

        Texture texture = new Texture(Gdx.files.internal(TEXTURE_PATH));
        sprite = new Sprite(texture);
    }

    @Override
    public int getVehiclePriority() {
        return 1;
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
        return "Taxi";
    }

    private static final int SPRITE_COUNT = 48;
    private static final int COLUMN_COUNT = 7;

    @Override
    protected void graphicalDraw() {
        float direction = getDirection().angleDeg();
        float spriteStep = 360f / (SPRITE_COUNT - 1);
        int spriteNumber = SPRITE_COUNT - (int) (direction / spriteStep) - 1;

        int regionX = (spriteNumber % COLUMN_COUNT) * SPRITE_SIZE;
        int regionY = (spriteNumber / COLUMN_COUNT) * SPRITE_SIZE;

        sprite.setRegion(regionX, regionY, SPRITE_SIZE, SPRITE_SIZE);

        new TextureDrawCall(
                        sprite,
                        position.x - RENDERED_SIZE / 2,
                        position.y - RENDERED_SIZE / 2,
                        -position.y,
                        RENDERED_SIZE,
                        RENDERED_SIZE)
                .submit();
    }
}
