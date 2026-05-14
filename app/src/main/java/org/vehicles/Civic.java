package org.vehicles;

import org.render.drawcalls.TextureDrawCall;
import org.road.Road;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.List;

public class Civic extends Vehicle {
    private static final float DEFAULT_SPEED = 60;
    private static final float WIDTH = 30;
    private static final float HEIGHT = 15;

    private static final int SPRITE_COUNT = 48;
    private static final int SPRITE_SIZE = 100;
    private static final int GRID_WIDTH = 7;
    private static final int RENDERED_SIZE = 50;

    private static final String TEXTURE_PATH = "org/vehicles/textures/civics/";

    private Sprite sprite;

    public Civic(List<Road> path, CivicColor colorVariant) {
        super(path, DrivingMode.NORMAL, DEFAULT_SPEED);
        FileHandle textureFile = Gdx.files.internal(TEXTURE_PATH + colorVariant.toString().toLowerCase() + ".png");
        sprite = new Sprite(new Texture(textureFile));
    }

    @Override
    public String getVehicleName() {
        return "Civic";
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

        int regionX = (spriteNumber % GRID_WIDTH) * SPRITE_SIZE;
        int regionY = (spriteNumber / GRID_WIDTH) * SPRITE_SIZE;

        sprite.setRegion(regionX, regionY, SPRITE_SIZE, SPRITE_SIZE);

        new TextureDrawCall(
                sprite, 
                position.x - RENDERED_SIZE / 2, 
                position.y - RENDERED_SIZE / 2, 
                -position.y, 
                RENDERED_SIZE, 
                RENDERED_SIZE
                )
            .submit();
    }
}

enum CivicColor {
    BLACK,
    BROWN,
    GREEN,
    MAGENTA,
    WHITE
}
