package org.render.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class RenderModeButton extends TextButton {
    private boolean clicked;

    public RenderModeButton(BitmapFont font, Skin skin) {
        super("Press Me", makeStyle(skin, font));

        addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(
                            InputEvent event, float x, float y, int pointer, int button) {
                        clicked = !clicked;
                        return true;
                    }
                });
    }

    private static TextButtonStyle makeStyle(Skin skin, BitmapFont font) {
        TextButtonStyle style =
                new TextButtonStyle(
                        skin.getDrawable("button"),
                        skin.getDrawable("button-pressed"),
                        skin.getDrawable("button-over"),
                        font);
        return style;
    }
}
