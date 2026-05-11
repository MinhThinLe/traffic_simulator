package org.render.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class Styles {
    public static TextButtonStyle makeButtonStyle(Skin skin, BitmapFont font) {
        return new TextButtonStyle(
                skin.getDrawable("button"),
                skin.getDrawable("button-pressed"),
                skin.getDrawable("button-over"),
                font);
    }

    public static SliderStyle makeSliderStyle(Skin skin, BitmapFont font) {
        return new SliderStyle(
                skin.getDrawable("slider-horizontal"), skin.getDrawable("slider-knob"));
    }
}
