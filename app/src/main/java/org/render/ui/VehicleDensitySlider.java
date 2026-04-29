package org.render.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

public class VehicleDensitySlider extends Slider {
    private static final float MIN_TIMER = 1;
    private static final float MAX_TIMER = 60;
    private static final float TIMER_STEP = 1;

    public VehicleDensitySlider(Skin skin) {
        super(MIN_TIMER, MAX_TIMER, TIMER_STEP, false, makeSliderStyle(skin));
    }

    private static SliderStyle makeSliderStyle(Skin skin) {
        SliderStyle style =
                new SliderStyle(
                        skin.getDrawable("slider-horizontal"), skin.getDrawable("slider-knob"));
        return style;
    }
}
