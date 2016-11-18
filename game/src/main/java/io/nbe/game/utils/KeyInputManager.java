package io.nbe.game.utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by beaussan on 31/10/16.
 */
public class KeyInputManager implements KeyListener {

    private Map<Integer, Boolean> keys = new HashMap<>();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys.put(e.getKeyCode(), false);
    }

    public boolean isPressed(int keycode){
        if (!keys.containsKey(keycode)) {
            return false;
        }
        else return keys.get(keycode);
    }
}
