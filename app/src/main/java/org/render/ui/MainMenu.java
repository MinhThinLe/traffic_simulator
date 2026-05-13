package org.render.ui;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import org.Globals;
import org.render.Renderer;

public class MainMenu implements Gui {
    @Override
    public Table createGUI() {
        Table table = new Table();
        table.setFillParent(true);

        TextButton quitButton =
                new TextButton("Quit", Styles.makeButtonStyle(Renderer.uiSkin, Renderer.getFont(Globals.FONT_SIZE)));
        TextButton startButton =
                new TextButton("Start", Styles.makeButtonStyle(Renderer.uiSkin, Renderer.getFont(Globals.FONT_SIZE)));

        table.add(startButton).size(100, 50).pad(10).row();
        table.add(quitButton).size(100, 50).pad(10);

        EventListener eventListener =
                new EventListener() {
                    @Override
                    public boolean handle(Event event) {
                        if (!(event instanceof ChangeEvent)) {
                            return false;
                        }

                        if (event.getTarget() == quitButton) {
                            System.exit(0);
                        }
                        if (event.getTarget() == startButton) {
                            Globals.gameState = GameState.LEVEL_SELECTION;
                            Renderer.resetUI();
                        }

                        return false;
                    }
                };

        table.addListener(eventListener);
        return table;
    }
}
