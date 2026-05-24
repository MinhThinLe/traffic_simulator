package org.render.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

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

        List<TextButton> buttons = new ArrayList<>();
        for (int i = 0; i < MAPS.length; i++) {
            TextButton button = new TextButton(DISPLAY_NAMES[i], Styles.getButtonStyle());
            button.setName("level selection button");
            button.setUserObject(MAPS[i]);

            buttons.add(button);
        }

        for (int i = 0; i < MAPS.length; i++) {
            table.add(buttons.get(i)).size(300, 50).pad(10).row();
        }

        TextButton customMapButton = new TextButton("Nạp từ file", Styles.getButtonStyle());
        customMapButton.setName("level selection button");
        table.add(customMapButton).size(300, 50).pad(10).row();
        customMapButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        JFileChooser chooser = new JFileChooser();
                        chooser.showOpenDialog(null);
                        customMapButton.setUserObject(chooser.getSelectedFile().toString());
                    }
                });

        return table;
    }
}
