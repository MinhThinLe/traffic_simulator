package org.vehicles.vehicles;

import java.util.List;

import org.vehicles.DrivingMode;
import org.vehicles.Vehicle;
import org.render.drawcalls.TextureDrawCall;
import org.road.Road;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Sedan extends Vehicle {
    private static final String TEXTURE_PATH = "org/vehicles/textures/sedans/";

    private static final float DEFAULT_SPEED = 50;
    private static final float WIDTH = 30;
    private static final float HEIGHT = 15;

    private static final int SPRITE_SIZE = 100;
    private static final int RENDERED_SIZE = 50;
    private static final int SPRITE_COUNT = 48;
    private static final int COLUMN_COUNT = 7;

    private Sprite sprite;

    public Sedan(List<Road> path, SedanColor colorVariant) {
        super(path, DrivingMode.NORMAL, DEFAULT_SPEED, 0f, 0f);

        FileHandle file = Gdx.files.internal(TEXTURE_PATH + colorVariant.toString().toLowerCase() + ".png");
        sprite = new Sprite(new Texture(file));
    }

    @Override
    public String getVehicleName() {
        return "Sedan";
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
    public int getVehiclePriority() {
        return 0;
    }

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
