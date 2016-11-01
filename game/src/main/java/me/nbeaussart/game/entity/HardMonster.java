package me.nbeaussart.game.entity;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.game.map.GameSquare;
import me.nbeaussart.game.map.SquareContent;

/**
 * Created by beaussan on 01/11/16.
 */
public class HardMonster extends Monster {
    public HardMonster(GameSquare gameSquare) {
        super("Petit gros et moche", 100, 30, new Color(255,0,255), gameSquare, new SquareContent('c', new Color(255,0,0), new Color(255,0,0)));
    }

    @Override
    public int getPointWhenDeath() {
        return 1000;
    }
}
