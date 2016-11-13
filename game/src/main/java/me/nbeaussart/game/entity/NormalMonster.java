package me.nbeaussart.game.entity;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.game.map.GameSquare;
import me.nbeaussart.game.map.SquareContent;

import static me.nbeaussart.game.utils.Const.NORMAL_MONSTER_POINT;
import static me.nbeaussart.game.utils.Const.NORMAL_MONSTER_SQUARE;

/**
 * Created by beaussan on 01/11/16.
 */
public class NormalMonster extends Monster {
    public NormalMonster(GameSquare gameSquare) {
        super("Normal", 50, 20, gameSquare, NORMAL_MONSTER_SQUARE);
    }
    public NormalMonster(String name, int life, int atk, GameSquare gameSquare) {
        super(name, life, atk, gameSquare, NORMAL_MONSTER_SQUARE);
    }

    @Override
    public int getPointWhenDeath() {
        return NORMAL_MONSTER_POINT;
    }
}
