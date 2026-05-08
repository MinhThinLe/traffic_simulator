package org.render.drawcalls;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Container;

public class ContainerDrawCall extends GraphicalDrawCall {
    protected Container<?> container;

    public ContainerDrawCall(Container<?> container) {
        super(0, 0, 0);
        this.container = container;
    }
    @Override
    public void draw(SpriteBatch graphicalRenderer) {
        container.draw(graphicalRenderer, 1);
    }
}
