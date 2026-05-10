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

    private static final int SPRITE_SIZE = 140;

    private Texture AMBULANCE_TEXTURE = new Texture(Gdx.files.internal("org/vehicles/textures/Ambulance.png"));
    private Sprite sprite = new Sprite(AMBULANCE_TEXTURE, 0, 0, 140, 140);

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

    private static final int SPRITE_COUNT = 48;
    private static final int GRID_WIDTH = 7;
    @Override
    protected void graphicalDraw() {
        float direction = getDirection().angleDeg();
        float spriteStep = 360f / (SPRITE_COUNT - 1);
        int spriteNumber =  SPRITE_COUNT - (int) (direction / spriteStep) - 1;

        int regionX = (spriteNumber % GRID_WIDTH) * SPRITE_SIZE;
        int regionY = (spriteNumber / GRID_WIDTH) * SPRITE_SIZE;

        sprite.setRegion(regionX, regionY, SPRITE_SIZE, SPRITE_SIZE);

        new TextureDrawCall(sprite, position.x - SPRITE_SIZE / 4, position.y - SPRITE_SIZE / 4, SPRITE_SIZE / 4, SPRITE_SIZE / 4, SPRITE_SIZE / 2, SPRITE_SIZE / 2, 1, 1, 0).submit();;
    }
}
