package org.vehicles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import org.Globals;
import org.render.DrawMode;
import org.render.Renderer;
import org.render.drawcalls.PolygonDrawCall;
import org.render.drawcalls.WidgetDrawCall;
import org.render.ui.Inspectable;
import org.render.ui.Styles;
import org.road.Road;
import org.utils.Timer;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle implements Inspectable {
    private static final float BASE_TIMER_DURATION = 5;
    protected List<Road> path;
    protected Vector2 position;
    protected DrivingMode drivingMode;
    protected float speed;
    protected Vector2 direction;
    // A float ranging from 0 to 1 indicating the chance that this vehicle
    // would send an overtake request
    protected float impatientness;
    protected Timer honkTimer;
    // A float ranging from 0 to 1 indicating the chance that this vehicle would
    // refuse an overtake request
    protected float stinginess;

    private List<Group> groups;

    public Vehicle(List<Road> path) {
        this(path, DrivingMode.NORMAL, 0f);
    }

    public Vehicle(List<Road> path, DrivingMode drivingMode, float speed) {
        this(path, drivingMode, speed, 0f, 0f);
    }

    public Vehicle(
            List<Road> path,
            DrivingMode drivingMode,
            float speed,
            float impatientness,
            float stinginess) {
        this.path = path;
        this.position = path.getFirst().getPosition();
        this.drivingMode = drivingMode;
        this.speed = speed;
        this.impatientness = impatientness;
        this.stinginess = stinginess;
        this.direction = new Vector2();

        this.honkTimer = new Timer(BASE_TIMER_DURATION - impatientness * 4);
        this.groups = new ArrayList<>();
    }

    public Road nextDestination() {
        try {
            return this.path.getFirst();
        } catch (Exception e) {
            return null;
        }
    }

    public Road nextNextDestination() {
        try {
            return this.path.get(1);
        } catch (Exception e) {
            return null;
        }
    }

    public Vector2 getPosition() {
        return new Vector2(this.position);
    }

    public Vector2 getDirection() {
        return new Vector2(this.direction);
    }

    public float getSpeed() {
        return this.speed;
    }

    public void moveToward(Vector2 newPosition, float deltaTime) {
        float distance = this.position.dst(newPosition);
        float speed = this.speed * deltaTime;
        if (distance < speed) {
            this.position.set(newPosition);
            return;
        }

        Vector2 direction = newPosition.sub(this.position);

        this.direction = new Vector2(direction);
        this.position.add(direction.setLength(speed));
    }

    public final void draw() {
        switch (Globals.drawMode) {
            case DrawMode.PRIMITIVE -> primitiveDraw();
            case DrawMode.GRAPHICAL -> graphicalDraw();
        }
    }

    public void increaseStinginess() {
        this.stinginess += 0.1;
    }

    public final void popDestination() {
        this.path.removeFirst();
    }

    public void resetTimer() {
        this.honkTimer.reset();
    }

    public boolean shouldSendOvertakeRequest() {
        honkTimer.tick();
        if (!honkTimer.hasFinished()) {
            return false;
        }
        return Globals.rng.nextFloat() < impatientness;
    }

    public boolean shouldAcceptOvertakeRequest() {
        return Globals.rng.nextFloat() > stinginess;
    }

    public boolean shouldRunRedLight() {
        return switch (this.drivingMode) {
            case DrivingMode.AGGRESSIVE -> shouldSendOvertakeRequest();
            default -> false;
        };
    }

    private Polygon getPolygonMesh() {
        return new Polygon(
                new float[] {
                    -getWidth() / 2, -getHeight() / 2,
                    getWidth() / 2, -getHeight() / 2,
                    getWidth() / 2, getHeight() / 2,
                    -getWidth() / 2, getHeight() / 2
                });
    }

    public void primitiveDraw() {
        drawBody();
        drawText();
    }

    public boolean isUnderCursor() {
        Polygon polygon = getPolygonMesh();
        polygon.translate(position.x, position.y);
        polygon.rotate(getDirection().angleDeg());

        return polygon.contains(Globals.mouseWorldPosition);
    }

    public List<Road> getPath() {
        return this.path;
    }

    void drawBody() {
        float angle = getDirection().angleDeg();
        Polygon polygon = getPolygonMesh();

        polygon.rotate(angle);
        polygon.translate(position.x, position.y);

        PolygonDrawCall drawCall = new PolygonDrawCall(polygon, Color.BLACK, ShapeType.Line);
        Renderer.addPrimitiveDrawCall(drawCall);
    }

    void drawText() {
        LabelStyle style = new LabelStyle(Renderer.getFont(Globals.FONT_SIZE), Color.RED);
        Label text = new Label(getVehicleName(), style);

        Container<Label> container = new Container<>(text);

        container.setTransform(true);
        container.setX(position.x);
        container.setY(position.y);

        float scaleX = getWidth() / text.getWidth();
        float scaleY = getHeight() / text.getHeight();

        container.setScaleX(scaleX);
        container.setScaleY(scaleY);

        float angle = getDirection().angleDeg();
        if (angle > 90 && angle < 270) {
            angle -= 180;
        }
        container.setRotation(angle);

        WidgetDrawCall drawCall = new WidgetDrawCall(container);
        Renderer.addGraphicalDrawCall(drawCall);
    }

    public abstract int getVehiclePriority();

    public abstract float getWidth();

    public abstract float getHeight();

    public abstract String getVehicleName();

    protected abstract void graphicalDraw();

    private static final float PADDING = 5;

    @Override
    public Table inspect() {
        Table inspectTable = new Table();
        inspectTable.setBackground(Renderer.uiSkin.getDrawable("window2"));
        inspectTable.defaults().pad(PADDING).growX();

        inspectTable.add(createSpeedSlider()).row();
        inspectTable.add(createImpatientnessSlider()).row();
        inspectTable.add(createStinginessSlider()).row();
        inspectTable.add(createDrivingModeDropDown()).row();

        return inspectTable;
    }

    private Table createSpeedSlider() {
        Table speedSliderGroup = new Table();
        speedSliderGroup.defaults().growX();

        Label textLabel = new Label("Tốc độ: ", Styles.getLabelStyle());
        Label speedLabel = new Label((int) speed + "", Styles.getLabelStyle());
        speedLabel.setAlignment(Align.right);
        Slider slider = new Slider(0, 100, 1, false, Styles.getSliderStyle());
        slider.setValue(speed);

        speedSliderGroup.add(textLabel).left();
        speedSliderGroup.add(speedLabel).right().row();
        speedSliderGroup.add(slider).colspan(2);

        speedSliderGroup.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (actor == slider) {
                            speed = slider.getValue();
                            speedLabel.setText((int) slider.getValue());
                        }
                    }
                });
        return speedSliderGroup;
    }

    private Table createImpatientnessSlider() {
        Table impatientnessSliderGroup = new Table();
        impatientnessSliderGroup.defaults().growX();

        Label textLabel = new Label("Độ nóng vội: ", Styles.getLabelStyle());
        Label impatientnessLabel =
                new Label(String.format("%.2f", impatientness), Styles.getLabelStyle());
        impatientnessLabel.setAlignment(Align.right);

        Slider slider = new Slider(0f, 1f, 0.01f, false, Styles.getSliderStyle());
        slider.setValue(impatientness);

        impatientnessSliderGroup.add(textLabel).left();
        impatientnessSliderGroup.add(impatientnessLabel).right().row();
        impatientnessSliderGroup.add(slider).colspan(2);

        impatientnessSliderGroup.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (actor == slider) {
                            impatientness = slider.getValue();
                            honkTimer.setDuration(BASE_TIMER_DURATION - impatientness * 4);
                            impatientnessLabel.setText(String.format("%.2f", impatientness));
                        }
                    }
                });

        return impatientnessSliderGroup;
    }

    private Table createStinginessSlider() {
        Table stinginessSliderGroup = new Table();
        stinginessSliderGroup.defaults().growX();

        Label textLabel = new Label("Độ xấu tính", Styles.getLabelStyle());
        Label stinginessLabel =
                new Label(String.format("%.2f", stinginess), Styles.getLabelStyle());
        stinginessLabel.setAlignment(Align.right);

        Slider slider = new Slider(0f, 1f, 0.01f, false, Styles.getSliderStyle());
        slider.setValue(stinginess);

        stinginessSliderGroup.add(textLabel).left();
        stinginessSliderGroup.add(stinginessLabel).right().row();
        stinginessSliderGroup.add(slider).colspan(2);

        stinginessSliderGroup.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        stinginess = slider.getValue();
                        stinginessLabel.setText(String.format("%.2f", stinginess));
                    }
                });

        return stinginessSliderGroup;
    }

    private Table createDrivingModeDropDown() {
        Table dropDownComponent = new Table();
        dropDownComponent.defaults().growX();

        SelectBox<DrivingMode> selectBox = new SelectBox<>(Styles.getSelectBoxStyle());
        selectBox.setItems(DrivingMode.values());
        selectBox.setSelectedIndex(drivingMode.ordinal());

        Label label = new Label("Phong cách lái xe: ", Styles.getLabelStyle());

        dropDownComponent.add(label).left().row();
        dropDownComponent.add(selectBox).left().row();

        dropDownComponent.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (actor == selectBox) {
                            drivingMode = selectBox.getSelected();
                        }
                    }
                });

        groups.add(selectBox.getScrollPane());

        return dropDownComponent;
    }

    @Override
    public void dropInspect() {
        groups.clear();
    }

    @Override
    public List<Group> getGroups() {
        return groups;
    }
}
