package org.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class AudioPlayer {
    private static final String HORN_SFX_PATH = "org/vehicles/sounds/honk.mp3";

    private static Sound honkSound;

    static {
        honkSound = Gdx.audio.newSound(Gdx.files.internal(HORN_SFX_PATH));
    }
    
    public static void playHonk() {
        honkSound.play();
    }
}
