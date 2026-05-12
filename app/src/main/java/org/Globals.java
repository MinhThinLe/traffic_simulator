package org;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.render.*;
import org.render.ui.GameState;

import java.util.Random;

public class Globals {
    public static GameState gameState = GameState.MAIN_MENU;
    public static DrawMode drawMode = DrawMode.PRIMITIVE;
    public static float vehicleSpawnDelay = 10;
    public static Stage stage = new Stage(new FitViewport(1280, 720));
    public static InputMultiplexer inputMultiplexer = new InputMultiplexer();

    public static Vector2 mouseWorldPosition = new Vector2();
    public static Random rng = new Random();
    public static String mapName = "3-way-intersection.graphml";

    static {
        inputMultiplexer.addProcessor(stage);
    }
}
