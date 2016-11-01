package me.nbeaussart.game.entity;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.game.map.GameSquare;
import me.nbeaussart.game.map.SquareContent;

/**
 * Created by beaussan on 01/11/16.
 */
public class NormalMonster extends Monster {
    public NormalMonster(GameSquare gameSquare) {
        super("Normal", 50, 20, gameSquare, new SquareContent('b', new Color(255,255,0), new Color(255,255,0)));
    }

    @Override
    public int getPointWhenDeath() {
        return 100;
    }
}
