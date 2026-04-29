package org.render.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import org.render.DrawMode;

public class RenderModeButton extends TextButton {
    public RenderModeButton(BitmapFont font, Skin skin) {
        super(DrawMode.PRIMITIVE.toString(), makeStyle(skin, font));
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
