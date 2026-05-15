package org.vehicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import org.render.drawcalls.TextureDrawCall;
import org.road.Road;
import org.utils.Timer;

import java.util.List;

public class Ambulance extends Vehicle {
    private static final String TEXTURE_PATH = "org/vehicles/textures/ambulance/ambulance.png";
    private static final float DEFAULT_AMBULANCE_SPEED = 100;
    private static final float WIDTH = 30;
    private static final float HEIGHT = 15;

    static final int SPRITE_SIZE = 140;
    static final int RENDERED_SIZE = 50;

    private Sprite sprite;
    private AmbulanceLight light;

    public Ambulance(List<Road> path) {
        super(path, DrivingMode.AGGRESSIVE, DEFAULT_AMBULANCE_SPEED, 1f, 1f);
        Texture ambulanceTexture = new Texture(Gdx.files.internal(TEXTURE_PATH));
        sprite = new Sprite(ambulanceTexture);
        light = new AmbulanceLight();
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

        this.light.draw(this.position, this.direction.angleDeg());
    }
}

class AmbulanceLight {
    private static final String TEXTURE_PATH = "org/vehicles/textures/ambulance/light.png";

    private static final int RENDERED_SIZE = 16;

    private static final Vector2 LEFT_LIGHT_OFFSET = new Vector2(11, -16).scl(Ambulance.RENDERED_SIZE / Ambulance.SPRITE_SIZE);
    private static final Vector2 RIGHT_LIGHT_OFFSET = new Vector2(11, 6).scl(Ambulance.RENDERED_SIZE / Ambulance.SPRITE_SIZE);
    
    private Sprite sprite;
    private Timer timer;
    private int renderState;
    AmbulanceLight() {
        Texture texture = new Texture(Gdx.files.internal(TEXTURE_PATH));
        this.sprite = new Sprite(texture);
        this.timer = new Timer(0.2f);
    }

    public void draw(Vector2 parentPosition, float rotation) {
        timer.tick();
        if (timer.hasFinished()) {
            renderState = (renderState + 1) % 4;
        }
        if (renderState % 2 == 0) {
            return;
        }

        Vector2 offset = switch (renderState) {
            // case 1 -> LEFT_LIGHT_OFFSET;
            // case 3 -> RIGHT_LIGHT_OFFSET;
            default -> Vector2.Zero;
        };

        offset.rotateDeg(rotation);

        new TextureDrawCall(sprite, 
                parentPosition.x - RENDERED_SIZE / 2 + offset.x, 
                parentPosition.y - RENDERED_SIZE / 2 + offset.y, 
                -parentPosition.y, 
                RENDERED_SIZE, 
                RENDERED_SIZE).submit();;
    }
}
