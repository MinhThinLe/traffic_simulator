package org;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.render.*;

import java.util.Random;

public class Globals {
    public static final float DEFAULT_SIMULATION_SPEED = 1;
    public static final float DEFAULT_VEHICLE_SPAWN_DELAY = 10;

    public static DrawMode drawMode = DrawMode.PRIMITIVE;
    public static float vehicleSpawnDelay = DEFAULT_VEHICLE_SPAWN_DELAY;
    public static Stage stage = new Stage(new FitViewport(1280, 720));
    public static InputMultiplexer inputMultiplexer = new InputMultiplexer();

    public static Vector2 mouseWorldPosition = new Vector2();
    public static float simulationSpeed = DEFAULT_SIMULATION_SPEED;
    public static Random rng = new Random();
    public static int FONT_SIZE = 18;


    public static String VIETNAMESE_CHARACTERS =
            """
              à á ả ã ạ
            ă ằ ắ ẳ ẵ ặ
            â ầ ấ ẩ ẫ ậ
            đ
              è é ẻ ẽ ẹ
            ê ề ế ể ễ ệ
              ì í ỉ ĩ ị
              ò ó ỏ õ ọ
            ô ồ ố ổ ỗ ộ
            ơ ờ ớ ở ỡ ợ
              ù ú ủ ũ ụ
            ư ừ ứ ử ữ ự
              ỳ ý ỷ ỹ ỵ
            """
                    .replace(" ", "").replace("\n", "");

    static {
        inputMultiplexer.addProcessor(stage);
    }
}
