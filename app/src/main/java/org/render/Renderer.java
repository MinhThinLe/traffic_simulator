package org.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;

import org.Globals;
import org.render.drawcalls.*;
import org.render.ui.*;

import java.util.*;

public class Renderer {
    private static SpriteBatch graphicalRenderer = new SpriteBatch();
    private static List<GraphicalDrawCall> graphicalDrawCalls = new ArrayList<>();

    private static ShapeRenderer primitiveRenderer = new ShapeRenderer();
    private static List<PrimitiveDrawCall> primitiveDrawCalls = new ArrayList<>();

    private static final String FONT_PATH = "org/render/ui/skin/NotoSans.ttf";
    private static FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();
    private static Map<Integer, BitmapFont> fontCache = new HashMap<>();
    private static FreeTypeFontGenerator fontGenerator =
            new FreeTypeFontGenerator(Gdx.files.internal(FONT_PATH));

    private static final String UI_SKIN_PATH = "org/render/ui/skin/skin-composer-ui.atlas";
    public static Skin uiSkin = new Skin(new TextureAtlas(Gdx.files.internal(UI_SKIN_PATH)));
    private static Table uiTable = new Table();
    private static Stage stage = new Stage(new FitViewport(1280, 720));

    static {
        primitiveRenderer.setAutoShapeType(true);

        fontParameter.characters +=
                Globals.VIETNAMESE_CHARACTERS + Globals.VIETNAMESE_CHARACTERS.toUpperCase();
        fontParameter.magFilter = TextureFilter.Linear;
        fontParameter.minFilter = TextureFilter.Linear;

        uiTable.setFillParent(true);
        stage.addActor(uiTable);

        resetUI(GameState.MAIN_MENU);
    }

    public static void render(Matrix4 matrix) {
        primitiveRenderer.setProjectionMatrix(new Matrix4(matrix));
        graphicalRenderer.setProjectionMatrix(new Matrix4(matrix));

        ScreenUtils.clear(Color.WHITE);
        renderShapes();
        renderTextures();
        stage.draw();
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
        Viewport viewport = stage.getViewport();
        viewport.update(width, height, true);
    }

    public static void processUI() {
        stage.act();
    }

    public static void resetUI(GameState currentState) {
        // This list has to match with the ordinality of GameState
        List<Gui> menus =
                List.of(
                        new MainMenu(), // The corresponding GUI of MAIN_MENU
                        new MapSelection(),
                        new LoadErrorMenu(),
                        new Hud(),
                        new PauseMenu());
        uiTable.clearChildren();
        uiTable.addActor(menus.get(currentState.ordinal()).createGUI());
    }

    public static Stage getStage() {
        return stage;
    }

    public static void addListener(EventListener listener) {
        stage.addListener(listener);
    }
}
