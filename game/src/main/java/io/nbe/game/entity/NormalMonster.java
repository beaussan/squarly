package io.nbe.game.entity;

import io.nbe.game.utils.Const;
import io.nbe.game.map.GameSquare;

/**
 * Created by beaussan on 01/11/16.
 */
public class NormalMonster extends Monster {
    public NormalMonster(GameSquare gameSquare) {
        super("Normal", 50, 20, gameSquare, Const.NORMAL_MONSTER_SQUARE);
    }
    public NormalMonster(String name, int life, int atk, GameSquare gameSquare) {
        super(name, life, atk, gameSquare, Const.NORMAL_MONSTER_SQUARE);
    }

    @Override
    public int getPointWhenDeath() {
        return Const.NORMAL_MONSTER_POINT;
    }
}
