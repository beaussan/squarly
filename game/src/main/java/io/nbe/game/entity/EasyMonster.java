package io.nbe.game.entity;

import io.nbe.game.utils.Const;
import io.nbe.game.map.GameSquare;

/**
 * Created by beaussan on 01/11/16.
 */
public class EasyMonster extends Monster {
    public EasyMonster(GameSquare gameSquare) {
        super("Noob", 20, 10, gameSquare, Const.EASY_MONSTER_SQUARE);

    }
    public EasyMonster(String name, int life, int atk, GameSquare gameSquare) {
        super(name, life, atk, gameSquare, Const.EASY_MONSTER_SQUARE);
    }



    @Override
    public int getPointWhenDeath() {
        return Const.EASY_MONSTER_POINT;
    }
}
