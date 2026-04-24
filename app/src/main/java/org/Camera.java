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
            translation.y += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            translation.x -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            translation.y -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            translation.x += 1;
        }

        if (translation.len2() < 1) {
            return;
        }

        translation.setLength(CAMERA_SPEED * this.camera.zoom * deltaTime);
        camera.translate(translation);
    }

    private static final float SCREEN_BORDER_RATIO = 0.1f;
    private static final float MOUSE_MOVEMENT_SPEED = 2.5f;
    private void windowBorderMovement(float deltaTime) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float borderSize = Float.min(screenWidth * SCREEN_BORDER_RATIO, screenHeight * SCREEN_BORDER_RATIO);

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.input.getY();

        Vector2 movementVector = new Vector2();

        if (mouseX < borderSize) {
            movementVector.x -= 1 - (mouseX / borderSize);
        }
        if (mouseY < borderSize) {
            movementVector.y += 1 - (mouseY / borderSize);
        }
        if (mouseX > screenWidth - borderSize) {
            mouseX -= screenWidth - borderSize;
            movementVector.x += mouseX / borderSize;
        }
        if (mouseY > screenHeight - borderSize) {
            mouseY -= screenHeight - borderSize;
            movementVector.y -= mouseY / borderSize;
        }

        movementVector.scl(MOUSE_MOVEMENT_SPEED * MOUSE_MOVEMENT_SPEED);
        camera.translate(movementVector);
    }

    private void zoom(float deltaTime) {
        float zoomAmount = ZOOM_SPEED * this.camera.zoom * deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            this.camera.zoom += zoomAmount;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            this.camera.zoom -= zoomAmount;
        }
        
        this.camera.zoom = (float) MathUtils.clamp(this.camera.zoom, MIN_ZOOM, MAX_ZOOM);
    }

    public void update(float deltaTime) {
        handleKeyboardMovement(deltaTime);
        windowBorderMovement(deltaTime);
        zoom(deltaTime);
        camera.update();
    }
}
