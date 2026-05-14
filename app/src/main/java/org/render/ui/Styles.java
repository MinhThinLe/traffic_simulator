package org.render.ui;

import org.Globals;
import org.render.Renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class Styles {
    public static TextButtonStyle getButtonStyle() {
        return new TextButtonStyle(
                Renderer.uiSkin.getDrawable("button"),
                Renderer.uiSkin.getDrawable("button-pressed"),
                Renderer.uiSkin.getDrawable("button-over"),
                Renderer.getFont(Globals.FONT_SIZE));
    }

    public static SliderStyle getSliderStyle() {
        return new SliderStyle(
                Renderer.uiSkin.getDrawable("slider-horizontal"), Renderer.uiSkin.getDrawable("slider-knob"));
    }

    public static LabelStyle getLabelStyle() {
        return new LabelStyle(Renderer.getFont(Globals.FONT_SIZE), Color.BLACK);
    }
}
