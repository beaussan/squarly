package me.nbeaussart.game.entity;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.game.map.GameSquare;
import me.nbeaussart.game.map.SquareContent;

/**
 * Created by beaussan on 01/11/16.
 */
public class EasyMonster extends Monster {
    public EasyMonster(GameSquare gameSquare) {
        super("Noob", 20, 10, new Color(255,255,0), gameSquare, new SquareContent('a', new Color(255,0,255), new Color(255,0,255)));
    }

    @Override
    public int getPointWhenDeath() {
        return 10;
    }
}
