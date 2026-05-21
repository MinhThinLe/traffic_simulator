package org.render.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class LoadErrorMenu implements Gui {
    private static String errorMessage;
    @Override
    public Table createGUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(10);

        Label errorLabel = new Label(errorMessage, Styles.getLabelStyle());

        TextButton mainMenuButton = new TextButton("Màn hình chính", Styles.getButtonStyle());
        mainMenuButton.setName("title screen button");

        table.add(errorLabel).row();
        table.add(mainMenuButton).size(150, 50).row();

        return table;
    }

    public static void setErrorMessage(String message) {
        errorMessage = message;
    }
}
