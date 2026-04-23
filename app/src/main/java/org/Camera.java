package org;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class Camera {
    private static final float CAMERA_SPEED = 0.1f;

    private OrthographicCamera camera;
    
    public Camera(OrthographicCamera camera) {
        this.camera = camera;
        this.camera.zoom = 50;
    }
    
    public Matrix4 getCameraProjection() {
        return this.camera.combined;
    }

    private void handleKeyboardMovement() {
        Vector2 translation = new Vector2();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            translation.y = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            translation.x = -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            translation.y = -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            translation.x = 1;
        }

        if (translation.len2() < 1) {
            return;
        }

        translation.setLength(CAMERA_SPEED * this.camera.zoom);
        camera.translate(translation);
    }

    private void zoom() {
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            this.camera.zoom += 0.02 * this.camera.zoom;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            this.camera.zoom -= 0.02 * this.camera.zoom;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            System.out.println("Location: " + this.camera.position + " Zoom: " + this.camera.zoom);
        }
        
        this.camera.zoom = (float) MathUtils.clamp(this.camera.zoom, 0.00001, 100);
    }

    public void update() {
        handleKeyboardMovement();
        zoom();
        camera.update();
    }
}
