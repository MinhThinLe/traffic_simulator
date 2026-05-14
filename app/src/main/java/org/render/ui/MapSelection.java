package org.render.ui;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import org.Globals;
import org.render.Renderer;

import java.util.ArrayList;

public class MapSelection implements Gui {
    private static final String[] MAPS = {
        "3-way-intersection.graphml",
        "4-way-intersection.graphml",
        "5-way-intersection.graphml",
        "3-way-intersection-traffic-light.graphml",
    };

    private static final String[] DISPLAY_NAMES = {
        "Ngã ba", "Ngã tư", "Ngã năm", "Ngã ba với đèn đỏ",
    };

    @Override
    public Table createGUI() {
        Table table = new Table();
        table.setFillParent(true);

        Label mapSelection = new Label("Hãy chọn một bản đồ", Styles.getLabelStyle());
        table.add(mapSelection).pad(10).row();

        ArrayList<TextButton> buttons = new ArrayList<>();
        for (int i = 0; i < MAPS.length; i++) {
            buttons.add(new TextButton(DISPLAY_NAMES[i], Styles.getButtonStyle()));
        }

        for (int i = 0; i < MAPS.length; i++) {
            table.add(buttons.get(i)).size(300, 50).pad(10).row();
        }

        table.addListener(
                new EventListener() {
                    @Override
                    public boolean handle(Event event) {
                        if (!(event instanceof ChangeEvent)) {
                            return false;
                        }

                        int pressedButton = buttons.indexOf(event.getTarget());
                        if (pressedButton == -1) {
                            System.out.println("The fuck?");
                            return false;
                        }

                        Globals.mapName = MAPS[pressedButton];
                        Globals.gameState = GameState.NORMAL;
                        Renderer.resetUI();
                        return true;
                    }
                });

        return table;
    }
}
