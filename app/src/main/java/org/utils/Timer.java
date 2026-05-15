package org.utils;

import com.badlogic.gdx.Gdx;

public class Timer {
    private float duration;
    private float remaining;

    public Timer(float duration) {
        this.duration = duration;
        this.remaining = duration;
    }

    public void tick(float time) {
        this.remaining -= time;
    }

    public void tick() {
        tick(Gdx.graphics.getDeltaTime());
    }

    public boolean hasFinished() {
        if (this.remaining < 0) {
            this.remaining = this.duration;
            return true;
        }
        return false;
    }

    public float getDuration() {
        return this.duration;
    }

    public void setDuration(float newDuration) {
        duration = newDuration;

        if (remaining > duration) {
            remaining = duration;
        }
    }

    public float getTimeRemaining() {
        return this.remaining;
    }

    public void reset() {
        remaining = duration;
    }
}
