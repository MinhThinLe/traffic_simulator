package org.vehicles.vehicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import org.render.drawcalls.TextureDrawCall;
import org.road.Road;
import org.utils.Timer;
import org.vehicles.*;

import java.util.List;

public class PoliceCar extends Vehicle {
    private static final String TEXTURE_PATH = "org/vehicles/textures/police_car/police_car.png";
    private static final float DEFAULT_SPEED = 80;
    private static final float WIDTH = 40;
    private static final float HEIGHT = 15;

    private static final int SPRITE_SIZE = 100;
    private static final int RENDERED_SIZE = 50;

    private static final float FLASH_INTERVAL = 0.2f;

    private Sprite sprite;
    private Timer timer;
    private boolean side;

    public PoliceCar(List<Road> path) {
        super(path, DrivingMode.AGGRESSIVE, DEFAULT_SPEED, 1f, 1f);

        Texture texture = new Texture(Gdx.files.internal(TEXTURE_PATH));
        sprite = new Sprite(texture);
        timer = new Timer(FLASH_INTERVAL);
    }

    @Override
    public int getVehiclePriority() {
        return 8;
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
        return "Police car";
    }

    private static final int SPRITE_COUNT = 48;
    private static final int COLUMN_COUNT = 7;

    @Override
    protected void graphicalDraw() {
        timer.tick();
        if (timer.hasFinished()) {
            side = !side;
        }

        float direction = getDirection().angleDeg();
        float spriteStep = 360f / (SPRITE_COUNT - 1);
        int spriteNumber = SPRITE_COUNT - (int) (direction / spriteStep) - 1;

        int regionX = (spriteNumber % COLUMN_COUNT) * SPRITE_SIZE;
        int regionY = (spriteNumber / COLUMN_COUNT) * SPRITE_SIZE;

        if (side) {
            regionX += COLUMN_COUNT * SPRITE_SIZE;
        }

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
