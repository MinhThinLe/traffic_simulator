package org.render.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class PauseMenu implements Gui {
    @Override
    public Table createGUI() {
        Table table = new Table();
        table.setFillParent(true);

        Label pauseLabel = new Label("Tạm dừng", Styles.getLabelStyle());
        TextButton unpauseButton = new TextButton("Tiếp tục", Styles.getButtonStyle());
        TextButton titleScreenButton = new TextButton("Màn hình chính", Styles.getButtonStyle());
        TextButton quitButton = new TextButton("Thoát", Styles.getButtonStyle());

        unpauseButton.setName("unpause button");
        titleScreenButton.setName("title screen button");

        table.add(pauseLabel).pad(10).row();
        table.add(unpauseButton).size(150, 50).pad(10).row();
        table.add(titleScreenButton).size(150, 50).pad(10).row();
        table.add(quitButton).size(150, 50).pad(10).row();

        table.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (actor == quitButton) {
                            System.exit(0);
                        }
                    }
                });

        return table;
    }
}
