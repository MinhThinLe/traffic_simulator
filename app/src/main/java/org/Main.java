package org;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main {
    public static void main(String[] args) {
        new Lwjgl3Application(new Game(), Game.getApplicationConfiguration());
    }
}

class Game implements ApplicationListener {
    FitViewport viewport;
    private SpriteBatch spriteBatch;
    private BitmapFont font;

    static Lwjgl3ApplicationConfiguration getApplicationConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Hello World");
        configuration.useVsync(true);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        configuration.setWindowedMode(1280, 720);

        return configuration;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void render() {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        spriteBatch.begin();

        font.setColor(0f, 0f, 0f, 1f);
        font.draw(spriteBatch, "Hello World", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        spriteBatch.end();
    }
    @Override
    public void pause() {

    }
    @Override
    public void create() {
        viewport = new FitViewport(16, 9);
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
    }
}
