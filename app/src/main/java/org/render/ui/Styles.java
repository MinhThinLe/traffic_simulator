package org.render.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import org.Globals;
import org.render.Renderer;

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
                Renderer.uiSkin.getDrawable("slider-horizontal"),
                Renderer.uiSkin.getDrawable("slider-knob"));
    }

    public static LabelStyle getLabelStyle() {
        return new LabelStyle(Renderer.getFont(Globals.FONT_SIZE), Color.BLACK);
    }

    public static SelectBoxStyle getSelectBoxStyle() {
        return new SelectBoxStyle(
                Renderer.getFont(Globals.FONT_SIZE),
                Color.BLACK,
                Renderer.uiSkin.getDrawable("selectbox"),
                getScrollPaneStyle(),
                getListStyle());
    }

    public static ScrollPaneStyle getScrollPaneStyle() {
        return new ScrollPaneStyle(
                null,
                Renderer.uiSkin.getDrawable("scrollpane"),
                Renderer.uiSkin.getDrawable("scrollpane-knob"),
                Renderer.uiSkin.getDrawable("scrollpane"),
                Renderer.uiSkin.getDrawable("scrollpane-knob"));
    }

    public static ListStyle getListStyle() {
        ListStyle style =
                new ListStyle(
                        Renderer.getFont(Globals.FONT_SIZE),
                        Color.WHITE,
                        Color.WHITE,
                        Renderer.uiSkin.getDrawable("button-pressed"));
        style.background = Renderer.uiSkin.getDrawable("list");

        return style;
    }

    public static CheckBoxStyle getCheckBoxStyle() {
        return new CheckBoxStyle(Renderer.uiSkin.getDrawable("checkbox-off"), Renderer.uiSkin.getDrawable("checkbox-on"), Renderer.getFont(Globals.FONT_SIZE), Color.BLACK);
    }
}
