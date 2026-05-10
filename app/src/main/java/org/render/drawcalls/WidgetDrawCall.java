package org.render.drawcalls;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

public class WidgetDrawCall extends GraphicalDrawCall {
    protected WidgetGroup widget;

    public WidgetDrawCall(WidgetGroup widget) {
        super(0, 0, 0);
        this.widget = widget;
    }

    @Override
    public void draw(SpriteBatch graphicalRenderer) {
        widget.draw(graphicalRenderer, 1);
    }
}
