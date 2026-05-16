package org.render.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenu implements Gui {
    @Override
    public Table createGUI() {
        Table table = new Table();
        table.setFillParent(true);

        TextButton quitButton = new TextButton("Thoát", Styles.getButtonStyle());
        TextButton startButton = new TextButton("Bắt đầu", Styles.getButtonStyle());

        startButton.setName("start button");

        table.add(startButton).size(100, 50).pad(10).row();
        table.add(quitButton).size(100, 50).pad(10);

        ChangeListener changeListener =
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (actor == quitButton) {
                            System.exit(0);
                        }
                    }
                };

        table.addListener(changeListener);
        return table;
    }
}
