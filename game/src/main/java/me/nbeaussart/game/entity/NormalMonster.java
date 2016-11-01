package me.nbeaussart.game.entity;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.game.map.GameSquare;

/**
 * Created by beaussan on 01/11/16.
 */
public class NormalMonster extends Monster {
    public NormalMonster(GameSquare gameSquare) {
        super("Normal", 50, 20, new Color(255,0,0), gameSquare);
    }

    @Override
    public int getPointWhenDeath() {
        return 100;
    }
}
