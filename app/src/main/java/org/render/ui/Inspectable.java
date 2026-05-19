package org.render.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public interface Inspectable {
    Table inspect();
    List<Rectangle> getWindows();
}
