package org.render.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.List;

public interface Inspectable {
    Table inspect();

    void dropInspect();

    List<Group> getGroups();
}
