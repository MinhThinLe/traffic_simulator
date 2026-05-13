package org.vehicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import org.render.drawcalls.TextureDrawCall;
import org.road.Road;
import java.util.List;

public class Bus extends Vehicle {
    private static final float DEFAULT_BUS_SPEED = 40;
    private static final float WIDTH = 40;
    private static final float HEIGHT = 20;

    private static final int SPRITE_SIZE = 210;
    private static final int RENDERED_SIZE = 60;
    private static final int SPRITE_COUNT = 48;
    private static final int GRID_WIDTH = 7;

    private Sprite sprite;

    public Bus(List<Road> path, BusColor colorVariant) {
        super(path, DrivingMode.NORMAL, DEFAULT_BUS_SPEED);
        FileHandle file = Gdx.files.internal("org/vehicles/textures/buses/" + colorVariant.toString() + ".png");
        sprite = new Sprite(new Texture(file));
    }

    @Override
    public String getVehicleName() {
        return "Bus";
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

        new TextureDrawCall(sprite, position.x - RENDERED_SIZE / 2, position.y - RENDERED_SIZE / 2, RENDERED_SIZE / 2,
                RENDERED_SIZE / 2, RENDERED_SIZE, RENDERED_SIZE, 1, 1, 0).submit();
    }
}

enum BusColor {
    BLACK,
    BLUE,
    GREEN,
    WHITE,
    YELLOW,
    ;

    @Override
    public String toString() {
        return switch (this.ordinal()) {
            case 0 -> "black";
            case 1 -> "blue";
            case 2 -> "green";
            case 3 -> "white";
            case 4 -> "yellow";
            default -> "NOT IMPLEMENTED";
        };
    }
}
