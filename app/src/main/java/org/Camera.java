package org;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class Camera {
    private static final float CAMERA_SPEED = 7f;
    private static final float ZOOM_SPEED = 1f;
    private static final float MIN_ZOOM = 0.0001f;
    private static final float MAX_ZOOM = 100f;
    private static final float DEFAULT_ZOOM = 50f;

    private OrthographicCamera camera;
    
    public Camera(OrthographicCamera camera) {
        this.camera = camera;
        this.camera.zoom = DEFAULT_ZOOM;
    }
    
    public Matrix4 getCameraProjection() {
        return this.camera.combined;
    }

    private void handleKeyboardMovement(float deltaTime) {
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

        translation.setLength(CAMERA_SPEED * this.camera.zoom * deltaTime);
        camera.translate(translation);
    }

    private void zoom(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            this.camera.zoom += (ZOOM_SPEED * this.camera.zoom) * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            this.camera.zoom -= (ZOOM_SPEED * this.camera.zoom) * deltaTime;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            System.out.println("Location: " + this.camera.position + " Zoom: " + this.camera.zoom);
        }
        
        this.camera.zoom = (float) MathUtils.clamp(this.camera.zoom, MIN_ZOOM, MAX_ZOOM);
    }

    public void update(float deltaTime) {
        handleKeyboardMovement(deltaTime);
        zoom(deltaTime);
        camera.update();
    }
}
