package me.nbeaussart.game.entity;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.game.map.GameSquare;

/**
 * Created by beaussan on 01/11/16.
 */
public class EasyMonster extends Monster {
    public EasyMonster(GameSquare gameSquare) {
        super("Noob", 20, 10, new Color(255,255,0), gameSquare);
    }

    @Override
    public int getPointWhenDeath() {
        return 10;
    }
}
