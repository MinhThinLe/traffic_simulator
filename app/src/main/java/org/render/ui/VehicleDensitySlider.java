package org.render.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Slider;

public class VehicleDensitySlider extends Slider {
    private static final float MIN_TIMER = 1;
    private static final float MAX_TIMER = 60;
    private static final float TIMER_STEP = 1;

    public VehicleDensitySlider() {
        super(MIN_TIMER, MAX_TIMER, TIMER_STEP, false, makeSliderStyle());
    }

    private static SliderStyle makeSliderStyle() {
        SliderStyle style = new SliderStyle();

        return style;
    }
}
