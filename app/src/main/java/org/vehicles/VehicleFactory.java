package org.vehicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import org.road.Road;

import java.util.List;

public interface VehicleFactory {
    public Vehicle createVehicle(List<Road> path);

    default int getSpriteSize() {
        return 100; // Most common sprite size
    }

    String getTexturePath();

    public default Sprite getThumbnail() {
        Texture vehicleTexture = new Texture(Gdx.files.internal(getTexturePath()));
        Sprite thumbnail = new Sprite(vehicleTexture);
        thumbnail.setRegion(0, 0, getSpriteSize(), getSpriteSize());
        return thumbnail;
    }
    ;
}
