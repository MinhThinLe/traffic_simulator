package org.vehicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import org.render.drawcalls.TextureDrawCall;
import org.road.Road;

import java.util.List;

public class Ambulance extends Vehicle {
    private static final String TEXTURE_PATH = "org/vehicles/textures/Ambulance.png";
    private static final float DEFAULT_AMBULANCE_SPEED = 100;
    private static final float WIDTH = 30;
    private static final float HEIGHT = 15;

    private static final int SPRITE_SIZE = 140;
    private static final int RENDERED_SIZE = 50;

    private Sprite sprite;

    public Ambulance(List<Road> path) {
        super(path, DrivingMode.AGGRESSIVE, DEFAULT_AMBULANCE_SPEED, 1f, 1f);
        Texture ambulanceTexture = new Texture(Gdx.files.internal(TEXTURE_PATH));
        sprite = new Sprite(ambulanceTexture);
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

    private static final int SPRITE_COUNT = 48;
    private static final int GRID_WIDTH = 7;

    @Override
    protected void graphicalDraw() {
        float direction = getDirection().angleDeg();
        float spriteStep = 360f / (SPRITE_COUNT - 1);
        int spriteNumber = SPRITE_COUNT - (int) (direction / spriteStep) - 1;

        int regionX = (spriteNumber % GRID_WIDTH) * SPRITE_SIZE;
        int regionY = (spriteNumber / GRID_WIDTH) * SPRITE_SIZE;

        sprite.setRegion(regionX, regionY, SPRITE_SIZE, SPRITE_SIZE);

        new TextureDrawCall(
                        sprite,
                        position.x - RENDERED_SIZE / 2,
                        position.y - RENDERED_SIZE / 2,
                        -position.y,
                        RENDERED_SIZE,
                        RENDERED_SIZE)
                .submit();
        ;
    }
}
