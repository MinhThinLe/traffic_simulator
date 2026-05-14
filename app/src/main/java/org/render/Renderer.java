package org.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import org.Globals;
import org.render.drawcalls.*;
import org.render.ui.GameState;
import org.render.ui.Hud;
import org.render.ui.MainMenu;
import org.render.ui.MapSelection;

import java.util.*;

public class Renderer {
    private static SpriteBatch graphicalRenderer = new SpriteBatch();
    private static List<GraphicalDrawCall> graphicalDrawCalls = new ArrayList<>();

    private static ShapeRenderer primitiveRenderer = new ShapeRenderer();
    private static List<PrimitiveDrawCall> primitiveDrawCalls = new ArrayList<>();

    private static final String FONT_PATH = "org/render/ui/skin/NotoSans.ttf";
    private static FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();
    private static Map<Integer, BitmapFont> fontCache = new HashMap<>();
    private static FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_PATH));

    private static final String UI_SKIN_PATH = "org/render/ui/skin/skin-composer-ui.atlas";
    public static Skin uiSkin = new Skin(new TextureAtlas(Gdx.files.internal(UI_SKIN_PATH)));

    static {
        primitiveRenderer.setAutoShapeType(true);

        fontParameter.characters += Globals.VIETNAMESE_CHARACTERS + Globals.VIETNAMESE_CHARACTERS.toUpperCase();
        fontParameter.magFilter = TextureFilter.Linear;
        fontParameter.minFilter = TextureFilter.Linear;

        Globals.stage.addActor(new MainMenu().createGUI());
    }

    public static void render(Matrix4 matrix) {
        primitiveRenderer.setProjectionMatrix(new Matrix4(matrix));
        graphicalRenderer.setProjectionMatrix(new Matrix4(matrix));

        ScreenUtils.clear(Color.WHITE);
        renderShapes();
        renderTextures();
        Globals.stage.draw();
    }

    private static final Comparator<PrimitiveDrawCall> PRIMITIVE_COMPARATOR =
            new PrimitiveDrawCallComparator();

    private static void renderShapes() {
        primitiveDrawCalls.sort(PRIMITIVE_COMPARATOR);
        primitiveRenderer.begin();

        for (int i = 0; i < primitiveDrawCalls.size(); i++) {
            primitiveDrawCalls.get(i).draw(primitiveRenderer);
        }

        primitiveRenderer.end();
        primitiveDrawCalls.clear();
    }

    private static final Comparator<GraphicalDrawCall> GRAPHICAL_COMPARATOR =
            new GraphicalDrawCallComparator();

    private static void renderTextures() {
        // TODO: Figure out how to batch texture draw calls later
        graphicalDrawCalls.sort(GRAPHICAL_COMPARATOR);
        graphicalRenderer.begin();

        for (int i = 0; i < graphicalDrawCalls.size(); i++) {
            graphicalDrawCalls.get(i).draw(graphicalRenderer);
        }

        graphicalRenderer.end();
        graphicalDrawCalls.clear();
    }

    public static void addPrimitiveDrawCall(PrimitiveDrawCall drawCall) {
        primitiveDrawCalls.add(drawCall);
    }

    public static void addGraphicalDrawCall(GraphicalDrawCall drawCall) {
        graphicalDrawCalls.add(drawCall);
    }

    public static BitmapFont getFont(int size) {
        if (fontCache.containsKey(size)) {
            return fontCache.get(size);
        }

        fontParameter.size = size;
        fontCache.put(size, fontGenerator.generateFont(fontParameter));
        
        return fontCache.get(size);
    }

    public static void resize(int width, int height) {
        Viewport viewport = Globals.stage.getViewport();
        viewport.update(width, height, true);
    }

    public static void processUI(float deltaTime) {
        Globals.stage.act(deltaTime);
    }

    public static void resetUI() {
        Globals.stage.clear();
        Globals.stage.addActor(
                switch (Globals.gameState) {
                    case GameState.MAIN_MENU -> new MainMenu().createGUI();
                    case GameState.NORMAL -> new Hud().createGUI();
                    case GameState.LEVEL_SELECTION -> new MapSelection().createGUI();
                });
    }
}
