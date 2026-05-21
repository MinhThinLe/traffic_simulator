package org.render.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class LoadErrorMenu implements Gui {
    private static String errorMessage;
    @Override
    public Table createGUI() {
        Table table = new Table();
        table.setFillParent(true);

        Label errorLabel = new Label(errorMessage, Styles.getLabelStyle());
        table.add(errorLabel).row();

        return table;
    }

    public static void setErrorMessage(String message) {
        errorMessage = message;
    }
}
