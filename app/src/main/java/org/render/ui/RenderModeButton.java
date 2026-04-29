package org.render.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class RenderModeButton extends TextButton {
    private boolean dummyBool;

    public RenderModeButton(BitmapFont font) {
        super("Press Me", makeStyle(font));

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dummyBool = true;

                return true;
            }
        });
    }

    private static TextButtonStyle makeStyle(BitmapFont font) {
        TextButtonStyle style = new TextButtonStyle();
        style.font = font;

        return style;
    }
}
